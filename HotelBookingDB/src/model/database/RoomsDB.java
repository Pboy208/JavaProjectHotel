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
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = String.format("SELECT count(*) FROM receipt WHERE status !=2 AND hotel_id = %s "
						+ "AND ((checkin_date <= '%s' AND checkout_date >= '%s')"
						+ "OR   (checkin_date >= '%s' AND checkin_date  <= '%s')"
						+ "OR   (checkout_date >= '%s' AND checkout_date <= '%s'))",
						hotelID,checkinDate,checkoutDate,checkinDate,checkoutDate,checkinDate,checkoutDate);

		ResultSet numberOfRoomsUnavailable = statement.executeQuery(queryStatement);
		numberOfRoomsUnavailable.next();
		return RoomsDB.queryNumberOfAllRooms(hotelID) - numberOfRoomsUnavailable.getInt(1);
	}

	public static ArrayList<Rooms> queryAvailableRooms(int hotelID, Date checkinDate, Date checkoutDate)
			throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = String.format(" SELECT id FROM room WHERE hotel_id = %s "
						+ "AND id NOT IN (SELECT room_id FROM receipt WHERE status !=2 AND hotel_id = %s "
						+ "AND ((checkin_date <= '%s' AND checkout_date >= '%s')"
						+ "OR   (checkin_date >= '%s' AND checkin_date  <= '%s')"
						+ "OR   (checkout_date >= '%s' AND checkout_date <= '%s')))",
						hotelID,hotelID,checkinDate,checkoutDate,checkinDate,checkoutDate,checkinDate,checkoutDate);
		
		ResultSet availableRoomsSet = statement.executeQuery(queryStatement);
		
		ArrayList<Rooms> availableRooms = new ArrayList<>();
		while (availableRoomsSet.next()) {
			availableRooms.add(new Rooms(availableRoomsSet.getInt(1), hotelID));
		}

		if(availableRooms.size()==0)
			return null;
		else {
			return availableRooms;
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public static int queryPaymentForReceipts(int numberOfRoom, int hotelID, Date checkinDate, Date checkoutDate) throws SQLException {
		int price= HotelsDB.queryPriceByID(hotelID);
		int payment =(price * numberOfRoom *(checkoutDate.getDay()-checkinDate.getDay()));	
		return payment;
	}
	
	public static int queryNumberOfAllRooms(int hotelID) throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT count(id) FROM room where hotel_id = " + hotelID;
		ResultSet numberOfRooms = statement.executeQuery(queryStatement);
		numberOfRooms.next();
		return numberOfRooms.getInt(1);
	}

	public static ArrayList<Rooms> queryAllRooms(int hotelID) throws SQLException {
		ArrayList<Rooms> allRooms = new ArrayList<>();
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM room where hotel_id = " + hotelID;
		ResultSet allRoomsSet = statement.executeQuery(queryStatement);
		while (allRoomsSet.next()) {
			allRooms.add(new Rooms(allRoomsSet.getInt(1), allRoomsSet.getInt(2)));
		}
		if (allRooms.size() == 0)
			return null;
		return allRooms;
	}
	
	@Override
	public void insertInstance(Object object) throws SQLException {
		Rooms room = (Rooms)object;
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String insertStatement="INSERT INTO room VALUES("+room.getRoomID()+","+room.getHotelID()+")";
		statement.executeUpdate(insertStatement);
	}

	@Override
	public Object queryInstance(int pk) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void updateInstance(Object object) throws SQLException {
//		Rooms room = (Rooms)object;
//		Connection connection = Postgre.makeConnection();
//		Statement statement = connection.createStatement();
//		String updateStatment="UPDATE rooms set price = "+ room.getPrice() + "where room_id=" +room.getRoomID();
//		statement.executeUpdate(updateStatment);
	}

}
