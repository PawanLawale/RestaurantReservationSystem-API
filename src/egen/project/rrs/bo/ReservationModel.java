package egen.project.rrs.bo;

import java.util.ArrayList;
import java.util.List;

import egen.project.rrs.configuration.ExceptionHandler;
import egen.project.rrs.dao.ReservationDAO;
import egen.project.rrs.dto.ReservationDetails;
import egen.project.rrs.dto.ResponseMessage;
import egen.project.rrs.dto.Tables;

public class ReservationModel {

	/**check if auto assign is ON. 
	 * If NO, then return "waiting" status to the user and ask for confirmation.
	 * If YES, then check if any table is available:
	 * 		-If YES assign that table and return Confirmation Code to user.
	 * 		-If NO then return a waiting status to the user and ask for confirmation.
	 * @throws ExceptionHandler */ 
	public ReservationDetails makeReservation(ReservationDetails reservation) throws ExceptionHandler{
		if(isAutoAssign(reservation)){
			if(isTableAvailable(reservation)){
				ReservationDAO dao = new ReservationDAO();
				reservation.setStatus("Confirm");
				dao.createReservation(reservation);
			}else{
				reservation.setStatus("Waiting");
			}
		}else{
			reservation.setStatus("Waiting");
		}
		return reservation;
	}
	
	/**
	 * Check if the auto assign flag is ON or NOT. 
	 * @param reservation
	 * @return True, if auto assign is ON. Else false
	 * @throws ExceptionHandler
	 */
	private boolean isAutoAssign(ReservationDetails reservation) throws ExceptionHandler{
		boolean flag = false;
		ReservationDAO dao = new ReservationDAO();
		flag = dao.isAutoAssign(reservation);
		return flag;
	}
	
	/**
	 * Check if the tables are available for the party size. 
	 * It finds the best suitable table for the given party size.
	 * @param reservation
	 * @return True if the table is available for reservation, else false.
	 * @throws ExceptionHandler
	 */
	private boolean isTableAvailable(ReservationDetails reservation) throws ExceptionHandler{
		boolean flag = false;
		ReservationDAO dao = new ReservationDAO();
		ArrayList<Integer> tables = dao.getSuitableTables(reservation);
		if(tables.size()>0){
			reservation = dao.findSuitableTable(reservation, tables);
			if(reservation.getAssignedTableNo() != 0){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * Find reservation based on confirmation code
	 * @param reservation
	 * @return reservation details
	 * @throws ExceptionHandler
	 */
	public ReservationDetails findReservation(ReservationDetails reservation) throws ExceptionHandler{
		ReservationDAO dao = new ReservationDAO();
		reservation = dao.findReservation(reservation);
		return reservation;
	}
	
	/**
	 * This method represents the logic of modifying the reservation.
	 * @param reservation
	 * @param contactModify
	 * @return reservation details
	 * @throws ExceptionHandler
	 */
	public ReservationDetails modifyReservation(ReservationDetails reservation, boolean contactModify) throws ExceptionHandler{
		ReservationDAO dao = new ReservationDAO();
		if(contactModify){
			dao.modifyReservation(reservation,contactModify);
		}else{
			
			if(isAutoAssign(reservation)){
				reservation.setAssignedTableNo(0);
				if(isTableAvailable(reservation)){
					reservation.setStatus("Confirm");
					dao.modifyReservation(reservation,contactModify);
				}else{
					reservation.setAssignedTableNo(0);
					reservation.setStatus("Waiting");
				}
			}else{
				reservation.setAssignedTableNo(0);
				reservation.setStatus("Waiting");
			}
		}
		return reservation;
	}
	
	/**
	 * This method is calls a method in DAO to update the reservation status to Cancel
	 * @param confNo
	 * @return ResponseMessage
	 * @throws ExceptionHandler
	 */
	public ResponseMessage cancelReservation(int confNo) throws ExceptionHandler{
		ResponseMessage message = null;
		ReservationDAO dao = new ReservationDAO();
		message = dao.cancelReservation(confNo);
		return message;
		
	}
	
	/**
	 * This method calls DAO's createReservation() method after
	 * confirmation by user for the reservation which is in Waiting state. 
	 * @param reservation
	 * @throws ExceptionHandler
	 */
	public void confirmReservationCreate(ReservationDetails reservation) throws ExceptionHandler{
		ReservationDAO dao = new ReservationDAO();
		dao.createReservation(reservation);
	}
	
	/**
	 * This method calls DAO's modifyReservation() method after
	 * user confirmed to modify there existing reservation to waiting state.
	 * @param reservation
	 * @throws ExceptionHandler
	 */
	public void confirmReservationModify(ReservationDetails reservation) throws ExceptionHandler{
		ReservationDAO dao = new ReservationDAO();
		dao.modifyReservation(reservation,false);
	}
	
	/**
	 * This method calls DAO's getAllReservation method
	 * which fetches all the reservations for the restaurant.
	 * @return: List of all the reservations for the restaurant.
	 * @throws ExceptionHandler 
	 */
	public List<ReservationDetails> getAllReservations(int id) throws ExceptionHandler{
		ReservationDAO dao = new ReservationDAO();
		List<ReservationDetails> reservations = dao.getAllReservations(id);
		return reservations;
	}

	/**
	 * This method calls DAO's getTables method to fetch all
	 * the tables of the given restaurant.
	 * @param restId
	 * @return
	 * @throws ExceptionHandler
	 */
	public List<Tables> getAllTable(int restId) throws ExceptionHandler {
		ReservationDAO dao = new ReservationDAO();
		List<Tables> tables = dao.getTables(restId);
		return tables;
	}

	/**
	 * Finds available list of tables.
	 * @param reservation
	 * @return
	 * @throws ExceptionHandler
	 */
	public List<Tables> getAvailableTables(ReservationDetails reservation) throws ExceptionHandler {
		ReservationDAO dao = new ReservationDAO();
		List<Tables> tables = dao.getAvailableTables(reservation);
		return tables;
	}

	/**
	 * This method assigns a table to the reservation.
	 * @param reservation
	 * @throws ExceptionHandler 
	 */
	public void assignTable(ReservationDetails reservation) throws ExceptionHandler {
		ReservationDAO dao = new ReservationDAO();
		dao.assignTable(reservation);
	}

	/**
	 * This method finds active reservations on a selected Table
	 * @param list of reservations
	 * @throws ExceptionHandler 
	 */
	public List<ReservationDetails> findActiveReservationsOnTable(int tableNo) throws ExceptionHandler {
		ReservationDAO dao = new ReservationDAO();
		return dao.findActiveReservationsOnTable(tableNo);
		
	}
}
