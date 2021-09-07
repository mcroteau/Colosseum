package sigma.web;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import sigma.service.VideoService;
import qio.annotate.HttpHandler;
import qio.annotate.Inject;
import qio.annotate.Variable;
import qio.annotate.verbs.Get;
import qio.annotate.verbs.Post;
import qio.model.web.ResponseData;

@HttpHandler
public class VideoHandler {

    Gson gson = new Gson();

    @Inject
    VideoService videoService;

    @Get("/project/create")
    public String index(ResponseData data){
        return videoService.create(data);
    }

    @Get("/project/{{id}}")
    public String index(ResponseData data,
                        @Variable Long id){
        return videoService.index(id, data);
    }

    @Post("/project/save")
    protected String save(HttpServletRequest req,
                          ResponseData data){
        return videoService.save(data, req);
    }

    @Get("/admin/project/list")
    public String getProjects(ResponseData data){
        return videoService.getProjects(data);
    }


    @Get("/project/edit/{{id}}")
    public String getEdit(HttpServletRequest req,
                          ResponseData data,
                          @Variable Long id){
        return videoService.getEdit(id, data);
    }

    @Post("/project/update/{{id}}")
    protected String update(HttpServletRequest req,
                            ResponseData data,
                            @Variable Long id){
        return videoService.update(id, data, req);
    }

    @Post("/project/delete/{{id}}")
    protected String delete(ResponseData data,
                            @Variable Long id){
        return videoService.delete(id, data);
    }

    @Get("/project/phone/add/{{id}}")
    public String addPhoneView(ResponseData data,
                               @Variable Long id){
        return videoService.addPhone(id, data);
    }

    @Post("/project/phone/add/{{id}}")
    protected String addPhone(HttpServletRequest req,
                              ResponseData data,
                              @Variable Long id){
        return videoService.addPhone(id, data, req);
    }

    @Post("/project/phone/delete/{{id}}")
    protected String deletePhone(ResponseData data,
                                 @Variable Long id){
        return videoService.deletePhone(id, data);
    }

    @Get("/project/email/add/{{id}}")
    public String addEmailView(ResponseData data,
                               @Variable Long id){
        return videoService.addEmail(id, data);
    }

    @Post("/project/email/add/{{id}}")
    protected String addEmail(HttpServletRequest req,
                              ResponseData data,
                              @Variable Long id){
        return videoService.addEmail(id, data, req);
    }

    @Post("/project/email/delete/{{id}}")
    protected String deleteEmail(ResponseData data,
                                 @Variable Long id){
        return videoService.deleteEmail(id, data);
    }

}
