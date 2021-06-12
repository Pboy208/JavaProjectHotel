package model.rooms;

import javafx.beans.property.SimpleStringProperty;

//import javafx.beans.property.SimpleStringProperty;

public class Rooms {
	private int roomID; // id == roomNumber?
	private int hotelID;

//	private SimpleStringProperty roomIDProperty;

	public Rooms() {
		// TODO Auto-generated constructor stub
	}

	public Rooms(int roomID, int hotelID) {
		setHotelID(hotelID);
		setRoomID(roomID);

	}

	// -------------------------------------------------------------
	public void printInfo() {
		System.out.println(roomID + "/" + hotelID );
	}

	// -------------------------------------------------------------

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}



	// --------------------------------------------------
//	public String getRoomIDProperty() {
//		return roomIDProperty.get();
//	}
//
//	public void setRoomIDProperty(SimpleStringProperty roomIDProperty) {
//		this.roomIDProperty = roomIDProperty;
//	}
//
	


}
