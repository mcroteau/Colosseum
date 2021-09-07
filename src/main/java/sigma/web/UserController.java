package sigma.web;

import jakarta.servlet.http.HttpServletRequest;
import sigma.service.UserService;
import qio.annotate.HttpHandler;
import qio.annotate.Inject;
import qio.annotate.Variable;
import qio.annotate.verbs.Get;
import qio.annotate.verbs.Post;
import qio.model.web.ResponseData;


@HttpHandler
public class UserController {

	@Inject
	UserService userService;

	@Get("/users")
	public String getUsers(ResponseData data){
		return userService.getUsers(data);
	}

	@Get("/users/edit/{{id}}")
	public String getEditUser(ResponseData data,
							  @Variable Long id){
		return userService.getEditUser(id, data);
	}

	@Post("/users/delete/{{id}}")
	public String deleteUser(ResponseData data,
							 @Variable Long id) {
		return userService.deleteUser(id, data);
	}

	@Post("/register")
	public String register(HttpServletRequest req,
						   ResponseData data){
    	return userService.register(req, data);
	}

	@Get("/users/reset")
	public String reset(){
		return "/pages/user/reset.jsp";
	}


	@Post("/users/send")
	public String sendReset(HttpServletRequest req,
							ResponseData data){
		return userService.sendReset(data, req);
	}

	@Get("/users/confirm")
	public String confirm(HttpServletRequest req,
						  ResponseData data){
		return userService.confirm(data, req);
	}

	@Post("/users/reset/{{id}}")
	public String resetPassword(HttpServletRequest req,
								ResponseData data,
								@Variable Long id){
    	return userService.resetPassword(id, data, req);
	}

}