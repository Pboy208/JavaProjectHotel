package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.rooms.Hotels;

public class HotelsDB implements DBInterface {

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
		String insertStatement = "INSERT INTO hotel(hotel_id,name,address,star) VALUES (" + newID + ",'" + name + "','"
				+ address + "',0)";
		statement.executeUpdate(insertStatement);
		return newID;
	}

	public static ArrayList<Hotels> queryHotelInfo(int[] hotelIDs) throws SQLException {
		// query by hotelID
		String queryStatement = "SELECT hotel.*,min(price) FROM hotel join rooms on hotel.hotel_id=rooms.hotel_id" + 
				" where hotel.hotel_id = ";
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ArrayList<Hotels> hotels = new ArrayList<>();
		for (int i : hotelIDs) {
			String fullQueryStatement = queryStatement + i + " group by hotel.hotel_id";
			ResultSet tmp = statement.executeQuery(fullQueryStatement);
			tmp.next();
			Float overallScore = HotelQualityDB.queryOverallScore(tmp.getInt(1));
			if (overallScore == -1)
				overallScore = 0f;
			Hotels tmpHotel = new Hotels(tmp.getInt(1), tmp.getString(2), tmp.getString(3), tmp.getInt(6), overallScore,
					tmp.getInt(10));
			tmpHotel.setMinPrice(tmp.getInt(11));
			hotels.add(tmpHotel);
		}
		return hotels;
	}

	@Override
	public void insertInstance(Object object) throws SQLException {
		return;
	}

	@Override
	public Object queryInstance(int hotelID) throws SQLException {
		String queryStatement = "SELECT * FROM hotel where hotel_id = " + hotelID;
		System.out.println(queryStatement);
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		if (tmp.next() == false)
			return null;
		else {
			ExtensionsDB.queryExtensions(hotelID);
			Float overallScore = HotelQualityDB.queryOverallScore(hotelID);
			if (overallScore == -1)
				overallScore = 0f;
			Hotels tmpHotel = new Hotels(tmp.getInt(1), tmp.getString(2), tmp.getString(3), tmp.getInt(6), overallScore,
					tmp.getInt(10));
			tmpHotel.setExtensions(ExtensionsDB.queryExtensions(hotelID));
			return tmpHotel;
		}
	}

	@Override
	public void updateInstance(Object object) throws SQLException {
		Hotels hotel = (Hotels)object; 
		String updateStatement = "UPDATE hotel SET name = '" + hotel.getName() + "',address='" + hotel.getAddress() + "',star="
				+ hotel.getStar() + "where hotel_id=" + hotel.getHotelID();
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(updateStatement);
		ExtensionsDB.updateExtensions(hotel.getHotelID(), hotel.getExtensions());
		
	}

}
