package egen.project.rrs.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import egen.project.rrs.bo.AuthenticationModel;
import egen.project.rrs.bo.RestaurantProfileModel;
import egen.project.rrs.configuration.ExceptionHandler;
import egen.project.rrs.dto.ReservationDetails;
import egen.project.rrs.dto.RestaurantProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/restaurantProfile")
@Api(tags={"/restaurantProfile"})
public class RestaurantProfileController {

	@GET
	@Path("/{restId}/{email}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Get Restaurant Details",
			notes = "This method retrives restaurant details."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error"),
			@ApiResponse(code = 401, message="Unauthorized")
	})
	public RestaurantProfile getRestaurantProfile(@PathParam("restId") int restId, @PathParam("email") String email,@PathParam("token") String token){
		RestaurantProfile profile = null;
		try {
			if(AuthenticationModel.isValidSession(email, token)){
				RestaurantProfileModel bo = new RestaurantProfileModel();
				profile = bo.getRestaurantProfile(restId);
			}else{
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
			
		} catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return profile;
	}
	
	@GET
	@Path("/guestRecord/{contactNo}/{email}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Get Guest Record",
			notes = "This method retrives guest records based on contact number."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error"),
			@ApiResponse(code = 401, message="Unauthorized")
	})
	public List<ReservationDetails> getguestRecord(@PathParam("contactNo") long contactNo, @PathParam("email") String email,@PathParam("token") String token){
		List<ReservationDetails> profile = null;
		try {
			if(AuthenticationModel.isValidSession(email, token)){
				RestaurantProfileModel bo = new RestaurantProfileModel();
				profile = bo.getGuestRecord(contactNo);
			}else{
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
			
		} catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return profile;
	}
	
	@PUT
	@Path("/update/{email}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Update Restaurant Profile",
			notes = "This method updates restaurant profile."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error"),
			@ApiResponse(code = 401, message="Unauthorized")
	})
	public RestaurantProfile updateRestaurantProfile(RestaurantProfile profile,@PathParam("email") String email,@PathParam("token") String token){
		try {
			if(AuthenticationModel.isValidSession(email, token)){
				RestaurantProfileModel bo = new RestaurantProfileModel();
				bo.updateRestaurantProfile(profile);
			}else{
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
			
		} catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return profile;
	}
	
	
}
