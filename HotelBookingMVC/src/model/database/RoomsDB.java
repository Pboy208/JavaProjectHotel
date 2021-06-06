package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import model.rooms.Rooms;

public class RoomsDB implements DBInterface {
	public static int queryNumberOfAvailableRooms(int hotelID, Date checkinDate, Date checkoutDate)
			throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT count(*) FROM receipt WHERE status !=2 AND hotel_id = " + hotelID + " "
				+ "AND ((checkin::date <= '" + checkinDate + "' AND checkout::date >= '" + checkoutDate + "')"
				+ "OR	(checkin::date >= '" + checkinDate + "' AND checkin::date <= '" + checkoutDate + "')"
				+ "OR (checkout::date >= '" + checkinDate + "' AND checkout::date <= '" + checkoutDate + "'))";
		ResultSet numberOfRoomsUnavailable = statement.executeQuery(queryStatement);
		numberOfRoomsUnavailable.next();
		return RoomsDB.queryNumberOfAllRooms(hotelID) - numberOfRoomsUnavailable.getInt(1);
	}

	public static ArrayList<Rooms> queryAvailableRooms(int hotelID, Date checkinDate, Date checkoutDate)
			throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT room_id,price FROM receipt WHERE status !=2 AND hotel_id = " + hotelID + " "
				+ "AND ((checkin::date <= '" + checkinDate + "' AND checkout::date >= '" + checkoutDate + "')"
				+ "OR	(checkin::date >= '" + checkinDate + "' AND checkin::date <= '" + checkoutDate + "')"
				+ "OR (checkout::date >= '" + checkinDate + "' AND checkout::date <= '" + checkoutDate + "'))"
				+ " ORDER BY price";
		ResultSet unavailableRoomsSet = statement.executeQuery(queryStatement);
		ArrayList<Rooms> unavailableRooms = new ArrayList<>();
		while (unavailableRoomsSet.next()) {
			unavailableRooms.add(new Rooms(unavailableRoomsSet.getInt(1), hotelID, unavailableRoomsSet.getInt(2)));
		}

		String queryStatement2 = "SELECT room_id,price FROM rooms where hotel_id =" + hotelID;
		ResultSet totalRoomsSet = statement.executeQuery(queryStatement2);
		ArrayList<Rooms> totalRooms = new ArrayList<>();
		while (totalRoomsSet.next()) {
			totalRooms.add(new Rooms(totalRoomsSet.getInt(1), hotelID, totalRoomsSet.getInt(2)));
		}
		if (unavailableRooms.size() == 0)
			return totalRooms;
		for (Rooms i : totalRooms) {
			for (Rooms j : unavailableRooms) {
				if (i == j) {
					totalRooms.remove(i);
				}
			}
		}
		return totalRooms;
	}
	
	
	@SuppressWarnings("deprecation")
	public static int queryPaymentForReceipts(int numberOfRoom, int hotelID, Date checkinDate, Date checkoutDate) throws SQLException {
		int payment=0;
		ArrayList<Rooms> availableRooms = RoomsDB.queryAvailableRooms(hotelID, checkinDate, checkoutDate);
		for (int i = 0; i < numberOfRoom; i++) {
			payment+=((availableRooms.get(i).getPrice())*(checkoutDate.getDay()-checkinDate.getDay()));	
		}
		return payment;
	}
	
	public static int queryNumberOfAllRooms(int hotelID) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT count(room_id) FROM rooms where hotel_id = " + hotelID;
		ResultSet numberOfRooms = statement.executeQuery(queryStatement);
		numberOfRooms.next();
		return numberOfRooms.getInt(1);
	}

	public static ArrayList<Rooms> queryAllRooms(int hotelID) throws SQLException {
		ArrayList<Rooms> allRooms = new ArrayList<>();
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM rooms where hotel_id = " + hotelID;
		ResultSet allRoomsSet = statement.executeQuery(queryStatement);
		while (allRoomsSet.next()) {
			allRooms.add(new Rooms(allRoomsSet.getInt(1), allRoomsSet.getInt(2), allRoomsSet.getInt(3)));
		}
		if (allRooms.size() == 0)
			return null;
		return allRooms;
	}
	
	public static int queryMinPrice(int hotelID)throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT min(price) FROM rooms where hotel_id = " + hotelID;
		ResultSet minPrice = statement.executeQuery(queryStatement);
		if(minPrice.next()) {
			return minPrice.getInt(1);
		}
		return 0;
	}
	
	@Override
	public void insertInstance(Object object) throws SQLException {
		Rooms room = (Rooms)object;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement="Select max(room_id) from rooms";
		ResultSet tmpCount = statement.executeQuery(queryStatement);
		tmpCount.next();
		int maxRoomID=tmpCount.getInt(1);
		room.setRoomID(maxRoomID+1);
		String updateStatment="INSERT INTO rooms values("+room.getRoomID()+","+room.getHotelID()+","+room.getPrice()+")";
		statement.executeUpdate(updateStatment);
	}

	@Override
	public Object queryInstance(int pk) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void updateInstance(Object object) throws SQLException {
		Rooms room = (Rooms)object;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String updateStatment="UPDATE rooms set price = "+ room.getPrice() + "where room_id=" +room.getRoomID();
		statement.executeUpdate(updateStatment);
	}

}
