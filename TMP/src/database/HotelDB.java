package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import rooms.Hotel;

public class HotelDB {

	public static int checkExistName(String name) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM hotel where name ='" + name + "'";
		ResultSet count = statement.executeQuery(queryStatement);
		if (count.next() == false) 
			return 0;
		else 
			return 1;
	}

	public static int insertHotel(String name, String address) throws SQLException {
		if (checkExistName(name) == 1)
			return -1;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT max(hotel_id) FROM hotel";
		ResultSet count = statement.executeQuery(queryStatement);
		count.next();
		int newID = count.getInt(1) + 1;
		String insertStatement = "INSERT INTO hotel(hotel_id,name,address) VALUES (" + newID + ",'" + name + "','"
				+ address + "')";
		statement.executeUpdate(insertStatement);
		return newID;
	}

	public static ArrayList<Hotel> queryHotelInfo(int[] hotelIDs) throws SQLException {
		// query by hotelID
		String queryStatement = "SELECT * FROM hotel where hotel_id = '";
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ArrayList<Hotel> hotels = new ArrayList<>();
		for (int i : hotelIDs) {
			String fullQueryStatement = queryStatement + i + "'";
			ResultSet tmp = statement.executeQuery(fullQueryStatement);
			tmp.next();
			Float overallScore = HotelQualityDB.queryOverallScore(tmp.getInt(1));
			if (overallScore == -1)
				overallScore = 0f;
			Hotel tmpHotel = new Hotel(tmp.getInt(1), tmp.getString(2), tmp.getString(3), tmp.getInt(6), overallScore,
					tmp.getInt(10));
			hotels.add(tmpHotel);
		}
		return hotels;
	}

	public static Hotel queryHotelInfo(int hotelID) throws SQLException {
		String queryStatement = "SELECT * FROM hotel where hotel_id = " + hotelID;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		if (tmp.next() == false)
			return null;
		else {
			ExtensionsDB.queryExtensions(hotelID);
			Float overallScore = HotelQualityDB.queryOverallScore(tmp.getInt(1));
			if (overallScore == -1)
				overallScore = 0f;
			Hotel tmpHotel = new Hotel(tmp.getInt(1), tmp.getString(2), tmp.getString(3), tmp.getInt(6), overallScore,
					tmp.getInt(10));
			tmpHotel.setExtensions(ExtensionsDB.queryExtensions(hotelID));
			return tmpHotel;
		}
	}

	public static void updateHotelInfo(int hotelID, String hotelName, String hotelAddress, int star, int[] extensions,
			float price) throws SQLException {
		String updateStatement = "UPDATE hotel SET name = '" + hotelName + "',address='" + hotelAddress + "',star="
				+ star + "where hotel_id=" + hotelID;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(updateStatement);
		ExtensionsDB.updateExtensions(hotelID, extensions);
	}

}
