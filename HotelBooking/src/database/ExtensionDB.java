package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import rooms.Filter;
import rooms.Hotel;

public class ExtensionDB {
	//keep this file
	
//	public static ArrayList<Hotel> queryHotelsByExtension(Filter filter) throws SQLException {
//		String values= valuesForQuery(filter);
//		ArrayList<Integer> hotel_IDs = new ArrayList<>();
//		
//		//------------------------- Check whether client let filter empty
//		int flag=0;
//		for(int i=0;i<12;i++)
//		{
//			if(filter.getExtensions()[i]==1) {
//				flag=1;
//			}
//		}
//		String queryStatement=null;
//		if(flag==1)
//			queryStatement = "SELECT hotel_id FROM hotelinfo where " +values;
//		else 
//			queryStatement = "SELECT hotel_id FROM hotelinfo";
//		//-------------------------
//		
//		
//		Connection connection= Postgre.makeConnection();
//		Statement statement= connection.createStatement();
//		System.out.println(queryStatement);
//		ResultSet tmp = statement.executeQuery(queryStatement);
//		while(tmp.next()) {
//			hotel_IDs.add(tmp.getInt("hotel_id"));
//		}
//		int size = hotel_IDs.size();
//		int[] array = new int[size];
//		
//		for(int i=0;i<size;i++) {
//			array[i]=hotel_IDs.get(i);
//		}
//		
//		ArrayList<Hotel> hotels= HotelDB.queryHotelInfo(array);
//		System.out.println(hotels.size());
//		if(hotels.size()==0)
//		{
//			System.out.println("array of hotel = NULL/ There is no hotel meets this filter");
//			return null;
//		}
//		return hotels;
//	}
//	
//	
//	private static String valuesForQuery(Filter filter) {
//		String values="";
//		int extensions[] = filter.getExtensions();
//		int length = extensions.length;
//		String library[]=filter.getExtensionsLibrary();
//		for(int i=0;i<length;i++) {
//			if(extensions[i]==1 ) {
//				values=values.concat(library[i]);
//				//values=values.concat(" = '1' AND "); // attributes dang text for testing
//				values=values.concat(" = 1 AND "); //sua khi ma attributes dang int
//			}
//		}
//		values=values.substring(0,values.length()-5);
//		return values;
//	}
}
