package model.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.LoginController;
import model.receipts.Receipts;
import model.rooms.Rooms;
import model.users.HotelEmployees;
import model.users.Users;

public class ReceiptsDB implements DBInterface{

	public static ArrayList<Receipts> queryReceipts(Object object) throws SQLException {
		ArrayList<Receipts> tmpReceipts = new ArrayList<>();
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement=null;
		if(object instanceof HotelEmployees) {
			queryStatement = "SELECT * FROM receipt where hotel_id = " + ((HotelEmployees)object).getHotelID();
			//System.out.println(queryStatement);
		}
		else {
			queryStatement = "SELECT * FROM receipt where user_id = " + ((Users)object).getUserID();
			//System.out.println(queryStatement);
		}
		ResultSet receiptSet = statement.executeQuery(queryStatement);
		while (receiptSet.next()) {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getInt(4), receiptSet.getDate(5), receiptSet.getDate(6), receiptSet.getDate(7),
					receiptSet.getInt(8),receiptSet.getInt(9));
			tmpReceipts.add(tmpReceipt);
		}
		if (tmpReceipts.size() == 0)
			return null;
		return tmpReceipts;
	}

	@SuppressWarnings("deprecation")
	public static void insertReciepts(int numberOfRoom, int hotelID, Date checkinDate, Date checkoutDate)
			throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT max(receipt_id) FROM receipt";
		ResultSet count = statement.executeQuery(queryStatement);
		count.next();
		int currentReceiptID = count.getInt(1);
		LocalDate localDate = LocalDate.now();
		ArrayList<Rooms> availableRooms = RoomsDB.queryAvailableRooms(hotelID, checkinDate, checkoutDate);
		Date orderDate = new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
		for (int i = 0; i < numberOfRoom; i++) {
			int tmpReceiptID = currentReceiptID + 1 + i;
			int roomID = availableRooms.get(i).getRoomID();
			Receipts receipt = new Receipts(tmpReceiptID,hotelID,roomID,LoginController.getUser().getUserID(),checkinDate,checkoutDate,orderDate);
			int totalDay = checkoutDate.getDay()-checkinDate.getDay();
			receipt.setPrice(availableRooms.get(i).getPrice()*totalDay);
			new ReceiptsDB().insertInstance(receipt);
		}
	}

	public static void cancelReciepts(int receiptID) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "update receipt set status = 2 where receipt_id=" + receiptID;
		statement.executeUpdate(queryStatement);
	}

	public static void updateReceiptStatus(Object object) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement=null;
		if(object instanceof HotelEmployeesDB) {
			queryStatement = "SELECT * FROM receipt WHERE status in (0,1) AND hotel_id = " + ((HotelEmployees)object).getHotelID();
		}
		else {
			queryStatement = "SELECT * FROM receipt WHERE status in (0,1) AND user_id = " + ((Users)object).getUserID();
		}
		ResultSet receiptSet = statement.executeQuery(queryStatement);
		while (receiptSet.next()!=false) {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getInt(4), receiptSet.getDate(5), receiptSet.getDate(6), receiptSet.getDate(7),
					receiptSet.getInt(8),receiptSet.getInt(9));
			LocalDate localDate = LocalDate.now();
			@SuppressWarnings("deprecation")
			Date dateNow = new Date(localDate.getYear() - 1900,localDate.getMonthValue() - 1, localDate.getDayOfMonth());
			updateStatus(tmpReceipt, dateNow);
		}
	}
	
	
	public static void updateStatus(Receipts receipt,Date dateNow) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		if (receipt.getStatus() == 0) { // waiting for checkin
			if (receipt.getCheckinDate().compareTo(dateNow)<=0) {
				String updateStatement = "UPDATE receipt SET status =1 WHERE receipt_id =  "
						+ receipt.getReceiptID();
				statement.executeUpdate(updateStatement);
			}
		} else { // waiting for checkout
			if (receipt.getCheckoutDate().before(dateNow)) {
				String updateStatement = "UPDATE receipt SET status = -1 WHERE receipt_id =  "
						+ receipt.getReceiptID();
				statement.executeUpdate(updateStatement);
			}
		}
	}
	@Override
	public void insertInstance(Object object) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		Receipts receipt = (Receipts) object;
		String insertStatement = "INSERT INTO receipt(receipt_id, hotel_id, room_id, user_id, checkin, checkout,order_date,status,price)"
					+ " VALUES (" + receipt.getReceiptID() + "," + receipt.getHotelID() + "," + receipt.getRoomID() + ","
					+ receipt.getUserID() + ",'" + receipt.getCheckinDate() + "','" + receipt.getCheckoutDate() + "','" + receipt.getOrderDate()
					+ "',0,"+receipt.getPrice()+")";
		statement.executeUpdate(insertStatement);	
	}

	@Override
	public Object queryInstance(int receiptID) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM receipt where receipt_id = " + receiptID;
		ResultSet receiptSet = statement.executeQuery(queryStatement);
		if (receiptSet.next() == false) {
			return null;
		} else {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getInt(4), receiptSet.getDate(5), receiptSet.getDate(6), receiptSet.getDate(7),
					receiptSet.getInt(8),receiptSet.getInt(9));
			return tmpReceipt;
		}
	}

	@Override
	public void updateInstance(Object object) throws SQLException {
		return;
	}

}
