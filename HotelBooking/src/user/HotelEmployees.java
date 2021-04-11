package user;

public class HotelEmployees extends User {

	private int departmentID; // ???
	private String departmentName;
	private int employeesID;
	
	public HotelEmployees() {
		super();
	}
	
	public int getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------
	public void checkReservedRoom() {
		
	}
	public void cancelRoom() {
		
	}
	public void viewAvailableRoom() {
		
	}
	public void checkReservingStatus() {
		
	}
	public int login() {
		//checking account in DB
		return 0;
	}
	public void updateRoomStatus() {
		
	}
}
