package sigma.web;

import jakarta.servlet.http.HttpServletRequest;
import sigma.service.AuthService;
import qio.annotate.HttpHandler;
import qio.annotate.Inject;
import qio.annotate.verbs.Get;
import qio.annotate.verbs.Post;
import qio.model.web.ResponseData;

@HttpHandler
public class AuthHandler {

	@Inject
    AuthService authService;

	@Post("/authenticate")
	public String authenticate(HttpServletRequest req,
							   ResponseData data){
		return authService.authenticate(data, req);
	}

	@Get("/signin")
	public String signin(ResponseData responseData){
		responseData.put("title", "Okay! Signin");
		responseData.put("page", "/pages/signin.jsp");
		return "/designs/guest.jsp";
	}

	@Get("/signup")
	public String signup(HttpServletRequest req,
						 ResponseData responseData){
		responseData.put("p", req.getParameter("p"));
		responseData.put("title", "Okay! Sign UP!");
		responseData.put("page", "/pages/signup.jsp");
		return "/designs/guest.jsp";
	}

	@Get("/signout")
	public String signout(HttpServletRequest req,
						  ResponseData data){
		return authService.deAuthenticate(data, req);
	}

}