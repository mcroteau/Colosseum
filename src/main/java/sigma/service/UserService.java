package sigma.service;

import chico.Chico;
import jakarta.servlet.http.HttpServletRequest;
import sigma.Sigma;
import sigma.model.Role;
import sigma.model.User;
import sigma.repo.RoleRepo;
import sigma.repo.UserRepo;
import qio.annotate.Inject;
import qio.annotate.Service;
import qio.model.web.ResponseData;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;

@Service
public class UserService {

    @Inject
    UserRepo userRepo;

    @Inject
    RoleRepo roleRepo;

    @Inject
    AuthService authService;


    private String getPermission(String id){
        return Sigma.USER_MAINTENANCE + id;
    }

    public String getUsers(ResponseData responseData){
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }
        if(!authService.isAdministrator()){
            responseData.put("message", "You must be a super user in order to access accounts.");
            return "[redirect]/";
        }

        List<User> users = userRepo.findAll();
        responseData.put("users", users);

        responseData.put("page", "/pages/user/index.jsp");
        return "/designs/auth.jsp";
    }

    public String getEditUser(Long id, ResponseData responseData){
        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }

        User user = userRepo.get(id);
        responseData.put("user", user);

        responseData.put("page", "/pages/user/edit.jsp");
        return "/designs/auth.jsp";
    }


    public String editPassword(Long id, ResponseData responseData) {

        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() ||
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }

        User user = userRepo.get(id);
        responseData.put("user", user);

        responseData.put("page", "/pages/user/password.jsp");
        return "/designs/auth.jsp";
    }


    public String updatePassword(User user, ResponseData responseData) {

        String permission = getPermission(Long.toString(user.getId()));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }

        if(user.getPassword().length() < 7){
            responseData.put("message", "Passwords must be at least 7 characters long.");
            return "[redirect]/signup";
        }

        if(!user.getPassword().equals("")){
            user.setPassword(Chico.dirty(user.getPassword()));
            userRepo.updatePassword(user);
        }

        responseData.put("message", "password successfully updated");
        Long id = authService.getUser().getId();
        return "[redirect]/user/edit_password/" + id;

    }

    public String deleteUser(Long id, ResponseData responseData) {
        if(!authService.isAdministrator()){
            responseData.put("message", "You don't have permission");
            return "[redirect]/";
        }

        responseData.put("message", "Successfully deleted user");
        return "[redirect]/admin/users";
    }

    public String register(HttpServletRequest req, ResponseData responseData) {


        Enumeration<String> parameters = req.getParameterNames();
        while(parameters.hasMoreElements()){
            System.out.println(parameters.nextElement());
        }

        String username = req.getParameter("username");
        String rawPassword = req.getParameter("password");

        if(username == null ||
                username.equals("")){
            responseData.put("message", "please enter a username.");
            return "[redirect]/signup";
        }

        User existingUser = userRepo.getByUsername(username);
        if(existingUser != null){
            responseData.put("message", "User exists with same username.");
            return "[redirect]/signup";
        }

        if(rawPassword == null ||
                rawPassword.equals("")) {
            responseData.put("message", "Password cannot be blank");
            return "[redirect]/signup";
        }

        if(rawPassword.length() < 7){
            responseData.put("message", "Password must be at least 7 characters long.");
            return "[redirect]/signup";
        }

        String passwordHashed = Chico.dirty(rawPassword);

        try{
            User user = new User();

            user.setUsername(username);
            user.setPassword(passwordHashed);
            user.setDateCreated(Sigma.getDate());
            userRepo.save(user);

            User savedUser = userRepo.getByUsername(user.getUsername());
            Role defaultRole = roleRepo.find(Sigma.USER_ROLE);

            userRepo.saveUserRole(savedUser.getId(), defaultRole.getId());
            String permission = getPermission(Long.toString(savedUser.getId()));
            userRepo.savePermission(savedUser.getId(), permission);


        }catch(Exception e){
            e.printStackTrace();
            responseData.put("message", "Will you contact us? Email us with the subject, support@amadeus.social. Our programmers missed something. Gracias!");
            return("[redirect]/signup");
        }

        if(!authService.signin(username, rawPassword)) {
            responseData.put("message", "Thank you for registering. We hope you enjoy!");
            return "[redirect]/";
        }

        req.getSession().setAttribute("username", username);
        req.getSession().setAttribute("userId", authService.getUser().getId());

        return "[redirect]/";
    }

    public String sendReset(ResponseData responseData, HttpServletRequest req) {

        try {
            String username = req.getParameter("username");
            User user = userRepo.getByUsername(username);
            if (user == null) {
                responseData.put("message", "Unable to find user.");
                return ("[redirect]/user/reset");
            }

            String resetUuid = Sigma.getString(13);
            user.setUuid(resetUuid);
            userRepo.updateUuid(user);

            StringBuffer url = req.getRequestURL();

            String[] split = url.toString().split(req.getContextPath());
            String httpSection = split[0];

            String resetUrl = httpSection + req.getContextPath() + "/user/confirm?";
            String params = "username=" + URLEncoder.encode(user.getUsername(), "utf-8") + "&uuid=" + resetUuid;
            resetUrl += params;

            String body = "<p>Reset password</p>" +
                    "<p><a href=\"" + resetUrl + "\">" + resetUrl + "</a></p>";


        }catch(Exception e){
            e.printStackTrace();
        }

        responseData.put("page", "/pages/user/send.jsp");
        return "/designs/auth.jsp";
    }

    public String confirm(ResponseData responseData, HttpServletRequest req) {

        String uuid = req.getParameter("uuid");
        String username = req.getParameter("username");

        User user = userRepo.getByUsernameAndUuid(username, uuid);
        if (user == null) {
            responseData.put("error", "Unable to locate user.");
            return "[redirect]/user/reset";
        }

        responseData.put("user", user);

        responseData.put("page", "/pages/user/confirm.jsp");
        return "/designs/auth.jsp";
    }

    public String resetPassword(Long id, ResponseData responseData, HttpServletRequest req) {

        User user = userRepo.get(id);
        String uuid = req.getParameter("uuid");
        String username = req.getParameter("username");
        String rawPassword = req.getParameter("password");

        if(rawPassword.length() < 7){
            responseData.put("message", "Passwords must be at least 7 characters long.");
            return "[redirect]/user/confirm?username=" + username + "&uuid=" + uuid;
        }

        if(!rawPassword.equals("")){
            String password = Chico.dirty(rawPassword);
            user.setPassword(password);
            userRepo.updatePassword(user);
        }

        responseData.put("message", "Password successfully updated");

        responseData.put("page", "/pages/user/success.jsp");
        return "/designs/auth.jsp";
    }

}
