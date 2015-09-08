package egen.project.rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import egen.project.rrs.configuration.ExceptionHandler;
import egen.project.rrs.dto.ReservationDetails;
import egen.project.rrs.dto.ResponseMessage;
import egen.project.rrs.dto.Tables;
import egen.project.rrs.utils.DBUtils;

public class ReservationDAO {

	/**
	 * This method checks in the database if the AutoAssign flag is ON or OFF
	 * @param reservation
	 * @return
	 * @throws ExceptionHandler
	 */
	public boolean isAutoAssign(ReservationDetails reservation) throws ExceptionHandler{
		boolean flag = false;
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			
			ps = conn.prepareStatement("SELECT AutoAssign FROM RestaurantDetails WHERE RestaurantId=?");
			ps.setInt(1,reservation.getRestaurantId());
			rs = ps.executeQuery();
			
			if(rs.next()){
				if(rs.getString("AutoAssign").equals("Y")){
					flag = true;
				}
			}else{
				System.err.println("Restaurant does not exist.");
				throw new ExceptionHandler("Restaurant does not exist.");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		return flag;
	}
	
	/**
	 * This method find all the tables sufficing the need of the party size.
	 * @param reservation
	 * @return
	 * @throws ExceptionHandler
	 */
	public ArrayList<Integer> getSuitableTables(ReservationDetails reservation) throws ExceptionHandler{
		ArrayList<Integer> tables = new ArrayList<Integer>();
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			
			ps = conn.prepareStatement("SELECT TableNo FROM RestaurantTables WHERE RestaurantId=? AND NoOfSeats >= ? ORDER BY NoOfSeats");
			ps.setInt(1,reservation.getRestaurantId());
			ps.setInt(2, reservation.getPartySize());
			rs = ps.executeQuery();
			while(rs.next()){
				tables.add(rs.getInt("TableNo"));
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		return tables;
	}
	
	/**
	 * This method the most suitable table to be assigned to the party size.
	 * @param reservation
	 * @param tables
	 * @return
	 * @throws ExceptionHandler
	 */
	public ReservationDetails findSuitableTable(ReservationDetails reservation, ArrayList<Integer> tables) throws ExceptionHandler{
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			for(Integer tableNo : tables){
				/*String sql = "SELECT * FROM ReservationDetails WHERE RestaurantId="+reservation.getRestaurantId()+
						" AND BookingDate = "+reservation.getBookingDate()+
						" AND BookingTime BETWEEN SUBTIME("+reservation.getBookingTime()+
						",'01:00:00') AND ADDTIME("+reservation.getBookingTime()+",'01:00:00')";
				System.out.println(sql);*/
				ps = conn.prepareStatement("SELECT * FROM ReservationDetails WHERE RestaurantId=? AND BookingDate = ? AND AssignedTableNo = ? AND BookingTime BETWEEN SUBTIME(?,'01:00:00') AND ADDTIME(?,'01:00:00') AND Status <> 'Cancel'");
				ps.setInt(1, reservation.getRestaurantId());
				ps.setString(2, reservation.getBookingDate());
				ps.setInt(3, tableNo);
				ps.setString(4, reservation.getBookingTime());
				ps.setString(5, reservation.getBookingTime());
				
				rs = ps.executeQuery();
				if(!rs.next()){
					reservation.setAssignedTableNo(tableNo);
					break;
				}
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		return reservation;
	}
	
	/**
	 * This method creates a new reservation.
	 * @param reservation
	 * @throws ExceptionHandler
	 */
	public void createReservation(ReservationDetails reservation) throws ExceptionHandler{
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ReservationDetails "
					+ "(FirstName, LastName, BookingDate, BookingTime, PartySize, ContactNo, Status, Visited, AssignedTableNo, RestaurantId) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, reservation.getFirstName());
			ps.setString(2, reservation.getLastName());
			ps.setString(3, reservation.getBookingDate());
			ps.setString(4, reservation.getBookingTime());
			ps.setInt(5, reservation.getPartySize());
			ps.setLong(6, reservation.getContactNo());
			ps.setString(7, reservation.getStatus());
			ps.setString(8, "N");
			ps.setInt(9, reservation.getAssignedTableNo());
			ps.setInt(10, reservation.getRestaurantId());
			
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs.next()){
				reservation.setConfirmationNo(rs.getInt(1));
			}else{
				throw new ExceptionHandler("Reservation Failed.");
			}		
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
	}
	
	/**
	 * This method finds the reservation for a given confirmation code.
	 * @param reservation
	 * @return
	 * @throws ExceptionHandler
	 */
	public ReservationDetails findReservation(ReservationDetails reservation) throws ExceptionHandler{
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM ReservationDetails WHERE ConfirmationNo = ?");
			ps.setInt(1, reservation.getConfirmationNo());
			rs = ps.executeQuery();
			if(rs.next()){
				reservation.setFirstName(rs.getString("FirstName"));
				reservation.setLastName(rs.getString("LastName"));
				reservation.setContactNo(rs.getLong("ContactNo"));
				reservation.setBookingDate(rs.getString("BookingDate"));
				reservation.setBookingTime(rs.getString("BookingTime"));
				reservation.setPartySize(rs.getInt("PartySize"));
				reservation.setRestaurantId(rs.getInt("RestaurantId"));
				reservation.setStatus(rs.getString("Status"));
				reservation.setAssignedTableNo(rs.getInt("AssignedTableNo"));
				reservation.setFlag(true);
			}else{
				reservation.setFlag(false);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		return reservation;
		
	}
	
	/**
	 * This method modifies reservation details into database
	 * @param reservation
	 * @param modifyContact
	 * @throws ExceptionHandler
	 */
	public void modifyReservation(ReservationDetails reservation, boolean modifyContact) throws ExceptionHandler{
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			if(modifyContact){
				ps = conn.prepareStatement("UPDATE ReservationDetails SET ContactNo = ? WHERE ConfirmationNo = ? ");
				ps.setLong(1, reservation.getContactNo());
				ps.setInt(2, reservation.getConfirmationNo());
			}else{
				ps = conn.prepareStatement("UPDATE ReservationDetails SET ContactNo = ?, BookingDate = ?, BookingTime = ?, PartySize = ?, AssignedTableNo=?, Status=?  WHERE ConfirmationNo = ? ");
				ps.setLong(1, reservation.getContactNo());
				ps.setString(2, reservation.getBookingDate());
				ps.setString(3, reservation.getBookingTime());
				ps.setInt(4, reservation.getPartySize());
				ps.setInt(5, reservation.getAssignedTableNo());
				ps.setString(6, reservation.getStatus());
				ps.setInt(7, reservation.getConfirmationNo());
			}
			
			ps.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
	}
	
	/**
	 * This method update status to Cancel for a given confirmation number.
	 * @param confNo
	 * @return Response Message
	 * @throws ExceptionHandler
	 */
	public ResponseMessage cancelReservation(int confNo) throws ExceptionHandler{
		ResponseMessage message = new ResponseMessage();
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("UPDATE ReservationDetails SET Status = ? WHERE ConfirmationNo = ?");
			ps.setString(1, "Cancel");
			ps.setInt(2, confNo);
			int row = ps.executeUpdate();
			if(row>0){
				message.setMessage("Reservation Cancelled.");
			}else{
				message.setError("Reservation NOT cancelled.");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		return message;
	}

	/**
	 * This method fetches all reservations for the given restaurant Id
	 * @param id
	 * @return List of reservations
	 * @throws ExceptionHandler
	 */
	public List<ReservationDetails> getAllReservations(int id) throws ExceptionHandler {
		List<ReservationDetails> reservations = new ArrayList<ReservationDetails>();
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM ReservationDetails WHERE RestaurantId = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				ReservationDetails res = new ReservationDetails();
				res.setConfirmationNo(rs.getInt("ConfirmationNo"));
				res.setFirstName(rs.getString("FirstName"));
				res.setLastName(rs.getString("LastName"));
				res.setBookingDate(rs.getString("BookingDate"));
				res.setBookingTime(rs.getString("BookingTime"));
				res.setPartySize(rs.getInt("PartySize"));
				res.setContactNo(rs.getLong("ContactNo"));
				res.setStatus(rs.getString("Status"));
				res.setVisited(rs.getString("Visited"));
				res.setAssignedTableNo(rs.getInt("AssignedTableNo"));
				reservations.add(res);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		return reservations;
	}

	/**
	 * Get all table for the restaurant
	 * @param restId
	 * @return
	 * @throws ExceptionHandler
	 */
	public List<Tables> getTables(int restId) throws ExceptionHandler {
		List<Tables> tables = new ArrayList<Tables>();
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM RestaurantTables WHERE RestaurantId = ?");
			ps.setInt(1, restId);
			rs = ps.executeQuery();
			while(rs.next()){
				Tables table = new Tables();
				table.setTableNo(rs.getInt("TableNo"));
				table.setNoOfSeats(rs.getInt("NoOfSeats"));
				tables.add(table);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		return tables;
	}

	/**
	 * 
	 * @param reservation
	 * @return
	 * @throws ExceptionHandler 
	 */
	public List<Tables> getAvailableTables(ReservationDetails reservation) throws ExceptionHandler {
		
		List<Tables> tables = new ArrayList<Tables>();
		List<Tables> availableTable = new ArrayList<Tables>();
		
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM RestaurantTables WHERE RestaurantId = ? AND NoOfSeats >= ?");
			ps.setInt(1, reservation.getRestaurantId());
			ps.setInt(2, reservation.getPartySize());
			rs = ps.executeQuery();
			while(rs.next()){
				Tables table = new Tables();
				table.setTableNo(rs.getInt("TableNo"));
				table.setNoOfSeats(rs.getInt("NoOfSeats"));
				tables.add(table);
			}
			
			for(Tables table : tables){
				ps = conn.prepareStatement("SELECT * FROM ReservationDetails WHERE RestaurantId=? AND BookingDate = ? AND AssignedTableNo = ? AND BookingTime BETWEEN SUBTIME(?,'01:00:00') AND ADDTIME(?,'01:00:00') AND Status <> 'Cancel'");
				ps.setInt(1, reservation.getRestaurantId());
				ps.setString(2, reservation.getBookingDate());
				ps.setInt(3, table.getTableNo());
				ps.setString(4, reservation.getBookingTime());
				ps.setString(5, reservation.getBookingTime());
				rs = ps.executeQuery();
				if(!rs.next()){
					availableTable.add(table);
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		return availableTable;
	}

	/**
	 * This method assigns a table to the reservation in the table.
	 * @param reservation
	 * @throws ExceptionHandler 
	 */
	public void assignTable(ReservationDetails reservation) throws ExceptionHandler {
		
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("UPDATE ReservationDetails SET AssignedTableNo = ?, Status = ? WHERE ConfirmationNo = ?");
			ps.setInt(1, reservation.getAssignedTableNo());
			ps.setString(2, "Confirm");
			ps.setInt(3, reservation.getConfirmationNo());
			
			int row = ps.executeUpdate();
			if(row>0){
				reservation.setStatus("Confirm");
				reservation.setFlag(true);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
	}

	/**
	 * This method finds the active reservations available on a selected table number.
	 * @param tableNo
	 * @return List of all the reservations.
	 * @throws ExceptionHandler
	 */
	public List<ReservationDetails> findActiveReservationsOnTable(int tableNo) throws ExceptionHandler {
		List<ReservationDetails> reservations = new ArrayList<ReservationDetails>();
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM ReservationDetails WHERE AssignedTableNo = ? AND BookingDate >= curdate()");
			ps.setInt(1, tableNo);
			rs = ps.executeQuery();
			while(rs.next()){
				ReservationDetails reservation = new ReservationDetails();
				reservation.setConfirmationNo(rs.getInt("ConfirmationNo"));
				reservation.setFirstName(rs.getString("FirstName"));
				reservation.setLastName(rs.getString("LastName"));
				reservation.setBookingDate(rs.getString("BookingDate"));
				reservation.setBookingTime(rs.getString("BookingTime"));
				reservation.setPartySize(rs.getInt("PartySize"));
				reservation.setContactNo(rs.getLong("ContactNo"));
				reservation.setStatus(rs.getString("Status"));
				reservation.setAssignedTableNo(rs.getInt("AssignedTableNo"));
				reservations.add(reservation);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		return reservations;
	}
	
	
}
