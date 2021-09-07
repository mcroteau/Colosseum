package sigma.repo;

import sigma.model.OkayPlan;
import sigma.model.OkayProduct;
import qio.Qio;
import qio.annotate.DataStore;
import qio.annotate.Inject;

import java.util.ArrayList;
import java.util.List;

@DataStore
public class PlanRepo {

    @Inject
    Qio qio;

    public OkayPlan getLastInsertedPlan() {
        String sql = "select max(id) from plans";
        OkayPlan okayPlan = (OkayPlan) qio.get(sql, new Object[]{}, OkayPlan.class);
        return okayPlan;
    }

    public OkayProduct getLastInsertedProduct() {
        String sql = "select max(id) from products";
        OkayProduct okayProduct = (OkayProduct) qio.get(sql, new Object[]{}, OkayProduct.class);
        return okayProduct;
    }

    public long getCount() {
        String sql = "select count(*) from plans";
        Long count = (Long) qio.get(sql, new Object[] { }, Long.class);
        return count;
    }

    public OkayProduct getProduct(long id){
        String sql = "select * from okay_products where id = [+]";
        OkayProduct okayProduct = (OkayProduct) qio.get(sql, new Object[] { id }, OkayProduct.class);
        return okayProduct;
    }

    public OkayPlan getPlan(long id){
        Object[] variables = new Object[] { id };
        String sql = "select * from okay_plans where id = [+]";
        OkayPlan okayPlan = (OkayPlan) qio.get(sql, variables, OkayPlan.class);
        return okayPlan;
    }

    public List<OkayPlan> getList(){
        String sql = "select * from okay_plans";
        List<OkayPlan> okayPlans = (ArrayList) qio.getList(sql, new Object[]{}, OkayPlan.class);
        return okayPlans;
    }

    public OkayProduct saveProduct(OkayProduct okayProduct){
        String sql = "insert into okay_products (nickname, stripe_id) values ('[+]', '[+]')";
        qio.save(sql, new Object[] {
                okayProduct.getNickname(),
                okayProduct.getStripeId()
        });
        OkayProduct savedProduct = getLastInsertedProduct();
        return savedProduct;
    }

    public boolean savePlan(OkayPlan okayPlan){
        String sql = "insert into okay_plans (amount, nickname, description, project_limit, okay_product_id, stripe_id) values ([+], '[+]', '[+]', [+], [+], '[+]')";
        qio.save(sql, new Object[] {
                okayPlan.getAmount(),
                okayPlan.getNickname(),
                okayPlan.getDescription(),
                okayPlan.getProjectLimit(),
                okayPlan.getOkayProductId(),
                okayPlan.getStripeId()
        });
        return true;
    }

    public boolean deleteProduct(long id){
        String sql = "delete from okay_products where id = [+]";
        qio.delete(sql, new Object[] { id });
        return true;
    }

    public boolean deletePlan(long id){
        String sql = "delete from okay_plans where id = [+]";
        qio.delete(sql, new Object[] { id });
        return true;
    }
}
