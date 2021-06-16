package model.rooms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.database.DBInterface;
import model.database.Mysql;

public class Rooms implements DBInterface {
	private int roomID; 
	private int hotelID;

	public Rooms() {
	}
	public Rooms(int roomID, int hotelID) {
		setHotelID(hotelID);
		setRoomID(roomID);
	}

	//--------------------------------------------------------------------------------------------------------------------------
	public void printInfo() {
		System.out.println(roomID + "/" + hotelID );
	}
	
	public static int queryNumberOfAvailableRooms(int hotelID, Date checkinDate, Date checkoutDate)
			throws SQLException {
		String queryStatement = String.format("SELECT COUNT(id) FROM room WHERE hotel_id = %d " 
										+"AND id NOT IN (SELECT room_id FROM receipt WHERE status !=2 AND hotel_id = %d " 
										+"AND ((checkin_date <= '%s' AND checkout_date >= '%s')" 
										+"OR   (checkin_date >= '%s' AND checkin_date  <= '%s')" 
										+"OR   (checkout_date >= '%s' AND checkout_date <= '%s')))",
						hotelID,hotelID,checkinDate,checkoutDate,checkinDate,checkoutDate,checkinDate,checkoutDate);

		ResultSet numberOfRoomsAvailable = Mysql.executeQuery(queryStatement);
		numberOfRoomsAvailable.next();
		return numberOfRoomsAvailable.getInt(1);
	}

	public static ArrayList<Rooms> queryAvailableRooms(int hotelID, Date checkinDate, Date checkoutDate)
			throws SQLException {
		String queryStatement = String.format(" SELECT id FROM room WHERE hotel_id = %d "
						+ "AND id NOT IN (SELECT room_id FROM receipt WHERE status !=2 AND hotel_id = %d "
						+ "AND ((checkin_date <= '%s' AND checkout_date >= '%s')"
						+ "OR   (checkin_date >= '%s' AND checkin_date  <= '%s')"
						+ "OR   (checkout_date >= '%s' AND checkout_date <= '%s')))",
						hotelID,hotelID,checkinDate,checkoutDate,checkinDate,checkoutDate,checkinDate,checkoutDate);
		
		ResultSet availableRoomsSet = Mysql.executeQuery(queryStatement);
		ArrayList<Rooms> availableRooms = new ArrayList<>();
		
		while (availableRoomsSet.next()) {
			availableRooms.add(new Rooms(availableRoomsSet.getInt(1), hotelID));
		}

		if(availableRooms.size()==0)
			return null;
		return availableRooms;

	}
	
	
	public static int queryPaymentForReceipts(int numberOfRoom, int hotelID, Date checkinDate, Date checkoutDate) throws SQLException {
		int price= Hotels.queryPriceByID(hotelID);
		int payment =(int) (price * numberOfRoom *(checkoutDate.getTime() - checkinDate.getTime())/86400000);	
		return payment;
	}
	
	public static int queryNumberOfAllRooms(int hotelID) throws SQLException {
		String queryStatement = "SELECT count(id) FROM room where hotel_id = " + hotelID;
		ResultSet numberOfRooms = Mysql.executeQuery(queryStatement);
		numberOfRooms.next();
		return numberOfRooms.getInt(1);
	}

	public static ArrayList<Rooms> queryAllRooms(int hotelID) throws SQLException {
		String queryStatement = "SELECT * FROM room where hotel_id = " + hotelID;
		ResultSet allRoomsSet = Mysql.executeQuery(queryStatement);
		
		ArrayList<Rooms> allRooms = new ArrayList<>();
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
		String insertStatement="INSERT INTO room VALUES("+room.getRoomID()+","+room.getHotelID()+")";
		Mysql.executeUpdate(insertStatement);
	}

	@Override
	public Object queryInstance(int pk) throws SQLException {
		return null;
	}
	
	@Override
	public void updateInstance(Object object) throws SQLException {
	}
	

	//--------------------------------------------------------------------------------------------------------------------------

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

}
