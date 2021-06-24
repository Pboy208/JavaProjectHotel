package model.rooms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.database.Mysql;

public class Filters {
	private int star;
	private int hotelID;
	private String location;
	private String destination;
	private String hotelName;
	private HotelInfo hotelInfo;
	
	public Filters() {

	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------

	public ArrayList<Hotels> filterSearching() throws SQLException {
		String queryStatement=String.format("CALL FilteredSearch('%s','%s',%d,'%s')",
				getDestination(),getHotelName(),getStar(),hotelInfo.extensionsToStringForQuery());
		System.out.println(String.format("CALL FilteredSearch('%s','%s',%d,'%s')",
				getDestination(),getHotelName(),getStar(),hotelInfo.extensionsToStringForQuery()));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		
		ArrayList<Integer> hotel_IDs = new ArrayList<>();
		
		while (tmp.next()) {
			hotel_IDs.add(tmp.getInt(1));
		}
		
		if (hotel_IDs.size() == 0) {
			System.out.println("array of hotel = NULL/ There is no hotel meets this filter");
			return null;
		}

		ArrayList<Hotels> hotels = Hotels.queryHotelInfo(hotel_IDs);
		return hotels;
	}
	
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void setExtensions(int extensions[]) {
		this.hotelInfo = new HotelInfo();
		hotelInfo.setExtensions(extensions);
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	
	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	public HotelInfo getHotelInfo() {
		return hotelInfo;
	}

	public void setHotelInfo(HotelInfo hotelInfo) {
		this.hotelInfo = hotelInfo;
	}

}
