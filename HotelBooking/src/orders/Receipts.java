package orders;

import java.util.Date;

public class Receipts {

	private int receiptID;
	private int hotelID;
	private int roomID;
	private int userID;
	private Date orderDate;
	private Date checkinDate;
	private Date checkoutDate;

	public Receipts(int receiptID, int hotelID, int roomID, int userID, Date checkinDate, Date checkoutDate,
			Date orderDate) {
		// TODO Auto-generated constructor stub
		this.setOrderDate(orderDate);
		this.setCheckinDate(checkinDate);
		this.setCheckoutDate(checkoutDate);
		this.setHotelID(hotelID);
		this.setRoomID(roomID);
		this.setUserID(userID);
		this.setReceiptID(receiptID);
	}

	public Receipts() {

	}
	
	public void printInfo() {
		System.out.println(receiptID+"/"+roomID);
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

}
