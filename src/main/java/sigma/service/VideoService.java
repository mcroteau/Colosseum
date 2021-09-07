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
        setProjectAttributes(video);

        List<ProjectPhone> phones = videoRepo.getPhones(id);
        video.setProjectPhones(phones);

        responseData.put("sigma", video);

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
        video.setName(name);
        video.setUserId(authdUser.getId());
        video.setUri(Sigma.convert(uri));
        video.setInitial(true);

        Video savedVideo = videoRepo.save(video);
        String permission = getPermission(Long.toString(savedVideo.getId()));
        userRepo.savePermission(authdUser.getId(), permission);

        return "[redirect]/video/overview";
    }

    public Status validate(HttpServletRequest req) {
        String uri = Sigma.convert(req.getParameter("uri"));
        Status status = new Status();

        int statusCode = 500;
        double responseTime = 0.0;
        try {
            Date start = new Date();
            URL url = new URL(uri);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(6701);
            statusCode = connection.getResponseCode();
            connection.disconnect();

            Date end = new Date();
            double diff = end.getTime() - start.getTime();
            responseTime = diff/1000;

        }catch(Exception e){ }

        status.setStatus(statusCode);
        status.setResponseTime(responseTime);

        return status;
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
        responseData.put("sigma", video);

        responseData.put("page", "/pages/video/edit.jsp");
        return "/designs/auth.jsp";
    }

    public String update(ResponseData responseData, HttpServletRequest req) {
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }
        Long id = Long.parseLong(req.getParameter("id"));
        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }
        Video storedVideo = videoRepo.get(id);
        storedVideo.setName(req.getParameter("name"));

        if(storedVideo.getName().equals("")){
            responseData.put("message", "Please give your web project a name...");
            return "[redirect]/project/edit/" + storedVideo.getId();
        }

        responseData.put("message", "Successfully updated project");
        videoRepo.update(storedVideo);

        return "[redirect]/project/edit/" + storedVideo.getId();
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

        videoRepo.deleteProjectStatus(id);
        videoRepo.deleteProjectPhones(id);
        videoRepo.deleteProjectEmails(id);
        videoRepo.delete(id);

        responseData.put("message", "Successfully deleted project.");

        return "[redirect]/project/overview";
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

        responseData.put("page", "/pages/project/list.jsp");
        return "/designs/auth.jsp";
    }

    public String getOverview(ResponseData responseData) {
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }

        User user = authService.getUser();

        List<Video> videos = videoRepo.getOverview(user.getId());
        videos.stream().forEach(this::setProjectAttributes);

        responseData.put("videos", videos);

        responseData.put("page", "/pages/project/overview.jsp");
        return "/designs/auth.jsp";
    }


    public String addPhone(Long id, ResponseData responseData) {
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }

        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/unauthorized";
        }

        Video video = videoRepo.get(id);
        responseData.put("sigma", video);

        responseData.put("page", "/pages/video/phone.jsp");
        return "/designs/auth.jsp";
    }

    public String addPhone(Long id, ResponseData responseData, HttpServletRequest req) {
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }

        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }

        ProjectPhone projectPhone = new ProjectPhone();
        projectPhone.setPhone(req.getParameter("phone").trim());

        if(projectPhone.getPhone().contains(" ")){
            responseData.put("message", "Phone cannot contain spaces");
            return "[redirect]/project/phone/add/" + id;
        }

        if(Sigma.containsSpecialCharacters(projectPhone.getPhone())){
            responseData.put("message", "Phone cannot contain special characters, only numbers");
            return "[redirect]/project/phone/add/" + id;
        }

        if(!smsService.validate(projectPhone.getPhone())){
            responseData.put("message", "We were unable to send a message to that number, want to try again?");
            return "[redirect]/project/phone/add/" + id;
        }

        projectPhone.setProjectId(id);
        videoRepo.addPhone(projectPhone);

        responseData.put("message", "Successfully added phone number");

        return "[redirect]/project/" + id;
    }

    public String deletePhone(Long id, ResponseData responseData) {
        String permission = getPhonePermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }
        videoRepo.deletePhone(id);
        return "[redirect]/project/" + id;
    }

    public String addEmail(Long id, ResponseData responseData) {
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }

        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/unauthorized";
        }

        Video video = videoRepo.get(id);
        responseData.put("sigma", video);

        responseData.put("page", "/pages/video/email.jsp");
        return "/designs/auth.jsp";
    }

    public String addEmail(Long id, ResponseData responseData, HttpServletRequest request) {
        ProjectEmail projectEmail = new ProjectEmail();
        projectEmail.setEmail(request.getParameter("email"));
        projectEmail.setProjectId(id);

        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }

        if(!Sigma.isValidMailbox(projectEmail.getEmail())){
            responseData.put("message", "Please enter a valid email");
            return "[redirect]/project/email/add/" + id;
        }

        videoRepo.addEmail(projectEmail);
        return "[redirect]/project/" + id;
    }

    public String deleteEmail(Long id, ResponseData responseData) {
        String permission = getEmailPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }

        videoRepo.deleteEmail(id);
        return "[redirect]/project/" + id;
    }

}
