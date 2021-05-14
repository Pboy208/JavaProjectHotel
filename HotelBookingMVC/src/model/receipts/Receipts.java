package model.receipts;

import java.sql.SQLException;
import java.sql.Date;

import javafx.beans.property.SimpleStringProperty;
import model.database.HotelDB;
import model.rooms.Hotel;

public class Receipts {

	private int receiptID;
	private int hotelID;
	private int roomID;
	private int userID;
	private int status;
	private Date orderDate;
	private Date checkinDate;
	private Date checkoutDate;
	private float price;

	private SimpleStringProperty hotelAddressProperty;
	private SimpleStringProperty hotelNameProperty;
	private SimpleStringProperty roomIDProperty;
	private SimpleStringProperty receiptIDProperty;
	private SimpleStringProperty statusProperty;
	private SimpleStringProperty orderDateProperty;
	private SimpleStringProperty checkinDateProperty;
	private SimpleStringProperty checkoutDateProperty;
	private SimpleStringProperty priceProperty;

	public Receipts(int receiptID, int hotelID, int roomID, int userID, Date checkinDate, Date checkoutDate,
			Date orderDate, int status) throws SQLException {
		// TODO Auto-generated constructor stub
		this.setOrderDate(orderDate);
		this.setCheckinDate(checkinDate);
		this.setCheckoutDate(checkoutDate);
		this.setHotelID(hotelID);
		this.setRoomID(roomID);
		this.setUserID(userID);
		this.setReceiptID(receiptID);
		this.setStatus(status);

		this.roomIDProperty = new SimpleStringProperty(Integer.toString(roomID));
		Hotel tmpHotel = HotelDB.queryHotelInfo(hotelID);
		this.hotelAddressProperty = new SimpleStringProperty(tmpHotel.getAddress());
		this.hotelNameProperty = new SimpleStringProperty(tmpHotel.getName());
		if (receiptID != 0)
			this.receiptIDProperty = new SimpleStringProperty(Integer.toString(receiptID));
		else
			this.receiptIDProperty = new SimpleStringProperty("Null");

		if (checkinDate != null)
			this.checkinDateProperty = new SimpleStringProperty(checkinDate.toString());
		else
			this.checkinDateProperty = new SimpleStringProperty("Null");

		if (checkoutDate != null)
			this.checkoutDateProperty = new SimpleStringProperty(checkoutDate.toString());
		else
			this.checkoutDateProperty = new SimpleStringProperty("Null");

		if (orderDate != null)
			this.orderDateProperty = new SimpleStringProperty(orderDate.toString());
		else
			this.orderDateProperty = new SimpleStringProperty("Null");

		switch (status) {
		case -1:
			this.statusProperty = new SimpleStringProperty("finish");
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

	public Receipts() {

	}

	public void printInfo() {
		System.out.println(receiptID + "/" + roomID);
	}

	// --------------------------------------------------------------------------------
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getReceiptID() {
		return receiptID;
	}

	public void setReceiptID(int receiptID) {
		this.receiptID = receiptID;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	public Date getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
		this.priceProperty = new SimpleStringProperty(Float.toString(price));
	}
	// --------------------------------------------------------------------------------

	public String getPriceProperty() {
		return priceProperty.get();
	}

	public void setPriceProperty(SimpleStringProperty priceProperty) {
		this.priceProperty = priceProperty;
	}

	public String getHotelAddressProperty() {
		return hotelAddressProperty.get();
	}

	public void setHotelAddressProperty(SimpleStringProperty hotelAddressProperty) {
		this.hotelAddressProperty = hotelAddressProperty;
	}

	public String getHotelNameProperty() {
		return hotelNameProperty.get();
	}

	public void setHotelNameProperty(SimpleStringProperty hotelNameProperty) {
		this.hotelNameProperty = hotelNameProperty;
	}

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
