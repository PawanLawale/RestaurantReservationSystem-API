package egen.project.rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import egen.project.rrs.configuration.ExceptionHandler;
import egen.project.rrs.dto.ReservationDetails;
import egen.project.rrs.dto.RestaurantProfile;
import egen.project.rrs.utils.DBUtils;

public class RestaurantProfileDAO {

	public RestaurantProfile getRestaurantProfile(int restId) throws ExceptionHandler{
		RestaurantProfile profile = new RestaurantProfile();
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM RestaurantDetails WHERE RestaurantId = ?");
			ps.setInt(1, restId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				profile.setRestaurantId(rs.getInt("RestaurantId"));
				profile.setRestaurantName(rs.getString("RestaurantName"));
				profile.setContactNo(rs.getLong("ContactNo"));
				profile.setEmail(rs.getString("Email"));
				profile.setAddress1(rs.getString("Address1"));
				profile.setAddress2(rs.getString("Address2"));
				profile.setOperationalDays(rs.getString("OperationalDays"));
				profile.setOpeningTime(rs.getString("OpeningTime"));
				profile.setClosingTime(rs.getString("ClosingTime"));
				profile.setAutoAssign(rs.getString("AutoAssign"));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		
		return profile;
	}

	/**
	 * This method updates restaurant details into database.
	 * @param profile
	 * @throws ExceptionHandler 
	 */
	public void updateRestaurantProfile(RestaurantProfile profile) throws ExceptionHandler {
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("UPDATE RestaurantDetails SET RestaurantName = ?, ContactNo = ?, Email= ?, Address1 = ?"
											+ ", Address2 = ?, OperationalDays = ?, OpeningTime = ?, ClosingTime = ?, AutoAssign = ?"
											+ " WHERE RestaurantId = ?");
			ps.setString(1, profile.getRestaurantName());
			ps.setLong(2, profile.getContactNo());
			ps.setString(3, profile.getEmail());
			ps.setString(4, profile.getAddress1());
			ps.setString(5, profile.getAddress2());
			ps.setString(6, profile.getOperationalDays());
			ps.setString(7, profile.getOpeningTime());
			ps.setString(8, profile.getClosingTime());
			ps.setString(9, profile.getAutoAssign());
			ps.setInt(10, profile.getRestaurantId());
			
			ps.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		
	}
	
	/**
	 * This method finds all the records of the guest based on their contact number.
	 * @param contactNo
	 * @return list of guests visit to the restaurant
	 * @throws ExceptionHandler
	 */
	public List<ReservationDetails> getGuestRecord(long contactNo) throws ExceptionHandler{
		List<ReservationDetails> records = new ArrayList<ReservationDetails>();
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT FirstName, LastName, BookingDate, BookingTime, PartySize, Visited FROM ReservationDetails WHERE ContactNo = ?");
			ps.setLong(1, contactNo);
			rs = ps.executeQuery();
			while(rs.next()){
				ReservationDetails record = new ReservationDetails();
				record.setFirstName(rs.getString("FirstName"));
				record.setLastName(rs.getString("LastName"));
				record.setBookingDate(rs.getString("BookingDate"));
				record.setBookingTime(rs.getString("BookingTime"));
				record.setPartySize(rs.getInt("PartySize"));
				record.setVisited(rs.getString("Visited"));
				records.add(record);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		return records;
	}
}
