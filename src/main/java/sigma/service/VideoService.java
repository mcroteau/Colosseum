package sigma.service;

import sigma.Sigma;
import sigma.repo.VideoRepo;
import jakarta.servlet.http.HttpServletRequest;
import sigma.repo.UserRepo;
import sigma.model.*;
import qio.annotate.Inject;
import qio.annotate.Service;
import qio.model.web.ResponseData;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Service
public class VideoService {

    @Inject
    UserRepo userRepo;

    @Inject
    VideoRepo videoRepo;

    @Inject
    AuthService authService;

    @Inject
    SmsService smsService;

    @Inject
    MailService emailService;

    public String getPermission(String id){
        return Sigma.VIDEO_MAINTENANCE + id;
    }

    public String create(ResponseData responseData) {
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }

        responseData.put("page", "/pages/project/create.jsp");
        return "/designs/auth.jsp";
    }

    public String index(Long id, ResponseData responseData) {
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }

        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }

        Video video = videoRepo.get(id);
        responseData.put("video", video);

        responseData.put("page", "/pages/video/index.jsp");
        return "/designs/auth.jsp";
    }

    public String save(ResponseData responseData, HttpServletRequest req) {

        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }

        String name = req.getParameter("name");
        String uri = req.getParameter("uri");

        if(name.equals("")){
            responseData.put("message", "Please give your web video a name...");
            return "[redirect]/video/create";
        }

        User authdUser = authService.getUser();
        Video video = new Video();


        videoRepo.save(video);
        Video savedVideo = videoRepo.getSaved();
        String permission = getPermission(Long.toString(savedVideo.getId()));
        userRepo.savePermission(authdUser.getId(), permission);

        return "[redirect]/video/edit/" + savedVideo.getId();
    }

    public String getEdit(Long id, ResponseData responseData) {
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }

        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/unauthorized";
        }

        Video video = videoRepo.get(id);
        responseData.put("video", video);

        responseData.put("page", "/pages/video/edit.jsp");
        return "/designs/auth.jsp";
    }

    public String update(Long id, ResponseData responseData, HttpServletRequest req) {
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }
        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }
        Video storedVideo = videoRepo.get(id);
        storedVideo.setAmount(Integer.valueOf(req.getParameter("amount")));

        if(storedVideo.getTitle().equals("")){
            responseData.put("message", "Please give your lecture a title...");
            return "[redirect]/video/edit/" + storedVideo.getId();
        }

        responseData.put("message", "Successfully updated project");
        videoRepo.update(storedVideo);

        return "[redirect]/video/edit/" + storedVideo.getId();
    }

    public String delete(Long id, ResponseData responseData) {
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }

        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/unauthorized";
        }

        videoRepo.delete(id);
        responseData.put("message", "Successfully deleted video.");

        return "[redirect]/video/overview";
    }

    public String getProjects(ResponseData responseData) {
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }

        if(!authService.isAdministrator()){
            return "[redirect]/";
        }

        List<Video> videos = videoRepo.getList();
        responseData.put("videos", videos);

        responseData.put("page", "/pages/video/list.jsp");
        return "/designs/auth.jsp";
    }

}
