package user;

import database.Postgresql;

public class HotelEmployees extends User implements Postgresql {

	private int hotelID; 
	
	public HotelEmployees(int user_id) {
		super(user_id);
	}
	
	public HotelEmployees(String name,String phone,String email) {
		super(name,phone,email);
	}

	//------------------------------------------------------------------------------------------------------------------------------
	public int getHotelID() {
		return hotelID;
	}
	
	public void setHotelID(int departmentID) {
		this.hotelID = departmentID;
	}

	public void printInfo() {
		System.out.println(this.getName()+"/"+this.getPhoneNumber()+"/"+this.getEmail()+"/"+this.hotelID);
	}
}
