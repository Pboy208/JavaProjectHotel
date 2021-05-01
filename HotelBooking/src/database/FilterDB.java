package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import rooms.Filter;
import rooms.Hotel;

public class FilterDB {

	public static ArrayList<Hotel> queryHotelsByFilter(Filter filter) throws SQLException {
		String values= null;
		String queryStatement = "SELECT hotelinfo.hotel_id FROM hotelinfo";
		ArrayList<Integer> hotel_IDs = new ArrayList<>();
		int flagExtensionEmpty=1;
		int flagDestinationEmpty=1;
		int flagHotelNameEmpty=1;
		
		//------------------------- Check whether client left HotelName/Destination empty
		if(filter.getHotelName()!=null || filter.getDestination()!=null) {
			if(filter.getHotelName()!=null)	
				flagHotelNameEmpty=0;
			if(filter.getDestination()!=null)
				flagDestinationEmpty=0;
			queryStatement = queryStatement.concat(" join hotel on hotel.hotel_id = hotelinfo.hotel_id"
												 + " join province on province.province_id = hotel.province_id");
		}
		

		//------------------------- Check whether client left Filter empty
		for(int i=0;i<12;i++)
		{
			if(filter.getExtensions()[i]==1) {
				flagExtensionEmpty=0;
			}
		}
		
		if(flagExtensionEmpty==0){//not empty
			values=valuesForQuery(filter);
			queryStatement = queryStatement.concat(" where " +values);
			if(flagDestinationEmpty==0) {
				queryStatement=queryStatement.concat(" and province.name = '" + filter.getDestination() + "'");
			}
			if(flagHotelNameEmpty == 0) {
				queryStatement=queryStatement.concat(" and hotel.name like '%" + filter.getHotelName() + "%'"
													+ " or hotel.name like '" + filter.getHotelName()+"%'"	
													+ " or hotel.name like '%" + filter.getHotelName() + "'"
													+ " or hotel.name like '"+ filter.getHotelName() + "'");
			}
			System.out.println("check1");
		}
		//------------------------- 
		else{	//Extension empty
			if(flagDestinationEmpty==0 || flagHotelNameEmpty == 0)
				queryStatement=queryStatement.concat(" where");
			if(flagDestinationEmpty==0) 
				queryStatement=queryStatement.concat(" province.name = '" + filter.getDestination() + "'");
			if(flagDestinationEmpty == 0 && flagHotelNameEmpty ==0)
				queryStatement=queryStatement.concat(" and hotel.name like '%" + filter.getHotelName() + "%'"
						+ " or hotel.name like '" + filter.getHotelName()+"%'"	
						+ " or hotel.name like '%" + filter.getHotelName() + "'"
						+ " or hotel.name like '"+ filter.getHotelName() + "'");
			else if(flagDestinationEmpty == 1 && flagHotelNameEmpty ==0)
				queryStatement=queryStatement.concat(" hotel.name like '%" + filter.getHotelName() + "%'"
						+ " or hotel.name like '" + filter.getHotelName()+"%'"	
						+ " or hotel.name like '%" + filter.getHotelName() + "'"
						+ " or hotel.name like '"+ filter.getHotelName() + "'");
			
		}
		
		//------------------------- 
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		System.out.println(queryStatement);
		ResultSet tmp = statement.executeQuery(queryStatement);
		while(tmp.next()) {
			hotel_IDs.add(tmp.getInt("hotel_id"));
		}
		int size = hotel_IDs.size();
		int[] array = new int[size];
		
		for(int i=0;i<size;i++) {
			array[i]=hotel_IDs.get(i);
		}
		
		ArrayList<Hotel> hotels= HotelDB.queryHotelInfo(array);
		System.out.println("Filter File:"+hotels.size());
		if(hotels.size()==0)
		{
			System.out.println("array of hotel = NULL/ There is no hotel meets this filter");
			return null;
		}
		
		return hotels;
		
	}
	
	
	private static String valuesForQuery(Filter filter) {
		String values="";
		int extensions[] = filter.getExtensions();
		int length = extensions.length;
		String library[]=filter.getExtensionsLibrary();
		for(int i=0;i<length;i++) {
			if(extensions[i]==1 ) {
				values=values.concat(library[i]);
				//values=values.concat(" = '1' AND "); // attributes dang text for testing
				values=values.concat(" = 1 AND "); //sua khi ma attributes dang int
			}
		}
		values=values.substring(0,values.length()-5);
		System.out.println("Values:"+values);
		return values;
	}


}