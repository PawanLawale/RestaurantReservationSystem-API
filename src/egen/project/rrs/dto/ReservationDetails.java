package egen.project.rrs.dto;

public class ReservationDetails {
	private int confirmationNo;
	private String firstName;
	private String lastName;
	private String bookingDate;
	private String bookingTime;
	private int partySize;
	private long contactNo;
	private String status;
	private String visited;
	private int assignedTableNo;
	private int restaurantId;
	private boolean flag;
	
	public int getConfirmationNo() {
		return confirmationNo;
	}
	public void setConfirmationNo(int confirmationNo) {
		this.confirmationNo = confirmationNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}
	public int getPartySize() {
		return partySize;
	}
	public void setPartySize(int partySize) {
		this.partySize = partySize;
	}
	public long getContactNo() {
		return contactNo;
	}
	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVisited() {
		return visited;
	}
	public void setVisited(String visited) {
		this.visited = visited;
	}
	public int getAssignedTableNo() {
		return assignedTableNo;
	}
	public void setAssignedTableNo(int assignedTableNo) {
		this.assignedTableNo = assignedTableNo;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
