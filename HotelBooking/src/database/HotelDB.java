package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import rooms.Hotel;

public class HotelDB {

	public static int checkExistName(String name) throws SQLException {
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		String queryStatement = "SELECT * FROM hotel where name ='"+name+"'" ;
		ResultSet count = statement.executeQuery(queryStatement);
		if(count.next()==false) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	public static int insertHotel(String name,String address) throws SQLException {
		if(checkExistName(name)==1)
			return -1;
		
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		String queryStatement = "SELECT max(hotel_id) FROM hotel" ;
		ResultSet count = statement.executeQuery(queryStatement);
		count.next();
		int newID = count.getInt(1)+1;
		
		String insertStatement = "INSERT INTO hotel(hotel_id,name,address) VALUES ("+newID+",'"+name+"','"+address+"')" ;
		statement.executeUpdate(insertStatement);
		return newID;
	}
	
	public static ArrayList<Hotel> queryHotelInfo(int[] hotelIDs) throws SQLException {
		//query by hotelID
		String queryStatement = "SELECT * FROM hotel where hotel_id = '" ;
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		ArrayList<Hotel> hotels = new ArrayList<>();
		for(int i : hotelIDs) {
			String fullQueryStatement=queryStatement+i+"'";
			//System.out.println(fullQueryStatement);
			ResultSet tmp = statement.executeQuery(fullQueryStatement);
			
			tmp.next();
			
			Float overallScore = HotelQualityDB.queryOverallScore(tmp.getInt(1));
			if(overallScore==-1) {
				overallScore=0f;
			}
			Hotel tmpHotel= new Hotel(tmp.getInt(1),tmp.getString(2),tmp.getString(3),tmp.getInt(6),overallScore);
			hotels.add(tmpHotel);
		}
		return hotels;
	}
}
