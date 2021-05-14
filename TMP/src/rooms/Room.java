package rooms;

import java.util.Date;

import javafx.beans.property.SimpleStringProperty;

public class Room {
	private int userID;
	private int roomID; // id == roomNumber?
	private int hotelID;
	private int receiptID;
	private int price;
	private int status; // -1 NOT AVAIL,0 waiting checkin,1 waiting checkout,2 completes,3 cancelled

	private Date orderDate;
	private Date checkinDate;
	private Date checkoutDate;

	private SimpleStringProperty roomIDProperty;
	private SimpleStringProperty receiptIDProperty;
	private SimpleStringProperty statusProperty;
	private SimpleStringProperty orderDateProperty;
	private SimpleStringProperty checkinDateProperty;
	private SimpleStringProperty checkoutDateProperty;
	
	public Room() {
		// TODO Auto-generated constructor stub
	}

	public Room(int roomID, int hotelID, int receiptID, int price) {
		setHotelID(hotelID);
		setPrice(price);
		setReceiptID(receiptID);
		setRoomID(roomID);

		this.roomIDProperty = new SimpleStringProperty(Integer.toString(roomID));
		if (receiptID != 0)
			this.receiptIDProperty = new SimpleStringProperty(Integer.toString(receiptID));
		else {
			this.receiptIDProperty = new SimpleStringProperty("Null");
		}
	}

	// -------------------------------------------------------------
	public void printInfo() {
		System.out.println(userID + "/" + hotelID + "/" + receiptID);
	}

	// -------------------------------------------------------------
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
		if (orderDate != null)
			this.orderDateProperty = new SimpleStringProperty(orderDate.toString());
		else
			this.orderDateProperty = new SimpleStringProperty("Null");
	}

	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
		if (checkinDate != null)
			this.checkinDateProperty = new SimpleStringProperty(checkinDate.toString());
		else
			this.checkinDateProperty = new SimpleStringProperty("Null");
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
		if (checkoutDate != null)
			this.checkoutDateProperty = new SimpleStringProperty(checkoutDate.toString());
		else
			this.checkoutDateProperty = new SimpleStringProperty("Null");
	}

	public void setStatus(int status) {
		this.status = status;
		switch (status) {
		case -1:
			this.statusProperty = new SimpleStringProperty("Availalble");
			break;
		case 0:
			this.statusProperty = new SimpleStringProperty("Waiting for checkin");
			break;
		case 1:
			this.statusProperty = new SimpleStringProperty("Waiting for checkout");
			break;
		case 2:
			this.statusProperty = new SimpleStringProperty("Cancelled");
			break;
		}
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public Date getCheckinDate() {
		return checkinDate;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

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

	public int getReceiptID() {
		return receiptID;
	}

	public void setReceiptID(int receiptID) {
		this.receiptID = receiptID;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	// --------------------------------------------------
	public String getRoomIDProperty() {
		return roomIDProperty.get();
	}

	public void setRoomIDProperty(SimpleStringProperty roomIDProperty) {
		this.roomIDProperty = roomIDProperty;
	}

	public String getReceiptIDProperty() {
		return receiptIDProperty.get();
	}

	public void setReceiptIDProperty(SimpleStringProperty receiptIDProperty) {
		this.receiptIDProperty = receiptIDProperty;
	}

	public String getStatusProperty() {
		return statusProperty.get();
	}

	public void setStatusProperty(SimpleStringProperty statusProperty) {
		this.statusProperty = statusProperty;
	}

	public String getOrderDateProperty() {
		return orderDateProperty.get();
	}

	public void setOrderDateProperty(SimpleStringProperty orderDateProperty) {
		this.orderDateProperty = orderDateProperty;
	}

	public String getCheckinDateProperty() {
		return checkinDateProperty.get();
	}

	public void setCheckinDateProperty(SimpleStringProperty checkinDateProperty) {
		this.checkinDateProperty = checkinDateProperty;
	}

	public String getCheckoutDateProperty() {
		return checkoutDateProperty.get();
	}

	public void setCheckoutDateProperty(SimpleStringProperty checkoutDateProperty) {
		this.checkoutDateProperty = checkoutDateProperty;
	}

}
