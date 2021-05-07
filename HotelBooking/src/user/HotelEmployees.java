package user;

import database.Postgresql;

public class HotelEmployees extends User implements Postgresql {

	private int hotelID; // ???
	
	public HotelEmployees(int user_id) {
		super(user_id);
	}
	
	public int getHotelID() {
		return hotelID;
	}
	public void setHotelID(int departmentID) {
		this.hotelID = departmentID;
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
	public void printInfo() {
		System.out.println(this.getName()+"/"+this.getPhoneNumber()+"/"+this.getEmail()+"/"+this.hotelID);
	}
}
