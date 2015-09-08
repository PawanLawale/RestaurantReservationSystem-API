package egen.project.rrs.bo;

import java.util.List;

import egen.project.rrs.configuration.ExceptionHandler;
import egen.project.rrs.dao.RestaurantProfileDAO;
import egen.project.rrs.dto.ReservationDetails;
import egen.project.rrs.dto.RestaurantProfile;

public class RestaurantProfileModel {

	/**
	 * Get Restaurant details based on restaurant id
	 * @param restId
	 * @return
	 * @throws ExceptionHandler
	 */
	public RestaurantProfile getRestaurantProfile(int restId) throws ExceptionHandler {
		RestaurantProfileDAO dao = new RestaurantProfileDAO();
		RestaurantProfile profile = dao.getRestaurantProfile(restId);
		return profile;
	}

	/**
	 * Update restaurant profile based on restaurant Id
	 * @param profile
	 * @throws ExceptionHandler 
	 */
	public void updateRestaurantProfile(RestaurantProfile profile) throws ExceptionHandler {
		RestaurantProfileDAO dao = new RestaurantProfileDAO();
		dao.updateRestaurantProfile(profile);
	}

	/**
	 * This method finds guest details based on their contact number
	 * @param contactNo
	 * @return list of guests visit to the restaurant
	 * @throws ExceptionHandler 
	 */
	public List<ReservationDetails> getGuestRecord(long contactNo) throws ExceptionHandler {
		RestaurantProfileDAO dao = new RestaurantProfileDAO();
		List<ReservationDetails> records = dao.getGuestRecord(contactNo);
		return records;
	}

}
