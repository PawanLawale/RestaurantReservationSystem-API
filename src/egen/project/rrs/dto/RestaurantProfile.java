package egen.project.rrs.dto;

public class RestaurantProfile {
	
	private int restaurantId;
	private String restaurantName;
	private long contactNo;
	private String email;
	private String address1;
	private String address2;
	private String operationalDays;
	private String openingTime;
	private String closingTime;
	private String autoAssign;
	
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public long getContactNo() {
		return contactNo;
	}
	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getOperationalDays() {
		return operationalDays;
	}
	public void setOperationalDays(String operationalDays) {
		this.operationalDays = operationalDays;
	}
	public String getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}
	public String getClosingTime() {
		return closingTime;
	}
	public void setClosingTime(String closingTime) {
		this.closingTime = closingTime;
	}
	public String getAutoAssign() {
		return autoAssign;
	}
	public void setAutoAssign(String autoAssign) {
		this.autoAssign = autoAssign;
	}
	
}
