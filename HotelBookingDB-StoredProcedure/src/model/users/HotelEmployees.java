package model.users;

import java.util.ArrayList;

public class HotelEmployees extends Users {

	private ArrayList<Integer> hotelID;
	private int managerID;

	public HotelEmployees(int userID, int managerID, String name, String phone, String email, String username,
			String password, ArrayList<Integer> hotelID) {
		super(userID, name, phone, email);
		setHotelID(hotelID);
		setManagerID(managerID);
		setUsername(username);
		setPassword(password);

	}
	
	public HotelEmployees(int userID, int managerID, String name, String phone, String email,String username,
			String password) {
		super(userID, name, phone, email);
		setManagerID(managerID);
		setUsername(username);
		setPassword(password);
	}
	
	public HotelEmployees(int userID, int managerID, String name, String phone, String email,
			ArrayList<Integer> hotelID) {
		super(userID, name, phone, email);
		setHotelID(hotelID);
		setManagerID(managerID);
	}
	
	public HotelEmployees(int userID, int managerID, String name, String phone, String email) {
		super(userID, name, phone, email);
		setManagerID(managerID);
	}
	
	public HotelEmployees(int userID, String name, String phone, String email) {
		super(userID, name, phone, email);
	}

	public HotelEmployees(int userID, String name, String phone, String email, String password) {
		super(userID, name, phone, email);
		setPassword(password);
	}
	
	public HotelEmployees(String name,String phone,String email) {
		super(name,phone,email);
	}
	
	public HotelEmployees() {
		super();
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	

	// ------------------------------------------------------------------------------------------------------------------------------
	public void printInfo() {
		System.out.println(this.getName() + "/" + this.getPhoneNumber() + "/" + this.getEmail() + "/" + this.hotelID);
	}

	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public ArrayList<Integer> getHotelID() {
		return hotelID;
	}

	public void setHotelID(ArrayList<Integer> hotelID) {
		this.hotelID = hotelID;
	}
}
