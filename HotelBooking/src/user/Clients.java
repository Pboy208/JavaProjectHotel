package user;

import database.Postgresql;

public class Clients extends User implements Postgresql {
	
	public Clients(int user_id) {
		super(user_id);
	}
	
	//-------------------------------------------------------------------------------------------------------
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
	public void registing() {
	
	}
	public void checkRecommendedRoom() {
		
	}
	public void checkRoomByName() {
		
	}
	
	public void printInfo() {
		System.out.println(this.getName()+"/"+this.getPhoneNumber()+"/"+this.getEmail());
	}
}
