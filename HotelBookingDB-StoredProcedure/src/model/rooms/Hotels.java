package model.rooms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import model.database.Mysql;

public class Hotels {
	private int star;
	private int hotelID;
	private float rating;
	private String address;
	private String provinceID;
	private String districtID;
	private int streetID;
	private String name;
	private int[] extensions;
	private int minPrice;

	private SimpleStringProperty minPriceProperty;
	
	public Hotels(int hotel_ID, String name, String address, int star_number,int minPrice, float rating) {
		setHotelID(hotel_ID);
		setAddress(address);
		setName(name);
		setStar(star_number);
		setRating(rating);
		setMinPrice(minPrice);
		
		this.minPriceProperty = new SimpleStringProperty(String.format("%,d", minPrice));
		
	}
	public Hotels(int hotel_ID, String name, String address, int star_number,int[] extensions,int price) {
		setHotelID(hotel_ID);
		setAddress(address);
		setName(name);
		setStar(star_number);
		setExtensions(extensions);
		setMinPrice(price);
	}
	public Hotels(String name,String address,int streetID) {
		setStreetID(streetID);
		setAddress(address);
		setName(name);
	}
	public Hotels() {
		
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------
	private static float queryOverallScore(int hotelID) throws SQLException {
		String queryStatement = "SELECT overall_score FROM hotelquality where id = " + hotelID;
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if(!tmp.next()) {
			return -1;
		}
		float result = tmp.getFloat("overall_score");
		return result;
	}
	
	public static ArrayList<Hotels> queryHotelInfo(ArrayList<Integer> hotelIDs) throws SQLException {

		String queryStatement = "SELECT * FROM hotel WHERE id = ";
		
		ArrayList<Hotels> hotels = new ArrayList<>();
		
		for (int i : hotelIDs) {
			String fullQueryStatement = queryStatement + i ;
			ResultSet tmp = Mysql.executeQuery(fullQueryStatement);
			tmp.next();
			
			Float overallScore = queryOverallScore(tmp.getInt(1));
			
			if (overallScore == -1)
				overallScore = 0f;
			
			Hotels tmpHotel = new Hotels(tmp.getInt(1), tmp.getString(2), tmp.getString(3), tmp.getInt(4), tmp.getInt(5), overallScore);

			hotels.add(tmpHotel);
		}
		return hotels;
	}
	
	public static ArrayList<Integer> queryHotelIDByManagerID(int managerID) throws SQLException{
		ArrayList<Integer> hotelIDs = new ArrayList<>();
		
		String queryStatement = "SELECT id FROM hotel WHERE manager_id = " + managerID;
		
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		
		while(tmp.next()) {
			hotelIDs.add(tmp.getInt(1));
		}
		return hotelIDs;
	}
	
	public static ArrayList<Hotels> queryHotelsByManagerID(int managerID) throws SQLException {
		ArrayList<Hotels> hotels = new ArrayList<>();
		
		ArrayList<Integer> hotelIDs = queryHotelIDByManagerID(managerID);
		
		for(Integer id:hotelIDs) {
			Hotels aHotel = queryHotelByID(id);
			hotels.add(aHotel);
		}
	
		return hotels;
	}
	
	public static int queryPriceByID(int hotelID) throws SQLException {
		String queryStatement = "SELECT price FROM hotel WHERE id = " + hotelID;
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		return tmp.getInt(1);
	}

	public static Hotels queryHotelByID(int hotelID) throws SQLException {
		String queryStatement = "SELECT * FROM hotel WHERE id = " + hotelID;
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		
		if (tmp.next() == false)
			return null;
		else {
			Filters.queryExtensions(hotelID);
			Float overallScore = queryOverallScore(hotelID);
			if (overallScore == -1)
				overallScore = 0f;
			Hotels tmpHotel = new Hotels(tmp.getInt(1), tmp.getString(2), tmp.getString(3), tmp.getInt(4),tmp.getInt(5), overallScore);
			tmpHotel.setExtensions(Filters.queryExtensions(hotelID));
			return tmpHotel;
		}
	}

	
	// ----------------------------------------------------------------------------------------------------------------------------------------------

	
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
		this.minPriceProperty = new SimpleStringProperty(String.format("%,d", minPrice));
	}

	public void printInfo() {
		System.out.println(name + "/" + address);
	}

	public int[] getExtensions() {
		return extensions;
	}

	public void setExtensions(int[] extensions) {
		this.extensions = extensions;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getMinPriceProperty() {
		return minPriceProperty.get();
	}
	public void setMinPriceProperty(SimpleStringProperty minPriceProperty) {
		this.minPriceProperty = minPriceProperty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	public String getProvinceID() {
		return provinceID;
	}

	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}

	public String getDistrictID() {
		return districtID;
	}

	public void setDistrictID(String district_id) {
		this.districtID = district_id;
	}

	public int getStreetID() {
		return streetID;
	}

	public void setStreetID(int streetID2) {
		this.streetID = streetID2;
	}

}
