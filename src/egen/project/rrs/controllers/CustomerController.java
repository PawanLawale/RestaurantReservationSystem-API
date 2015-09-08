package egen.project.rrs.controllers;

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

import egen.project.rrs.bo.ReservationModel;
import egen.project.rrs.configuration.ExceptionHandler;
import egen.project.rrs.dto.ReservationDetails;
import egen.project.rrs.dto.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/customer")
@Api(tags={"/customer"})
public class CustomerController {

	@POST
	@Path("/reserveMySeat")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Make New Reservation",
			notes = "This method is used to make new table reservation in an restaurant. "
					+ "If auto assign is on in the database then it checks if any table is available to reserve automatically and reserves the best suitable table."
					+ "If no table is available, it sends an waiting status to ask user to confirm if they want to be on waiting status."
					+ "If auto assign is off it sends a waiting status again to ask user to confirm if they want to be on waiting status."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error")
	})
	public ReservationDetails makeReservation(ReservationDetails reservation){
		try{
			ReservationModel bo = new ReservationModel();
			bo.makeReservation(reservation);
		}catch(ExceptionHandler e){
			throw new WebApplicationException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
		}
		
		return reservation;
	}
	
	@POST
	@Path("/confirmMySeatCreate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Confirm Reservation (During creat reservation)",
			notes = "This method is used to confirm the reservation details while creating new reservation."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error")
	})
	public ReservationDetails confirmReservationCreate(ReservationDetails reservation){
		try{
			ReservationModel bo = new ReservationModel();
			bo.confirmReservationCreate(reservation);
		}catch(ExceptionHandler e){
			throw new WebApplicationException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
		}
		return reservation;
	}
	
	@PUT
	@Path("/confirmMySeatModify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Confirm Reservation (During modifying reservation)",
			notes = "This method is used to confirm the reservation details while modifying new reservation."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error")
	})
	public ReservationDetails confirmReservationModify(ReservationDetails reservation){
		try{
			ReservationModel bo = new ReservationModel();
			bo.confirmReservationModify(reservation);
		}catch(ExceptionHandler e){
			throw new WebApplicationException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
		}
		return reservation;
	}
	
	@PUT
	@Path("/modifyMyReservation/{contactModify}")
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
			@ApiResponse(code = 500, message="Internal Server Error")
	})
	public ReservationDetails modifyReservation(ReservationDetails reservation,@PathParam("contactModify") boolean contactModify){
		try{
			ReservationModel bo = new ReservationModel();
			bo.modifyReservation(reservation, contactModify);
		}catch(ExceptionHandler e){
			throw new WebApplicationException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
		}
		
		return reservation;
	}
	
	@PUT
	@Path("/cancelMyReservation/{confNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Cancel Reservation",
			notes = "This method is used to cancel the reservation."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error")
	})
	public ResponseMessage cancelReservation(@PathParam("confNo") int confNo){
		ResponseMessage message;
		try{
			ReservationModel bo = new ReservationModel();
			message = bo.cancelReservation(confNo);
		}catch(ExceptionHandler e){
			throw new WebApplicationException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
		}
		return message;
	}
	
	@GET
	@Path("/findMyReservation/{confNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value="Search Reservation",
			notes = "This method is used to find the reservation based on the confirmation Number."
	)
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Success"),
			@ApiResponse(code = 500, message="Internal Server Error")
	})
	public ReservationDetails viewReservation(@PathParam("confNo") int confNo){
		ReservationDetails reservation = new ReservationDetails();
		try{
			reservation.setConfirmationNo(confNo);
			ReservationModel bo = new ReservationModel();
			reservation = bo.findReservation(reservation);
		}catch(ExceptionHandler e){
			throw new WebApplicationException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
		}
		return reservation;
	}
	
	
	
}
