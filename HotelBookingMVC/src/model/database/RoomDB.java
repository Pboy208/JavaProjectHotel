package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import model.rooms.Room;

public class RoomDB {
	public static int queryNumberOfAvailableRooms(int hotelID, Date checkinDate, Date checkoutDate)
			throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT count(*) FROM receipt WHERE status !=2 AND hotel_id = " + hotelID + " "
				+ "AND ((checkin::date <= '" + checkinDate + "' AND checkout::date >= '" + checkoutDate + "')"
				+ "OR	(checkin::date >= '" + checkinDate + "' AND checkin::date <= '" + checkoutDate + "')"
				+ "OR (checkout::date >= '" + checkinDate + "' AND checkout::date <= '" + checkoutDate + "'))";
		ResultSet numberOfRoomsUnavailable = statement.executeQuery(queryStatement);
		System.out.println(queryStatement);
		numberOfRoomsUnavailable.next();
		return RoomDB.queryNumberOfAllRooms(hotelID) - numberOfRoomsUnavailable.getInt(1);
	}

	public static ArrayList<Integer> queryAvailableRooms(int hotelID, Date checkinDate, Date checkoutDate)
			throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT room_id FROM receipt WHERE status !=2 AND hotel_id = " + hotelID + " "
				+ "AND ((checkin::date <= '" + checkinDate + "' AND checkout::date >= '" + checkoutDate + "')"
				+ "OR	(checkin::date >= '" + checkinDate + "' AND checkin::date <= '" + checkoutDate + "')"
				+ "OR (checkout::date >= '" + checkinDate + "' AND checkout::date <= '" + checkoutDate + "'))";
		ResultSet unavailableRoomsSet = statement.executeQuery(queryStatement);
		ArrayList<Integer> unavailableRooms = new ArrayList<>();
		while (unavailableRoomsSet.next()) {
			unavailableRooms.add(unavailableRoomsSet.getInt(1));
		}

		String queryStatement2 = "SELECT room_id FROM rooms where hotel_id =" + hotelID;
		ResultSet totalRoomsSet = statement.executeQuery(queryStatement2);
		ArrayList<Integer> totalRooms = new ArrayList<>();
		while (totalRoomsSet.next()) {
			totalRooms.add(totalRoomsSet.getInt(1));
		}
		if (unavailableRooms.size() == 0)
			return totalRooms;
		for (int i : totalRooms) {
			for (int j : unavailableRooms) {
				if (i == j) {
					totalRooms.remove(i);
				}
			}
		}
		return totalRooms;
	}

	public static int queryNumberOfAllRooms(int hotelID) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT count(room_id) FROM rooms where hotel_id = " + hotelID;
		ResultSet numberOfRooms = statement.executeQuery(queryStatement);
		numberOfRooms.next();
		return numberOfRooms.getInt(1);
	}

	public static ArrayList<Room> queryAllRooms(int hotelID) throws SQLException {
		ArrayList<Room> allRooms = new ArrayList<>();
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM rooms where hotel_id = " + hotelID;
		ResultSet allRoomsSet = statement.executeQuery(queryStatement);
		while (allRoomsSet.next()) {
			allRooms.add(new Room(allRoomsSet.getInt(1), allRoomsSet.getInt(2), allRoomsSet.getInt(3)));
		}
		if (allRooms.size() == 0)
			return null;
		return allRooms;
	}
}
