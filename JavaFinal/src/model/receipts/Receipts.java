package model.receipts;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.HostController;
import controller.LoginController;
import javafx.beans.property.SimpleStringProperty;
import model.database.Mysql;
import model.rooms.Hotels;

public class Receipts {

	private int receiptID;
	private int hotelID;
	private int roomID;
	private int userID;
	private int status;
	private int payment;
	private Date bookingDate;
	private Date checkinDate;
	private Date checkoutDate;

	private SimpleStringProperty hotelAddressProperty;
	private SimpleStringProperty hotelNameProperty;
	private SimpleStringProperty statusProperty;
	private SimpleStringProperty paymentProperty;
	
	public Receipts(int receiptID, int userID, int roomID, Date checkinDate, Date checkoutDate,
			Date orderDate,int payment, int status, int hotelID) throws SQLException {
		setBookingDate(orderDate);
		setCheckinDate(checkinDate);
		setCheckoutDate(checkoutDate);
		setHotelID(hotelID);
		setRoomID(roomID);
		setUserID(userID);
		setReceiptID(receiptID);
		setStatus(status);
		setPayment(payment);
		
		this.paymentProperty= new SimpleStringProperty(String.format("%,d", payment));
		
		//------------------------------------- Getting hotel address and name
		Hotels tmpHotel = new Hotels();
		tmpHotel.queryInstance(hotelID);
		this.hotelAddressProperty = new SimpleStringProperty(tmpHotel.getAddress());
		this.hotelNameProperty = new SimpleStringProperty(tmpHotel.getName());
		
		//------------------------------------- Convert Status value to string
		switch (status) {
		case -1:
			this.statusProperty = new SimpleStringProperty("Cancelled");
			break;
		case 0:
			this.statusProperty = new SimpleStringProperty("Waiting for checkin");
			break;
		case 1:
			this.statusProperty = new SimpleStringProperty("Waiting for checkout");
			break;
		case 2:
			this.statusProperty = new SimpleStringProperty("Finish");
			break;
		}

	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------

	public static void addReceipt(int numberOfRoom,int hotelID,Date checkinDate,Date checkoutDate,int userID) throws SQLException {
		String updateStatement=String.format("CALL ConfirmBooking(%d,%d,'%s','%s',%d)",
				numberOfRoom,hotelID,checkinDate,checkoutDate,userID);
		System.out.println(String.format("CALL ConfirmBooking(%d,%d,'%s','%s',%d)",
				numberOfRoom,hotelID,checkinDate,checkoutDate,userID));
		Mysql.executeQuery(updateStatement);
	}
	
	public static void cancelReceiptUser(int receiptID) throws SQLException {
		String queryStatement = String.format("CALL CancelReceiptUser(%d,%d)",receiptID,LoginController.getUser().getUserID());
		System.out.println(String.format("CALL CancelReceiptUser(%d,%d)",receiptID,LoginController.getUser().getUserID()));
		Mysql.executeQuery(queryStatement);
	}
	
	public static void cancelReceiptHotel(int receiptID) throws SQLException {
		String queryStatement = String.format("CALL CancelReceiptHotel(%d,%d)",receiptID,HostController.getHotel().getHotelID());
		System.out.println(String.format("CALL CancelReceiptHotel(%d,%d)",receiptID,HostController.getHotel().getHotelID()));
		Mysql.executeQuery(queryStatement);
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------

	
	public static ArrayList<Receipts> queryReceiptsForHotel() throws SQLException {
		ArrayList<Receipts> tmpReceipts = new ArrayList<>();
		String queryStatement= String.format("SELECT receipt.*,hotel.id FROM receipt JOIN room ON (receipt.room_id = room.id) "
					+ "JOIN hotel ON (room.hotel_id = hotel.id)"
					+ "WHERE hotel_id = %s", HostController.getHotel().getHotelID());

		ResultSet receiptSet = Mysql.executeQuery(queryStatement);
		while (receiptSet.next()) {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2),
					receiptSet.getInt(3), receiptSet.getDate(4), receiptSet.getDate(5), receiptSet.getDate(6),
					receiptSet.getInt(7),receiptSet.getInt(8),receiptSet.getInt(9));//1-8 attribute of receipt,9 of hotel id
			tmpReceipts.add(tmpReceipt);
		}
		if (tmpReceipts.size() == 0)
			return null;
		return tmpReceipts;
	}
	
	public static ArrayList<Receipts> queryReceiptsForUser() throws SQLException {
		ArrayList<Receipts> tmpReceipts = new ArrayList<>();
		String queryStatement=String.format("SELECT receipt.*,hotel.id FROM receipt JOIN room ON (receipt.room_id = room.id) "
					+ "JOIN hotel ON (room.hotel_id = hotel.id)"
					+ "WHERE user_id = %s", LoginController.getUser().getUserID());
	
		ResultSet receiptSet = Mysql.executeQuery(queryStatement);
		while (receiptSet.next()) {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2),
					receiptSet.getInt(3), receiptSet.getDate(4), receiptSet.getDate(5), receiptSet.getDate(6),
					receiptSet.getInt(7),receiptSet.getInt(8),receiptSet.getInt(9));//1-8 attribute of receipt,9 of hotel id
			tmpReceipts.add(tmpReceipt);
		}
		if (tmpReceipts.size() == 0)
			return null;
		return tmpReceipts;
	}

	public static void updateReceiptStatusForUser() throws SQLException {
		String queryStatement = String.format("SELECT receipt.*,hotel.id FROM receipt JOIN room ON (receipt.room_id = room.id) "
					+ "JOIN hotel ON (room.hotel_id = hotel.id)"
					+ "WHERE user_id = %s", LoginController.getUser().getUserID());
		ResultSet receiptSet = Mysql.executeQuery(queryStatement);
		while (receiptSet.next()!=false) {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getDate(4), receiptSet.getDate(5), receiptSet.getDate(6),
					receiptSet.getInt(7),receiptSet.getInt(8),receiptSet.getInt(9));
			
			long now=System.currentTimeMillis();  
	        Date bookingDate=new Date(now);  
			
			updateStatus(tmpReceipt, bookingDate);
		}
	}
	
	public static void updateReceiptStatusForHotel() throws SQLException {
		String queryStatement = String.format("SELECT receipt.*,hotel.id FROM receipt JOIN room ON (receipt.room_id = room.id) "
				+ "JOIN hotel ON (room.hotel_id = hotel.id)"
				+ "WHERE hotel_id = %s", HostController.getHotel().getHotelID());
		ResultSet receiptSet = Mysql.executeQuery(queryStatement);
		while (receiptSet.next()!=false) {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getDate(4), receiptSet.getDate(5), receiptSet.getDate(6),
					receiptSet.getInt(7),receiptSet.getInt(8),receiptSet.getInt(9));
			
			long now=System.currentTimeMillis();  
	        Date bookingDate=new Date(now);  
			
			updateStatus(tmpReceipt, bookingDate);
		}
	}
	
	private static void updateStatus(Receipts receipt,Date dateNow) throws SQLException {
		if (receipt.getStatus() == 0) { // waiting for checkin
			if (receipt.getCheckinDate().compareTo(dateNow)<=0) {
				String updateStatement = "UPDATE receipt SET status = 1 WHERE id =  "+ receipt.getReceiptID();
				Mysql.executeUpdate(updateStatement);
			}
		} else { // waiting for checkout
			if (receipt.getCheckoutDate().before(dateNow)) {
				String updateStatement = "UPDATE receipt SET status = 2 WHERE id =  "+ receipt.getReceiptID();
				Mysql.executeUpdate(updateStatement);
			}
		}
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------

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

	public void setBookingDate(Date orderDate) {
		this.bookingDate = orderDate;
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

	public Date getBookingDate() {
		return bookingDate;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}
	// --------------------------------------------------------------------------------

	public String getPaymentProperty() {
		return paymentProperty.get();
	}

	public void setPaymentProperty(SimpleStringProperty paymentProperty) {
		this.paymentProperty = paymentProperty;
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

	public String getStatusProperty() {
		return statusProperty.get();
	}

	public void setStatusProperty(SimpleStringProperty statusProperty) {
		this.statusProperty = statusProperty;
	}

}
