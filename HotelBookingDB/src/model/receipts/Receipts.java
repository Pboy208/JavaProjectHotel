package model.receipts;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.HostController;
import controller.LoginController;
import java.sql.Date;
import java.sql.ResultSet;
import javafx.beans.property.SimpleStringProperty;
import model.database.DBInterface;
import model.database.Mysql;
import model.rooms.Hotels;
import model.rooms.Rooms;

public class Receipts implements DBInterface {

	private int receiptID;
	private int hotelID;
	private int roomID;
	private int userID;
	private int status;
	private Date orderDate;
	private Date checkinDate;
	private Date checkoutDate;
	private int price;

	private SimpleStringProperty hotelAddressProperty;
	private SimpleStringProperty hotelNameProperty;
	
	private SimpleStringProperty statusProperty;
	private SimpleStringProperty priceProperty;
	
	public Receipts(int receiptID, int userID, int roomID, Date checkinDate, Date checkoutDate,
			Date orderDate,int price, int status, int hotelID) throws SQLException {
		// TODO Auto-generated constructor stub
		this.setOrderDate(orderDate);
		this.setCheckinDate(checkinDate);
		this.setCheckoutDate(checkoutDate);
		this.setHotelID(hotelID);
		this.setRoomID(roomID);
		this.setUserID(userID);
		this.setReceiptID(receiptID);
		this.setStatus(status);
		this.price=price;
		
		
		this.priceProperty= new SimpleStringProperty(String.format("%,d", price));
		Hotels tmpHotel =(Hotels) new Hotels().queryInstance(hotelID);
		this.hotelAddressProperty = new SimpleStringProperty(tmpHotel.getAddress());
		this.hotelNameProperty = new SimpleStringProperty(tmpHotel.getName());

//		if (receiptID != 0)
//			this.receiptIDProperty = new SimpleStringProperty(Integer.toString(receiptID));
//		else
//			this.receiptIDProperty = new SimpleStringProperty("Null");

//		if (checkinDate != null)
//			this.checkinDateProperty = new SimpleStringProperty(checkinDate.toString());
//		else
//			this.checkinDateProperty = new SimpleStringProperty("Null");
//
//		if (checkoutDate != null)
//			this.checkoutDateProperty = new SimpleStringProperty(checkoutDate.toString());
//		else
//			this.checkoutDateProperty = new SimpleStringProperty("Null");
//
//		if (orderDate != null)
//			this.orderDateProperty = new SimpleStringProperty(orderDate.toString());
//		else
//			this.orderDateProperty = new SimpleStringProperty("Null");

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
	
	public Receipts(int receiptID, int userID, int roomID, Date checkinDate, Date checkoutDate,
			Date orderDate,int price, int status) throws SQLException {
		// TODO Auto-generated constructor stub
		this.setOrderDate(orderDate);
		this.setCheckinDate(checkinDate);
		this.setCheckoutDate(checkoutDate);
		this.setRoomID(roomID);
		this.setUserID(userID);
		this.setReceiptID(receiptID);
		this.setStatus(status);
		this.price=price;
		
		
		this.priceProperty= new SimpleStringProperty(String.format("%,d", price));

//		if (receiptID != 0)
//			this.receiptIDProperty = new SimpleStringProperty(Integer.toString(receiptID));
//		else
//			this.receiptIDProperty = new SimpleStringProperty("Null");

//		if (checkinDate != null)
//			this.checkinDateProperty = new SimpleStringProperty(checkinDate.toString());
//		else
//			this.checkinDateProperty = new SimpleStringProperty("Null");
//
//		if (checkoutDate != null)
//			this.checkoutDateProperty = new SimpleStringProperty(checkoutDate.toString());
//		else
//			this.checkoutDateProperty = new SimpleStringProperty("Null");
//
//		if (orderDate != null)
//			this.orderDateProperty = new SimpleStringProperty(orderDate.toString());
//		else
//			this.orderDateProperty = new SimpleStringProperty("Null");

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
	
	public Receipts( int userID,int roomID, Date checkinDate, Date checkoutDate,
			Date orderDate) {
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
		System.out.println(receiptID + "/" + roomID);
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public static ArrayList<Receipts> queryReceiptsForHotel(Object object) throws SQLException {
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
	
	public static ArrayList<Receipts> queryReceiptsForUser(Object object) throws SQLException {
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
	
	@SuppressWarnings("deprecation")
	public static void insertReciepts(int numberOfRoom, int hotelID, Date checkinDate, Date checkoutDate)
			throws SQLException {
		
		LocalDate localDate = LocalDate.now();
		
		int price = Hotels.queryPriceByID(hotelID);
		
		ArrayList<Rooms> availableRooms = Rooms.queryAvailableRooms(hotelID, checkinDate, checkoutDate);
		
		Date orderDate = new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
		
		for (int i = 0; i < numberOfRoom; i++) {
			int roomID = availableRooms.get(i).getRoomID();
			Receipts receipt = new Receipts(LoginController.getUser().getUserID(),roomID,checkinDate,checkoutDate,orderDate);
			int totalDay = checkoutDate.getDay()-checkinDate.getDay();
			receipt.setPrice(price*totalDay);
			new Receipts().insertInstance(receipt);
		}
	}
	
	public static void cancelReciepts(int receiptID) throws SQLException {
		String updateStatement = "UPDATE receipt SET status = 2 WHERE id=" + receiptID;
		Mysql.executeUpdate(updateStatement);
	}

	public static void updateReceiptStatusForUser(Object object) throws SQLException {
		String queryStatement = String.format("SELECT receipt.*,hotel.id FROM receipt JOIN room ON (receipt.room_id = room.id) "
					+ "JOIN hotel ON (room.hotel_id = hotel.id)"
					+ "WHERE user_id = %s", LoginController.getUser().getUserID());
		ResultSet receiptSet = Mysql.executeQuery(queryStatement);
		while (receiptSet.next()!=false) {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getDate(4), receiptSet.getDate(5), receiptSet.getDate(6),
					receiptSet.getInt(7),receiptSet.getInt(8),receiptSet.getInt(9));
			
			LocalDate localDate = LocalDate.now();
			@SuppressWarnings("deprecation")
			Date dateNow = new Date(localDate.getYear() - 1900,localDate.getMonthValue() - 1, localDate.getDayOfMonth());
			updateStatus(tmpReceipt, dateNow);
		}
	}
	
	public static void updateReceiptStatusForHotel(Object object) throws SQLException {
		String queryStatement = String.format("SELECT receipt.*,hotel.id FROM receipt JOIN room ON (receipt.room_id = room.id) "
				+ "JOIN hotel ON (room.hotel_id = hotel.id)"
				+ "WHERE hotel_id = %s", HostController.getHotel().getHotelID());
		ResultSet receiptSet = Mysql.executeQuery(queryStatement);
		while (receiptSet.next()!=false) {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getDate(4), receiptSet.getDate(5), receiptSet.getDate(6),
					receiptSet.getInt(7),receiptSet.getInt(8),receiptSet.getInt(9));
			
			LocalDate localDate = LocalDate.now();
			@SuppressWarnings("deprecation")
			Date dateNow = new Date(localDate.getYear() - 1900,localDate.getMonthValue() - 1, localDate.getDayOfMonth());
			updateStatus(tmpReceipt, dateNow);
		}
	}
	
	public static void updateStatus(Receipts receipt,Date dateNow) throws SQLException {
		if (receipt.getStatus() == 0) { // waiting for checkin
			if (receipt.getCheckinDate().compareTo(dateNow)<=0) {
				String updateStatement = "UPDATE receipt SET status = 1 WHERE id =  "+ receipt.getReceiptID();
				Mysql.executeUpdate(updateStatement);
			}
		} else { // waiting for checkout
			if (receipt.getCheckoutDate().before(dateNow)) {
				String updateStatement = "UPDATE receipt SET status = -1 WHERE id =  "+ receipt.getReceiptID();
				Mysql.executeUpdate(updateStatement);
			}
		}
	}
	@Override
	public void insertInstance(Object object) throws SQLException {
		Receipts receipt = (Receipts) object;
		String insertStatement = String.format("INSERT INTO receipt(user_id, room_id, checkin_date, checkout_date,booking_date,payment,status)"
					+ " VALUES(%d,%d,'%s','%s','%s',%d,%d)"
					,receipt.getUserID(),receipt.getRoomID(),receipt.getCheckinDate(),receipt.getCheckoutDate(),receipt.getOrderDate(),receipt.getPrice(),receipt.getStatus());
		Mysql.executeUpdate(insertStatement);	
	}

	@Override
	public Object queryInstance(int receiptID) throws SQLException {
		String queryStatement =String.format("SELECT instance.*,hotel.id"
		+" FROM hotel,room,(SELECT * FROM receipt WHERE id = %d) AS instance"
		+" WHERE instance.room_id = room.id AND room.hotel_id = hotel.id",receiptID);
		ResultSet receiptSet = Mysql.executeQuery(queryStatement);
		if (receiptSet.next() == false) {
			return null;
		} else {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2),receiptSet.getInt(3),
					receiptSet.getDate(4), receiptSet.getDate(5), receiptSet.getDate(6),
					receiptSet.getInt(7),receiptSet.getInt(8),receiptSet.getInt(9));
			return tmpReceipt;
		}
	}

	@Override
	public void updateInstance(Object object) throws SQLException {
		return;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

//
//	public String getReceiptIDProperty() {
//		return receiptIDProperty.get();
//	}
//
//	public void setReceiptIDProperty(SimpleStringProperty receiptIDProperty) {
//		this.receiptIDProperty = receiptIDProperty;
//	}

	public String getStatusProperty() {
		return statusProperty.get();
	}

	public void setStatusProperty(SimpleStringProperty statusProperty) {
		this.statusProperty = statusProperty;
	}

//	public String getOrderDateProperty() {
//		return orderDateProperty.get();
//	}
//
//	public void setOrderDateProperty(SimpleStringProperty orderDateProperty) {
//		this.orderDateProperty = orderDateProperty;
//	}
//
//	public String getCheckinDateProperty() {
//		return checkinDateProperty.get();
//	}
//
//	public void setCheckinDateProperty(SimpleStringProperty checkinDateProperty) {
//		this.checkinDateProperty = checkinDateProperty;
//	}
//
//	public String getCheckoutDateProperty() {
//		return checkoutDateProperty.get();
//	}
//
//	public void setCheckoutDateProperty(SimpleStringProperty checkoutDateProperty) {
//		this.checkoutDateProperty = checkoutDateProperty;
//	}
}
