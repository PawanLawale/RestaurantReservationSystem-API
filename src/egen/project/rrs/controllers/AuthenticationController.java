package egen.project.rrs.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import egen.project.rrs.bo.AuthenticationModel;
import egen.project.rrs.configuration.ExceptionHandler;
import egen.project.rrs.dto.LoginDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/authenticate")
@Api(tags={"/authenticate"})
public class AuthenticationController {

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Authenticate User",
			notes = "This method authenticate the user and sends the authentication token to the client if successful."
					+ "Else it returns the failur message to the user."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error")
	})
	public LoginDetails authenticateUser(LoginDetails loginDetails){
		try{
			AuthenticationModel model = new AuthenticationModel();
			model.authenticateUser(loginDetails);
		}catch(ExceptionHandler e){
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		
		return loginDetails;
	}
	
	@DELETE
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Logout User",
			notes = "This method deauthenticates the user."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error")
	})
	public LoginDetails logoutUser(LoginDetails details){
		AuthenticationModel model = new AuthenticationModel();
		model.logoutUser(details);
		return details;
	}
	
}
