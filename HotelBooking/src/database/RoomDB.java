package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import orders.Receipts;
import rooms.Room;

public class RoomDB {

	public static ArrayList<Room> queryRooms(int hotelID) throws SQLException {
		ArrayList<Room> tmpRooms = new ArrayList<>();
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM rooms where hotel_id = " + hotelID;

		ResultSet roomsSet = statement.executeQuery(queryStatement);
		while (roomsSet.next()) {
			Room tmpRoom = new Room(roomsSet.getInt(1), roomsSet.getInt(2), roomsSet.getInt(3), roomsSet.getInt(4));
			Receipts tmpReceipts = ReceiptDB.queryRooms(roomsSet.getInt(3));
			if (tmpReceipts == null) {
				System.out.println("1 ");
				tmpRoom.setCheckinDate(null);
				tmpRoom.setCheckoutDate(null);
				tmpRoom.setOrderDate(null);
				tmpRoom.setStatus(-1);
			} else {
				tmpReceipts.printInfo();
				tmpRoom.setCheckinDate(tmpReceipts.getCheckinDate());
				tmpRoom.setCheckoutDate(tmpReceipts.getCheckoutDate());
				tmpRoom.setOrderDate(tmpReceipts.getOrderDate());
				Date checkinDate = tmpRoom.getCheckinDate();
				Date checkoutDate = tmpRoom.getCheckoutDate();

				if (LocalDate.now().isBefore(LocalDate.of(checkinDate.getYear() + 1900, checkinDate.getMonth() + 1,
						checkinDate.getDate()))) {
					System.out.println("2 "+ tmpReceipts.getReceiptID());
					tmpRoom.setStatus(0);
				} else {
					if (LocalDate.now().isBefore(LocalDate.of(checkoutDate.getYear() + 1900,
							checkoutDate.getMonth() + 1, checkoutDate.getDate()))) {
						System.out.println("3 "+tmpReceipts.getReceiptID());
						tmpRoom.setStatus(1);
					} else {
//						System.out.println(LocalDate.now()+"/"+LocalDate.of(checkoutDate.getYear(), checkoutDate.getMonth() + 1,
//								checkoutDate.getDate()));
						System.out.println("4 "+tmpReceipts.getReceiptID());
						RoomDB.cancelRoom(tmpReceipts.getReceiptID());
						tmpRoom.setCheckinDate(null);
						tmpRoom.setCheckoutDate(null);
						tmpRoom.setOrderDate(null);
						tmpRoom.setStatus(-1);
						tmpRoom.setReceiptID(0);
					}
				}
			}
			tmpRooms.add(tmpRoom);
		}
		if (tmpRooms.size() == 0)
			return null;
		return tmpRooms;
	}

	public static ArrayList<Room> queryAvailableRooms(int hotelID) throws SQLException {
		
		ArrayList<Room> tmpRooms = new ArrayList<>();
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM rooms where hotel_id = " + hotelID + " AND status = -1";
		ResultSet roomsSet = statement.executeQuery(queryStatement);
		while (roomsSet.next()) {
			Room tmpRoom = new Room(roomsSet.getInt(1), roomsSet.getInt(2), roomsSet.getInt(3), roomsSet.getInt(4));
			tmpRoom.setCheckinDate(null);
			tmpRoom.setCheckoutDate(null);
			tmpRoom.setOrderDate(null);
			tmpRoom.setStatus(-1);
			tmpRooms.add(tmpRoom);
		}

		if (tmpRooms.size() == 0)
			return null;
		return tmpRooms;
	}
	
	public static int queryNumberOfAvailableRooms(int hotelID) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT count(room_id) FROM rooms where hotel_id = " + hotelID + " AND status = -1 GROUP BY hotel_id";
		ResultSet numberOfRooms = statement.executeQuery(queryStatement);
		if(numberOfRooms.next()==false)
			return 0;
		return numberOfRooms.getInt(1);
	}
	
	public static void bookRoom(int hotelID, int roomID, int receiptID) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String updateStatement = "UPDATE rooms Set receipt_id=" + receiptID + ",status=0 " + "where room_id = " + roomID
				+ " and hotel_id= " + hotelID;
		statement.executeUpdate(updateStatement);
	}

	public static void cancelRoom(int receiptID) throws SQLException {
		Receipts tmpReceipts = ReceiptDB.queryRooms(receiptID);
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String updateStatement = "UPDATE rooms SET receipt_id= null, status = -1" + "where room_id = "
				+ tmpReceipts.getRoomID() + " and hotel_id= " + tmpReceipts.getHotelID();
		System.out.println(updateStatement);
		statement.executeUpdate(updateStatement);
	}
}
