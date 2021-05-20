package model.rooms;

import javafx.beans.property.SimpleStringProperty;

public class Rooms {
	private int roomID; // id == roomNumber?
	private int hotelID;
	private int price;

	private SimpleStringProperty roomIDProperty;
	private SimpleStringProperty priceProperty;

	public Rooms() {
		// TODO Auto-generated constructor stub
	}

	public Rooms(int roomID, int hotelID, int price) {
		setHotelID(hotelID);
		setPrice(price);
		setRoomID(roomID);

		this.roomIDProperty = new SimpleStringProperty(Integer.toString(roomID));
		this.priceProperty = new SimpleStringProperty(Integer.toString(price));
	}

	// -------------------------------------------------------------
	public void printInfo() {
		System.out.println(roomID + "/" + hotelID + "/" + price);
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	// --------------------------------------------------
	public String getRoomIDProperty() {
		return roomIDProperty.get();
	}

	public void setRoomIDProperty(SimpleStringProperty roomIDProperty) {
		this.roomIDProperty = roomIDProperty;
	}

	public String getPriceProperty() {
		return priceProperty.get();
	}

	public void setPriceProperty(SimpleStringProperty priceProperty) {
		this.priceProperty = priceProperty;
	}

}
