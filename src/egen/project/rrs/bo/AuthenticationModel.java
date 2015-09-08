package egen.project.rrs.bo;

import java.util.TreeMap;
import java.util.UUID;

import egen.project.rrs.configuration.ExceptionHandler;
import egen.project.rrs.dao.AuthenticationDAO;
import egen.project.rrs.dto.LoginDetails;

public class AuthenticationModel {
	
	private static TreeMap<String,String> sessionTracker = null;
	
	public AuthenticationModel(){
		if(sessionTracker == null){
			sessionTracker = new TreeMap<String,String>();
		}
	}

	/**
	 * This method authenticates the user and on successful authentication
	 * it generates the token and shares it with the client and also stores the 
	 * same token on server for further validations of requests.
	 * @param loginDetails
	 * @throws ExceptionHandler
	 */
	public void authenticateUser(LoginDetails loginDetails) throws ExceptionHandler{
		AuthenticationDAO dao = new AuthenticationDAO();
		boolean flag = dao.validateUser(loginDetails);
		if(flag){
			loginDetails.setPassword(null);
			loginDetails.setSessionToken(UUID.randomUUID().toString());
			sessionTracker.put(loginDetails.getEmail(), loginDetails.getSessionToken());
		}else{
			loginDetails.setMessage("Login Failed. Invalid Email or Password.");
		}
	}
	
	/**
	 * This method earses all user information at the server side
	 * and logs user out.
	 * @param loginDetails
	 */
	public void logoutUser(LoginDetails loginDetails){
		sessionTracker.remove(loginDetails.getEmail());
		loginDetails.setSessionToken(null);
		loginDetails.setEmail(null);
		loginDetails.setFirstName(null);
		loginDetails.setLastName(null);
		loginDetails.setMessage("You have been logged out");
		loginDetails.setContactNo(0);
	}
	
	/**
	 * This method validates incoming request from user based on
	 * email address and token shared with client.
	 * @param email
	 * @param token
	 * @return
	 */
	public static boolean isValidSession(String email, String token){
		boolean flag = false;
		String sessionToken = sessionTracker.get(email);
		if(sessionToken != null && sessionToken.equals(token)){
			flag = true;
		}
		return flag;
	}
	
}
