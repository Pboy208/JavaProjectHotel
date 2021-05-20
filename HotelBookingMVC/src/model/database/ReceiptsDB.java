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

public class ReceiptsDB {

	public static Receipts queryRooms(int receiptID) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM receipt where receipt_id = " + receiptID;
		ResultSet receiptSet = statement.executeQuery(queryStatement);
		if (receiptSet.next() == false) {
			System.out.println(receiptID + " Doest not exists");
			return null;
		} else {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getInt(4), receiptSet.getDate(5), receiptSet.getDate(6), receiptSet.getDate(7),
					receiptSet.getInt(8));
			System.out.print("ReceiptDB :");
			tmpReceipt.printInfo();
			return tmpReceipt;
		}
	}

	public static ArrayList<Receipts> queryReceiptsForHotel(int hotelID) throws SQLException {
		ArrayList<Receipts> tmpReceipts = new ArrayList<>();
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM receipt where hotel_id = " + hotelID;
		ResultSet receiptSet = statement.executeQuery(queryStatement);
		System.out.println("begin " + hotelID);
		while (receiptSet.next()) {
			System.out.println("Testing...");
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getInt(4), receiptSet.getDate(5), receiptSet.getDate(6), receiptSet.getDate(7),
					receiptSet.getInt(8));
			tmpReceipts.add(tmpReceipt);
		}
		System.out.println("end");
		if (tmpReceipts.size() == 0)
			return null;
		return tmpReceipts;
	}

	public static ArrayList<Receipts> queryReceiptsForClient(int userID) throws SQLException {
		ArrayList<Receipts> tmpReceipts = new ArrayList<>();
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM receipt where user_id = " + userID;
		ResultSet receiptSet = statement.executeQuery(queryStatement);
		while (receiptSet.next()) {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getInt(4), receiptSet.getDate(5), receiptSet.getDate(6), receiptSet.getDate(7),
					receiptSet.getInt(8));
			tmpReceipts.add(tmpReceipt);
		}
		if (tmpReceipts.size() == 0)
			return null;
		return tmpReceipts;
	}

	public static void insertReciepts(int numberOfRoom, int hotelID, Date checkinDate, Date checkoutDate)
			throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT max(receipt_id) FROM receipt";
		ResultSet count = statement.executeQuery(queryStatement);
		count.next();
		int currentReceiptID = count.getInt(1);
		LocalDate localDate = LocalDate.now();
		ArrayList<Integer> availableRooms = RoomsDB.queryAvailableRooms(hotelID, checkinDate, checkoutDate);
		@SuppressWarnings("deprecation")
		Date orderDate = new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
		for (int i = 0; i < numberOfRoom; i++) {
			int tmpReceiptID = currentReceiptID + 1 + i;
			int roomID = availableRooms.get(i);
			String insertStatement = "INSERT INTO receipt(receipt_id, hotel_id, room_id, user_id, checkin, checkout,order_date,status)"
					+ " VALUES (" + tmpReceiptID + "," + hotelID + "," + roomID + ","
					+ LoginController.getUser().getId() + ",'" + checkinDate + "','" + checkoutDate + "','" + orderDate
					+ "',0)";
			System.out.println(insertStatement);
			statement.executeUpdate(insertStatement);
		}
	}

	public static void cancelReciepts(int receiptID) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "update receipt set status = 2 where receipt_id=" + receiptID;
		statement.executeUpdate(queryStatement);
	}

	@SuppressWarnings("deprecation")
	public static void updateReceiptStatusClients(int userID) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT count(*) FROM receipt WHERE status in (0,1) AND user_id = " + userID;
		ResultSet receiptSet = statement.executeQuery(queryStatement);
//		receiptSet.next();
//		System.out.println(receiptSet.getInt(1));
		while (receiptSet.next()==true) {
			System.out.println("abc");
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getInt(4), receiptSet.getDate(5), receiptSet.getDate(6), receiptSet.getDate(7),
					receiptSet.getInt(8));
			LocalDate localDate = LocalDate.now();
			if (tmpReceipt.getStatus() == 0) { // waiting for checkin
				if (tmpReceipt.getCheckinDate().compareTo(new Date(localDate.getYear() - 1900,
						localDate.getMonthValue() - 1, localDate.getDayOfMonth()))<=0) {
					String updateStatement = "UPDATE receipt SET status =1 WHERE receipt_id =  "
							+ tmpReceipt.getReceiptID();
					statement.executeUpdate(updateStatement);
				}
			} else { // waiting for checkout
				if (tmpReceipt.getCheckoutDate().before(new Date(localDate.getYear() - 1900,
						localDate.getMonthValue() - 1, localDate.getDayOfMonth()))) {
					String updateStatement = "UPDATE receipt SET status = -1 WHERE receipt_id =  "
							+ tmpReceipt.getReceiptID();
					statement.executeUpdate(updateStatement);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void updateReceiptStatusHotels(int hotelID) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM receipt WHERE status in (0,1) AND hotel_id = " + hotelID;
		ResultSet receiptSet = statement.executeQuery(queryStatement);
		while (receiptSet.next()!=false) {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getInt(4), receiptSet.getDate(5), receiptSet.getDate(6), receiptSet.getDate(7),
					receiptSet.getInt(8));
			LocalDate localDate = LocalDate.now();
			if (tmpReceipt.getStatus() == 0) { // waiting for checkin
				if (tmpReceipt.getCheckinDate().compareTo(new Date(localDate.getYear() - 1900,
						localDate.getMonthValue() - 1, localDate.getDayOfMonth()))<=0) {
					String updateStatement = "UPDATE receipt SET status =1 WHERE receipt_id =  "
							+ tmpReceipt.getReceiptID();
					statement.executeUpdate(updateStatement);
				}
			} else { // waiting for checkout
				if (tmpReceipt.getCheckoutDate().before(new Date(localDate.getYear() - 1900,
						localDate.getMonthValue() - 1, localDate.getDayOfMonth()))) {
					String updateStatement = "UPDATE receipt SET status = -1 WHERE receipt_id =  "
							+ tmpReceipt.getReceiptID();
					statement.executeUpdate(updateStatement);
				}
			}
		}
	}

}
