package model.users;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class HotelEmployees extends Users {

	private ArrayList<Integer> hotelID;
	private int managerID;
	private SimpleStringProperty nameProperty;
	private SimpleStringProperty phoneProperty;
	private SimpleStringProperty emailProperty;

	public HotelEmployees(int userID,int managerID,String name, String phone, String email,String username,String password,ArrayList<Integer> hotelID) {
		super(userID, name, phone, email);
		setHotelID(hotelID);
		setManagerID(managerID);
		setUsername(username);
		setPassword(password);
		
		this.nameProperty=new SimpleStringProperty(name);
		this.phoneProperty=new SimpleStringProperty(phone);
		this.emailProperty=new SimpleStringProperty(email);
	}
	public HotelEmployees(int userID,int managerID,String name, String phone, String email,ArrayList<Integer> hotelID) {
		super(userID, name, phone, email);
		setHotelID(hotelID);
		setManagerID(managerID);
	}
	public HotelEmployees(int userID, String name, String phone, String email) {
		super(userID, name, phone, email);
	}
	public HotelEmployees(int userID, String name, String phone, String email,String password) {
		super(userID, name, phone, email);
		setPassword(password);
	}
	public HotelEmployees() {
		super();
	}

	// ------------------------------------------------------------------------------------------------------------------------------

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

	public void printInfo() {
		System.out.println(this.getName() + "/" + this.getPhoneNumber() + "/" + this.getEmail() + "/" + this.hotelID);
	}
	// ------------------------------------------------------------------------------------------------------------------------------
	public String getNameProperty() {
		return nameProperty.get();
	}

	public void setNameProperty(SimpleStringProperty nameProperty) {
		this.nameProperty = nameProperty;
	}

	public String getPhoneProperty() {
		return phoneProperty.get();
	}

	public void setPhoneProperty(SimpleStringProperty phoneProperty) {
		this.phoneProperty = phoneProperty;
	}

	public String getEmailProperty() {
		return emailProperty.get();
	}

	public void setEmailProperty(SimpleStringProperty emailProperty) {
		this.emailProperty = emailProperty;
	}

}
