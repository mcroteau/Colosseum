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

}
