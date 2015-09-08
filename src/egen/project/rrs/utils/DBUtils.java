package egen.project.rrs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import egen.project.rrs.configuration.ExceptionHandler;

public class DBUtils {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "password";
	private final static String URL = "jdbc:mysql://localhost:3307/RRS";
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver registered.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Driver registry failed.");
		}
	}
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Connection established.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection rejected.");
		}
		return conn;
	}
	
	public static void main(String args[]){
		getConnection();
	}
	
	public static void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) throws ExceptionHandler{
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		}
	}
}
