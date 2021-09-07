package sigma.service;

import com.stripe.Stripe;
import com.stripe.model.*;
import jakarta.servlet.http.HttpServletRequest;
import sigma.Sigma;
import sigma.repo.PlanRepo;
import sigma.repo.UserRepo;
import sigma.model.User;
import qio.annotate.Inject;
import qio.annotate.Property;
import qio.annotate.Service;
import qio.model.web.ResponseData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlanService {

    @Property("stripe.key")
    private String apiKey;

    @Inject
    UserRepo userRepo;

    @Inject
    PlanRepo planRepo;

    @Inject
    AuthService authService;

    @Inject
    MailService mailService;


    public String getUserPermission(String id){
        return Sigma.USER_MAINTENANCE + id;
    }

    public String select(ResponseData responseData){
        if(!authService.isAuthenticated()){
            responseData.put("message", "Please signin to continue");
            return "[redirect]/";
        }
        List<OkayPlan> okayPlans = planRepo.getList();
        responseData.put("okayPlans", okayPlans);
        return "plan/select";
    }

    public String upgrade(ResponseData responseData){
        if(!authService.isAuthenticated()){
            responseData.put("message", "Please signin to continue");
            return "[redirect]/";
        }

        User user = authService.getUser();
        List<OkayPlan> okayPlans = planRepo.getList();

        responseData.put("user", user);
        responseData.put("okayPlans", okayPlans);

        return "plan/upgrade";
    }

    public String confirm(Long id, ResponseData responseData){
        if(!authService.isAuthenticated()){
            return "[redirect]:/unauthorized";
        }

        User user = authService.getUser();
        String permission = getUserPermission(Long.toString(user.getId()));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/unauthorized";
        }

        OkayPlan okayPlan = planRepo.getPlan(id);
        responseData.put("okayPlan", okayPlan);

        return "plan/confirm";
    }


    public String start(Long id, ResponseData responseData, HttpServletRequest req){
        String username = req.getParameter("username");
        User user = userRepo.getByUsername(username);

        if(!authService.isAuthenticated()){
            return "[redirect]/unauthorized";
        }

        String permission = getUserPermission(Long.toString(user.getId()));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/unauthorized";
        }

        Stripe.apiKey = getApiKey();
        OkayPlan okayPlan = planRepo.getPlan(id);

        try {
            Subscription subscription = com.stripe.model.Subscription.retrieve(user.getStripeSubscriptionId());
            subscription.cancel();

            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("email", user.getUsername());
            customerParams.put("source", user.getStripeToken());
            Customer customer = com.stripe.model.Customer.create(customerParams);

            Map<String, Object> itemParams = new HashMap<>();
            itemParams.put("plan", okayPlan.getStripeId());

            Map<String, Object> itemsParams = new HashMap<>();
            itemsParams.put("0", itemParams);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("customer", customer.getId());
            params.put("items", itemsParams);

            Subscription s = com.stripe.model.Subscription.create(params);

            user.setStripeId(customer.getId());
            user.setOkayPlanId(id);
            user.setStripeSubscriptionId(s.getId());
            userRepo.updatePlan(user);

            responseData.put("message", "Congratulations, you are now ready! " + okayPlan.getNickname() + " for " + okayPlan.getProjectLimit() + " websites!");

        }catch(Exception ex){
            responseData.put("message", "Something went wrong, nothing should have been charged. Please try again, or contact us mail@okay.page");
            String message = user.getUsername() + " " + okayPlan.getNickname();
            mailService.send("croteau.mike@gmail.com", "subscription issue", message);
            ex.printStackTrace();
        }
        return "[redirect]/plan/select";
    }


    public String cancel(HttpServletRequest req){
        Long id = Long.parseLong(req.getParameter("id"));
        User user = userRepo.get(id);

        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }

        String permission = getUserPermission(Long.toString(user.getId()));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/unauthorized";
        }

        try{
            Stripe.apiKey = getApiKey();
            Subscription subscription = com.stripe.model.Subscription.retrieve(user.getStripeSubscriptionId());
            subscription.cancel();
        }catch(Exception e){
            e.printStackTrace();
        }

        user.setStripeSubscriptionId(null);
        user.setOkayPlanId(null);
        userRepo.updatePlan(user);

        return "[redirect]/user/edit/" + user.getId();
    }


    public String list(ResponseData responseData) {
        if(!authService.isAuthenticated()){
            return "[redirect]/unauthorized";
        }
        if(!authService.isAdministrator()){
            return "[redirect]/unauthorized";
        }
        List<OkayPlan> okayPlans = planRepo.getList();
        responseData.put("okayPlans", okayPlans);
        return "plan/index";
    }

    public String create(ResponseData responseData){
        if(!authService.isAuthenticated()){
            return "[redirect]/unauthorized";
        }
        if(!authService.isAdministrator()){
            return "[redirect]/unauthorized";
        }
        responseData.put("page", "/pages/plan/create.jsp");
        return "/designs/auth.jsp";
    }


    public String save(ResponseData responseData, HttpServletRequest req){
        Long id = Long.parseLong(req.getParameter("id"));
        OkayPlan okayPlan = planRepo.getPlan(id);

        if(!authService.isAuthenticated()){
            return "[redirect]/unauthorized";
        }
        if(!authService.isAdministrator()){
            return "[redirect]/unauthorized";
        }
        if(okayPlan.getAmount() > 4200){
            responseData.put("message", "You just entered an amount larger than $42.00");
            return "[redirect]/plan/list";
        }
        if(okayPlan.getNickname().equals("")){
            responseData.put("message", "blank nickname");
            return "[redirect]/plan/list";
        }

        try {

            Stripe.apiKey = getApiKey();

            Map<String, Object> productParams = new HashMap<>();
            productParams.put("name", okayPlan.getNickname());
            productParams.put("type", "service");
            Product stripeProduct = com.stripe.model.Product.create(productParams);

            OkayProduct okayProduct = new OkayProduct();
            okayProduct.setNickname(okayPlan.getNickname());
            okayProduct.setStripeId(stripeProduct.getId());
            OkayProduct savedProduct = planRepo.saveProduct(okayProduct);


            Map<String, Object> planParams = new HashMap<>();
            planParams.put("product", stripeProduct.getId());
            planParams.put("nickname", okayPlan.getNickname());
            planParams.put("interval", okayPlan.getFrequency());
            planParams.put("currency", okayPlan.getCurrency());
            planParams.put("amount", okayPlan.getAmount());
            Plan stripePlan = com.stripe.model.Plan.create(planParams);

            okayPlan.setStripeId(stripePlan.getId());
            okayPlan.setOkayProductId(savedProduct.getId());
            planRepo.savePlan(okayPlan);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return "[redirect]/plan/list";
    }

    public String delete(Long id, ResponseData responseData){
        if(!authService.isAuthenticated()){
            return "[redirect]/unauthorized";
        }
        if(!authService.isAdministrator()){
            return "[redirect]/unauthorized";
        }

        OkayPlan okayPlan = planRepo.getPlan(id);
        OkayProduct okayProduct = planRepo.getProduct(okayPlan.getOkayProductId());

        try{
            Plan plan = com.stripe.model.Plan.retrieve(okayPlan.getStripeId());
            plan.delete();
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            Product product = com.stripe.model.Product.retrieve(okayProduct.getStripeId());
            product.delete();
        }catch(Exception e){
            e.printStackTrace();
        }

        List<User> users = userRepo.getOkayPlanList(okayPlan.getId());
        for(User user : users){
            userRepo.removePlan(user.getId());
        }

        planRepo.deletePlan(okayPlan.getId());
        planRepo.deleteProduct(okayProduct.getId());
        responseData.put("message", "Successfully deleted plan");
        return "[redirect]/plan/list";
    }

    public String cleanup(){
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }
        if(!authService.isAdministrator()){
            return "[redirect]/";
        }
        try {
            Stripe.apiKey = getApiKey();

            Map<String, Object> params = new HashMap<>();
            PlanCollection planCollection = Plan.list(params);
            List<Plan> plans = planCollection.getData();
            for(Plan plan: plans){
                plan.delete();
            }

            ProductCollection productCollection = Product.list(params);
            List<Product> products = productCollection.getData();
            for(Product product: products){
                product.delete();
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return "redirect:/";
    }

    private String getApiKey(){
        return apiKey;
    }

}
