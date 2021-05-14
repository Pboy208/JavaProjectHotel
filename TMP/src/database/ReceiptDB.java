package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import orders.Receipts;
public class ReceiptDB {

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
		System.out.println("Testing: " + queryStatement);
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

	@SuppressWarnings("deprecation")
	public static void updateReceiptStatusClients(int userID) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM receipt WHERE status in (0,1) AND user_id = " + userID;
		ResultSet receiptSet = statement.executeQuery(queryStatement);
		while(receiptSet.next()) {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getInt(4), receiptSet.getDate(5), receiptSet.getDate(6), receiptSet.getDate(7),
					receiptSet.getInt(8));
			LocalDate localDate = LocalDate.now();
			if(tmpReceipt.getStatus()==0) { //waiting for checkin
				if(tmpReceipt.getCheckinDate().before(new Date(localDate.getYear()-1900,localDate.getMonthValue()-1,localDate.getDayOfMonth())))
				{
					String updateStatement = "UPDATE receipt SET status =1 WHERE receipt_id =  " + tmpReceipt.getReceiptID();
					statement.executeUpdate(updateStatement);
				}
			}
			else { //waiting for checkout
				if(tmpReceipt.getCheckoutDate().before(new Date(localDate.getYear()-1900,localDate.getMonthValue()-1,localDate.getDayOfMonth())))
				{
					String updateStatement = "UPDATE receipt SET status = -1 WHERE receipt_id =  " + tmpReceipt.getReceiptID();
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
		while(receiptSet.next()) {
			Receipts tmpReceipt = new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3),
					receiptSet.getInt(4), receiptSet.getDate(5), receiptSet.getDate(6), receiptSet.getDate(7),
					receiptSet.getInt(8));
			LocalDate localDate = LocalDate.now();
			if(tmpReceipt.getStatus()==0) { //waiting for checkin
				if(tmpReceipt.getCheckinDate().before(new Date(localDate.getYear()-1900,localDate.getMonthValue()-1,localDate.getDayOfMonth())))
				{
					String updateStatement = "UPDATE receipt SET status =1 WHERE receipt_id =  " + tmpReceipt.getReceiptID();
					statement.executeUpdate(updateStatement);
				}
			}
			else { //waiting for checkout
				if(tmpReceipt.getCheckoutDate().before(new Date(localDate.getYear()-1900,localDate.getMonthValue()-1,localDate.getDayOfMonth())))
				{
					String updateStatement = "UPDATE receipt SET status = -1 WHERE receipt_id =  " + tmpReceipt.getReceiptID();
					statement.executeUpdate(updateStatement);
				}
			}
		}
	}

}
