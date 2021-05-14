package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;

import controller.LoginController;

public class BookAndCancelDB {

	public static void insertReciepts(int numberOfRoom, int hotelID, Date checkinDate, Date checkoutDate)
			throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT max(receipt_id) FROM receipt";
		ResultSet count = statement.executeQuery(queryStatement);
		count.next();
		int currentReceiptID = count.getInt(1);
		LocalDate localDate = LocalDate.now();
		ArrayList<Integer> availableRooms = RoomDB.queryAvailableRooms(hotelID, checkinDate, checkoutDate);
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

}
