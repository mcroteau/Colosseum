package sigma.service;

import chico.Chico;
import jakarta.servlet.http.HttpServletRequest;
import sigma.Sigma;
import sigma.model.User;
import sigma.repo.UserRepo;
import qio.annotate.Inject;
import qio.annotate.Service;
import qio.model.web.ResponseData;

@Service
public class AuthService {

    @Inject
    private UserRepo userRepo;

    public boolean signin(String username, String password){
        User user = userRepo.getByUsername(username);
        if(user == null) {
            return false;
        }
        return Chico.signin(username, password);
    }

    public boolean signout(){
        return Chico.signout();
    }

    public boolean isAuthenticated(){
        return Chico.isAuthenticated();
    }

    public boolean isAdministrator(){
        return Chico.hasRole(Sigma.SUPER_ROLE);
    }

    public boolean hasPermission(String permission){
        return Chico.hasPermission(permission);
    }

    public boolean hasRole(String role){
        return Chico.hasRole(role);
    }

    public User getUser(){
        String username = Chico.getUser();
        User user = userRepo.getByUsername(username);
        return user;
    }

    public String authenticate(ResponseData data, HttpServletRequest req) {

        try{
            String username = req.getParameter("username");
            String passwordDirty = req.getParameter("password");
            if(!signin(username, passwordDirty)){
                data.put("message", "Wrong username and password");
                return "[redirect]/signin";
            }

            User authdUser = userRepo.getByUsername(username);

            req.getSession().setAttribute("username", authdUser.getUsername());
            req.getSession().setAttribute("userId", authdUser.getId());

        } catch ( Exception e ) {
            e.printStackTrace();
            data.put("message", "Please yell at one of us, something is a little off.");
            return "[redirect]/";
        }

        return "[redirect]/";
    }

    public String deAuthenticate(ResponseData data, HttpServletRequest req) {
        signout();
        data.put("message", "Successfully signed out");
        req.getSession().setAttribute("username", "");
        req.getSession().setAttribute("userId", "");
        return "[redirect]/";
    }
}
