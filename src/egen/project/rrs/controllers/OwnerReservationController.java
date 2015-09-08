package egen.project.rrs.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import egen.project.rrs.bo.AuthenticationModel;
import egen.project.rrs.bo.ReservationModel;
import egen.project.rrs.configuration.ExceptionHandler;
import egen.project.rrs.dto.ReservationDetails;
import egen.project.rrs.dto.Tables;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/ownerReservation")
@Api(tags={"/ownerReservation"})
public class OwnerReservationController {
	
	@GET
	@Path("/{restId}/{email}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Get All Reservations",
			notes = "This method retrieves all the reservations for a given restaurant id"
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error"),
			@ApiResponse(code = 401, message="Unauthorized")
	})
	public List<ReservationDetails> getAllReservations(@PathParam("restId") int restId, @PathParam("email") String email,@PathParam("token") String token){
		List<ReservationDetails> reservations = null;
		try {
			if(AuthenticationModel.isValidSession(email, token)){
				ReservationModel bo = new ReservationModel();
				reservations = bo.getAllReservations(restId);
			}else{
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
			
		} catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return reservations;
	}

	@GET
	@Path("/viewSeatingArea/{restId}/{email}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="View Seating Area",
			notes = "This method retrieves the seating tables of the restaurant"
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error"),
			@ApiResponse(code = 401, message="Unauthorized")
	})
	public List<Tables> viewSeatingArea(@PathParam("restId") int restId, @PathParam("email") String email,@PathParam("token") String token){
		List<Tables> tables = null;
		try{
			if(AuthenticationModel.isValidSession(email, token)){
				ReservationModel bo = new ReservationModel();
				tables = bo.getAllTable(restId);
			}else{
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		}catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return tables;
	}
	
	@GET
	@Path("/findReservation/{confId}/{email}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Find Reservation: By Owner",
			notes = "This method retrieves the seating tables of the restaurant"
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error"),
			@ApiResponse(code = 401, message="Unauthorized")
	})
	public ReservationDetails findReservation(@PathParam("confId") int confId, @PathParam("email") String email,@PathParam("token") String token){
		ReservationDetails reservation = null;
		try{
			if(AuthenticationModel.isValidSession(email, token)){
				ReservationModel bo = new ReservationModel();
				reservation = new ReservationDetails();
				reservation.setConfirmationNo(confId);
				reservation = bo.findReservation(reservation);
			}else{
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		}catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return reservation;
	}
	
	@GET
	@Path("/findActiveReservationsOnTable/{tableNo}/{email}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Find reservation on a table",
			notes = "This method finds all the active reservations on a selected table."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error"),
			@ApiResponse(code = 401, message="Unauthorized")
	})
	public List<ReservationDetails> findActiveReservationsOnTable( 
			@PathParam("tableNo") int tableNo,
			@PathParam("email") String email,
			@PathParam("token") String token){
		List<ReservationDetails> reservations = null;
		try{
			if(AuthenticationModel.isValidSession(email, token)){
				
				ReservationModel bo = new ReservationModel();
				reservations = bo.findActiveReservationsOnTable(tableNo);
			}else{
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		}catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return reservations;
	}
	
	
	@POST
	@Path("/makeReservation/{email}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Make Reservation: By Owner",
			notes = "This method is used to creats a reservation. This reservation is created by OWNER"
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error"),
			@ApiResponse(code = 401, message="Unauthorized")
	})
	public ReservationDetails makeReservation(ReservationDetails reservation, @PathParam("email") String email,@PathParam("token") String token){
		try{
			if(AuthenticationModel.isValidSession(email, token)){
				ReservationModel bo = new ReservationModel();
				reservation = bo.makeReservation(reservation);
			}else{
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		}catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return reservation;
	}
	
	@PUT
	@Path("/modifyReservation/{contactModify}/{email}/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Modify Reservation",
			notes = "This method is used to modify existing reservation. The contactModify flag is set to TRUE"
					+ " only when contact no is changed and nothing else. If Contact number is changes and/or other information is"
					+ " changed then contacctModify remains FALSE."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error"),
			@ApiResponse(code = 401, message="Unauthorized")
	})
	public ReservationDetails modifyReservation(ReservationDetails reservation, @PathParam("contactModify") boolean contactModify, @PathParam("email") String email,@PathParam("token") String token){
		try{
			if(AuthenticationModel.isValidSession(email, token)){
				ReservationModel bo = new ReservationModel();
				reservation = bo.modifyReservation(reservation, contactModify);
			}else{
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		}catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return reservation;
	}
	
	@PUT
	@Path("/getAvailableTables/{email}/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Find List Of Available Tables",
			notes = "This method is used to find list of all the available tables in a given restaurant, for the perticular booking time and date."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error"),
			@ApiResponse(code = 401, message="Unauthorized")
	})
	public List<Tables> getAvailableTables(ReservationDetails reservation, @PathParam("email") String email,@PathParam("token") String token){
		List<Tables> tables = null;
		try{
			if(AuthenticationModel.isValidSession(email, token)){
				ReservationModel bo = new ReservationModel();
				tables = bo.getAvailableTables(reservation);
			}else{
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		}catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return tables;
	}
	
	@PUT
	@Path("/assignTable/{email}/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Assign Table",
			notes = "This method is used to assign table to the reservation."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error"),
			@ApiResponse(code = 401, message="Unauthorized")
	})
	public ReservationDetails assignTable(ReservationDetails reservation, @PathParam("email") String email,@PathParam("token") String token){
		try{
			if(AuthenticationModel.isValidSession(email, token)){
				ReservationModel bo = new ReservationModel();
				bo.assignTable(reservation);
			}else{
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		}catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return reservation;
	}
}
