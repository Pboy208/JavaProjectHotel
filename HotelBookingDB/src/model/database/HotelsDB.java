package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.LoginController;
import model.rooms.Hotels;
import model.users.HotelEmployees;

public class HotelsDB implements DBInterface {

	public static int checkExistName(String name) throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM hotel WHERE name ='" + name + "'";
		ResultSet count = statement.executeQuery(queryStatement);
		if (count.next() == false)
			return 0;
		else
			return 1;
	}

	public static int insertHotel(String name, String address,int streetID) throws SQLException {
		if (checkExistName(name) == 1)
			return -1;
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		int managerID =((HotelEmployees) LoginController.getUser()).getUserID();
		String insertStatement = String.format("INSERT INTO hotel(name,address,star,street_id,manager_id) "
				+ "VALUES ('%s','%s',0,%d,%d)",name,address,0,streetID,managerID);
		statement.executeUpdate(insertStatement);
		
		String queryStatement = String.format("SELECT id FROM hotel "
				+ "WHERE name = '%s',manager_id = %d",name,managerID);
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		return tmp.getInt(1);
	}

	public static ArrayList<Hotels> queryHotelInfo(int[] hotelIDs) throws SQLException {
		// query by hotelID
		String queryStatement = "SELECT * FROM hotel WHERE id = ";
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		
		ArrayList<Hotels> hotels = new ArrayList<>();
		
		for (int i : hotelIDs) {
			String fullQueryStatement = queryStatement + i ;
			ResultSet tmp = statement.executeQuery(fullQueryStatement);
			tmp.next();
			
			Float overallScore = HotelQualityDB.queryOverallScore(tmp.getInt(1));
			
			if (overallScore == -1)
				overallScore = 0f;
			
			Hotels tmpHotel = new Hotels(tmp.getInt(1), tmp.getString(2), tmp.getString(3), tmp.getInt(6), tmp.getInt(7), overallScore);

			hotels.add(tmpHotel);
		}
		return hotels;
	}
	
	public static ArrayList<Integer> queryHotelIDByManagerID(int managerID) throws SQLException{
		ArrayList<Integer> hotelIDs = new ArrayList<>();
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT id FROM hotel WHERE manager_id = " + managerID;
		ResultSet tmp = statement.executeQuery(queryStatement);
		while(tmp.next()) {
			hotelIDs.add(tmp.getInt(1));
		}
		return hotelIDs;
	}
	
	public static ArrayList<Hotels> queryHotelsByManagerID(int managerID) throws SQLException{
		ArrayList<Hotels> hotels = new ArrayList<>();
		
		ArrayList<Integer> hotelIDs = queryHotelIDByManagerID(managerID);
		
		for(Integer id:hotelIDs) {
			Hotels aHotel = (Hotels) (new HotelsDB()).queryInstance(id);
			hotels.add(aHotel);
		}
	
		return hotels;
	}
	
	public static int queryPriceByID(int hotelID) throws SQLException {
		String queryStatement = "SELECT price FROM hotel WHERE id = " + hotelID;
		System.out.println(queryStatement);
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		return tmp.getInt(1);
	}
	
	@Override
	public void insertInstance(Object object) throws SQLException {
		return;
	}

	@Override
	public Object queryInstance(int hotelID) throws SQLException {
		String queryStatement = "SELECT * FROM hotel WHERE id = " + hotelID;
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		if (tmp.next() == false)
			return null;
		else {
			ExtensionsDB.queryExtensions(hotelID);
			Float overallScore = HotelQualityDB.queryOverallScore(hotelID);
			if (overallScore == -1)
				overallScore = 0f;
			Hotels tmpHotel = new Hotels(tmp.getInt(1), tmp.getString(2), tmp.getString(3), tmp.getInt(6),tmp.getInt(7), overallScore);
			tmpHotel.setExtensions(ExtensionsDB.queryExtensions(hotelID));
			return tmpHotel;
		}
	}
	
	@Override
	public void updateInstance(Object object) throws SQLException {
		Hotels hotel = (Hotels)object; 
		String updateStatement = "UPDATE hotel SET name = '" + hotel.getName() + "',address='" + hotel.getAddress() + "',star="
				+ hotel.getStar() +"price="+hotel.getMinPrice()+ "WHERE id=" + hotel.getHotelID();
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(updateStatement);
		ExtensionsDB.updateExtensions(hotel.getHotelID(), hotel.getExtensions());
		
	}

}
