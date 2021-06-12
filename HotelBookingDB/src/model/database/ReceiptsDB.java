package model.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.HostController;
import controller.LoginController;
import model.receipts.Receipts;
import model.rooms.Rooms;
import model.users.HotelEmployees;
import model.users.Users;

public class ReceiptsDB implements DBInterface{

	public static ArrayList<Receipts> queryReceipts(Object object) throws SQLException {
		ArrayList<Receipts> tmpReceipts = new ArrayList<>();
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement=null;
		if(object instanceof HotelEmployees) {
			queryStatement = String.format("SELECT receipt.*,hotel.id FROM receipt JOIN room ON (receipt.room_id = room.id) "
					+ "JOIN hotel ON (room.hotel_id = hotel.id)"
					+ "WHERE hotel_id = %s", HostController.getHotel().getHotelID());
		}
		else {
			queryStatement = String.format("SELECT receipt.*,hotel.id FROM receipt JOIN room ON (receipt.room_id = room.id) "
					+ "JOIN hotel ON (room.hotel_id = hotel.id)"
					+ "WHERE user_id = %s", HostController.getHotel().getHotelID());
		}
	
		ResultSet receiptSet = statement.executeQuery(queryStatement);
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
		
		int price = HotelsDB.queryPriceByID(hotelID);
		
		ArrayList<Rooms> availableRooms = RoomsDB.queryAvailableRooms(hotelID, checkinDate, checkoutDate);
		
		Date orderDate = new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
		
		for (int i = 0; i < numberOfRoom; i++) {
			int roomID = availableRooms.get(i).getRoomID();
			Receipts receipt = new Receipts(LoginController.getUser().getUserID(),roomID,checkinDate,checkoutDate,orderDate);
			int totalDay = checkoutDate.getDay()-checkinDate.getDay();
			receipt.setPrice(price*totalDay);
			new ReceiptsDB().insertInstance(receipt);
		}
	}
	
	public static void cancelReciepts(int receiptID) throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "UPDATE receipt SET status = 2 WHERE id=" + receiptID;
		statement.executeUpdate(queryStatement);
	}

	public static void updateReceiptStatus(Object object) throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement=null;
		if(object instanceof HotelEmployeesDB) {
			queryStatement = String.format("SELECT receipt.*,hotel.id FROM receipt JOIN room ON (receipt.room_id = room.id) "
					+ "JOIN hotel ON (room.hotel_id = hotel.id)"
					+ "WHERE hotel_id = %s", HostController.getHotel().getHotelID());
		}
		else {
			queryStatement = String.format("SELECT receipt.*,hotel.id FROM receipt JOIN room ON (receipt.room_id = room.id) "
					+ "JOIN hotel ON (room.hotel_id = hotel.id)"
					+ "WHERE user_id = %s", HostController.getHotel().getHotelID());
		}
		ResultSet receiptSet = statement.executeQuery(queryStatement);
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
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		if (receipt.getStatus() == 0) { // waiting for checkin
			if (receipt.getCheckinDate().compareTo(dateNow)<=0) {
				String updateStatement = "UPDATE receipt SET status = 1 WHERE id =  "+ receipt.getReceiptID();
				statement.executeUpdate(updateStatement);
			}
		} else { // waiting for checkout
			if (receipt.getCheckoutDate().before(dateNow)) {
				String updateStatement = "UPDATE receipt SET status = -1 WHERE id =  "+ receipt.getReceiptID();
				statement.executeUpdate(updateStatement);
			}
		}
	}
	@Override
	public void insertInstance(Object object) throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		Receipts receipt = (Receipts) object;
		String insertStatement = String.format("INSERT INTO receipt(user_id, room_id, checkin_date, checkout_date,booking_date,payment,status)"
					+ " VALUES(%d,%d,'%s','%s','%s',%d,%d)"
					,receipt.getUserID(),receipt.getRoomID(),receipt.getCheckinDate(),receipt.getCheckoutDate(),receipt.getOrderDate(),receipt.getPrice(),receipt.getStatus());
		
		statement.executeUpdate(insertStatement);	
	}

	@Override
	public Object queryInstance(int receiptID) throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM receipt WHERE id = " + receiptID;
		ResultSet receiptSet = statement.executeQuery(queryStatement);
		if (receiptSet.next() == false) {
			return null;
		} else {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2),receiptSet.getInt(3),
					receiptSet.getDate(4), receiptSet.getDate(5), receiptSet.getDate(6),
					receiptSet.getInt(7),receiptSet.getInt(8));
			return tmpReceipt;
		}
	}

	@Override
	public void updateInstance(Object object) throws SQLException {
		return;
	}

}
