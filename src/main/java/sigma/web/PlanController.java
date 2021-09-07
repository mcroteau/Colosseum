package sigma.web;

import jakarta.servlet.http.HttpServletRequest;
import sigma.service.PlanService;
import qio.annotate.HttpHandler;
import qio.annotate.Inject;
import qio.annotate.Variable;
import qio.annotate.verbs.Get;
import qio.annotate.verbs.Post;
import qio.model.web.ResponseData;

@HttpHandler
public class PlanController {

    @Inject
    PlanService planService;

    @Get("/plan/select")
    public String select(ResponseData data){
        return planService.select(data);
    }

    @Get("/plan/upgrade")
    public String upgrade(ResponseData data){
        return planService.upgrade(data);
    }

    @Get("/plan/confirm/{{id}}")
    public String confirm(ResponseData data,
                          @Variable Long id){
        return planService.confirm(id, data);
    }

    @Post("/plan/start/{{id}}")
    public String start(HttpServletRequest req,
                        ResponseData data,
                        @Variable Long id){
        return planService.start(id, data, req);
    }

    @Post("/plan/cancel")
    public String cancel(HttpServletRequest req,
                         ResponseData data){
        return planService.cancel(req);
    }


    @Get("/plan/create")
    public String create(ResponseData responseData){
        return planService.create(responseData);
    }

    @Post("/plan/save")
    public String save(HttpServletRequest req,
                       ResponseData data){
        return planService.save(data, req);
    }

    @Get("/plan/list")
    public String list(HttpServletRequest req,
                       ResponseData data){
        return planService.list(data);
    }

    @Post("/plan/delete/{{id}}")
    public String delete(HttpServletRequest req,
                         ResponseData data,
                         @Variable Long id){
        return planService.delete(id, data);
    }

    @Get(value="/plan/cleanup")
    public String cleanup(){ return planService.cleanup(); }
}
