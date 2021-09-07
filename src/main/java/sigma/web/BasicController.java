package sigma.web;

import sigma.service.AuthService;
import qio.annotate.HttpHandler;
import qio.annotate.Inject;
import qio.annotate.verbs.Get;
import qio.model.web.ResponseData;

@HttpHandler
public class BasicController {

	@Inject
	AuthService authService;

	@Get("/")
	public String index(){
		if(authService.isAuthenticated()){
			return "[redirect]/project/overview";
		}
		return "[redirect]/home";
	}

	@Get("/home")
	public String home(ResponseData responseData){
		responseData.put("title", "Okay! Http Monitoring");
		responseData.put("page", "/pages/index.jsp");
		return "/designs/guest.jsp";
	}

	@Get("/pricing")
	public String pricing(ResponseData responseData){
		responseData.put("title", "Okay! Pricing");
		responseData.put("page", "/pages/pricing.jsp");
		return "/designs/guest.jsp";
	}

	@Get("/unauthorized")
	public String unauthorized(ResponseData responseData){
		responseData.put("page", "/pages/unauthorized.jsp");
		return "/designs/guest.jsp";
	}

}