package egen.project.rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import egen.project.rrs.configuration.ExceptionHandler;
import egen.project.rrs.dto.LoginDetails;
import egen.project.rrs.utils.DBUtils;

public class AuthenticationDAO {

	public boolean validateUser(LoginDetails loginDetails) throws ExceptionHandler {
		boolean flag = false;
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM Login WHERE email = ? AND password = ?");
			ps.setString(1, loginDetails.getEmail());
			ps.setString(2, loginDetails.getPassword());
			rs = ps.executeQuery();
			if(rs.next()){
				loginDetails.setFirstName(rs.getString("FirstName"));
				loginDetails.setLastName(rs.getString("LastName"));
				loginDetails.setRestaurantId(rs.getInt("RestaurantId"));
				loginDetails.setContactNo(rs.getLong("ContactNo"));
				flag = true;
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(),e.getCause());
		}finally {
			DBUtils.closeResources(conn, ps, rs);
		}
		return flag;
	}

}
