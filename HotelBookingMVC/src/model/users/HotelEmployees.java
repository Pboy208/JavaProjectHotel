package model.users;

import javafx.beans.property.SimpleStringProperty;

public class HotelEmployees extends Users {

	private int hotelID;
	private int rank; // 1 for manager, 2 for employee

	private SimpleStringProperty nameProperty;
	private SimpleStringProperty phoneProperty;
	private SimpleStringProperty emailProperty;
	private SimpleStringProperty rankProperty;

	public HotelEmployees(int userID, String name, String phone, String email, int hotelID, int rank) {
		super(userID, name, phone, email);
		setHotelID(hotelID);
		setRank(rank);
		
		this.nameProperty=new SimpleStringProperty(name);
		this.phoneProperty=new SimpleStringProperty(phone);
		this.emailProperty=new SimpleStringProperty(email);
		if(rank==1) {
			this.rankProperty=new SimpleStringProperty("Manager");
		}
		else if(rank==2) {
			this.rankProperty=new SimpleStringProperty("Employee");
		}
		else {
			System.out.println("Wrong rank of HotelEmployees");
		}
	}

	public HotelEmployees() {
		super();
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	public int getRank() {
		return rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int departmentID) {
		this.hotelID = departmentID;
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

	public String getRankProperty() {
		return rankProperty.get();
	}

	public void setRankProperty(SimpleStringProperty rankProperty) {
		this.rankProperty = rankProperty;
	}
}
