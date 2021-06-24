package model.rooms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import model.database.DBInterface;
import model.database.Mysql;

public class Hotels implements DBInterface{
	private String name;
	private String address;
	private int star;
	private int hotelID;
	private int streetID;
	private int price;
	private int managerID;
	private float rating;
	private int[] extensions = new int[12];

	private SimpleStringProperty priceProperty;
	
	public Hotels(int hotel_ID, String name, String address, int star_number,int minPrice, float rating) {
		setHotelID(hotel_ID);
		setAddress(address);
		setName(name);
		setStar(star_number);
		setRating(rating);
		setPrice(minPrice);
		
		this.priceProperty = new SimpleStringProperty(String.format("%,d", minPrice));
		
	}

	public Hotels(String name,String address,int streetID) {
		setStreetID(streetID);
		setAddress(address);
		setName(name);
	}

	public Hotels() {
		
	}
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------

	public int addHotelProcedure() throws SQLException {
		String queryStatement=String.format("CALL InsertHotel('%s','%s',%d,%d,@OUTPUTPAREMETER)",name,address,streetID,managerID);
		System.out.println(String.format("CALL InsertHotel('%s','%s',%d,%d,OUTPUTPAREMETER)",name,address,streetID,managerID));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		
		return tmp.getInt(1);
	}
	
	@Override
	public void insertInstance() throws SQLException {
		String updateStatment=String.format("INSERT INTO hotel(name,addres,star,price,manager_id,street_id) "
				+ "VALUES('%s','%s',int,int,int,int)",getName(),getAddress(),getStar(),getPrice(),getManagerID(),getStreetID());
		Mysql.executeUpdate(updateStatment);
		
		String queryStatement = String.format("SELECT id FROM hotel WHERE name = '%s' AND manager_id = %d",getName(),getManagerID());
		ResultSet result = Mysql.executeQuery(queryStatement);
		result.next();
		int hotelID = result.getInt("id");
		
		HotelInfo hotelInfo = new HotelInfo(hotelID);
		hotelInfo.insertInstance();
	}

	@Override
	public void queryInstance(int pk) throws SQLException {
		String queryStatement = String.format("SELECT * FROM hotel WHERE id = %d", pk);
		ResultSet result = Mysql.executeQuery(queryStatement);
		result.next();
		
		HotelInfo hotelInfo = new HotelInfo(pk);
		hotelInfo.queryInstance(pk);
		
		setHotelID(pk);
		setName(result.getString("name"));
		setAddress(result.getString("address"));
		setStar(result.getInt("star"));
		setPrice(result.getInt("price"));
		setManagerID(result.getInt("manager_id"));
		setStreetID(result.getInt("street_id"));
		setRating(Hotels.queryOverallScore(pk));
		setExtensions(hotelInfo.getExtensions());
	}
	
	@Override
	public void updateInstance() throws SQLException {
		HotelInfo hotelInfo = new HotelInfo(hotelID, extensions);

		String updateStatement=String.format("CALL ManagerUpdateDetail(%d,%d,%d,'%s')",
				hotelID,star,price,hotelInfo.extensionsToStringForUpdate());
		System.out.println(String.format("CALL ManagerUpdateDetail(%d,%d,%d,'%s')",
				hotelID,star,price,hotelInfo.extensionsToStringForUpdate()));
		Mysql.executeUpdate(updateStatement);
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------
	private static float queryOverallScore(int hotelID) throws SQLException {
		String queryStatement = "SELECT overall_score FROM hotelquality where id = " + hotelID;
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if(!tmp.next()) {
			return 0f;
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
		
		for(Integer hotelID:hotelIDs) {
			Hotels aHotel = new Hotels();
			aHotel.queryInstance(hotelID);
			hotels.add(aHotel);
		}
	
		return hotels;
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------

	
	public int getPrice() {
		return price;
	}
	public void setPrice(int minPrice) {
		this.price = minPrice;
		this.priceProperty = new SimpleStringProperty(String.format("%,d", minPrice));
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

	public String getPriceProperty() {
		return priceProperty.get();
	}
	public void setPriceProperty(SimpleStringProperty minPriceProperty) {
		this.priceProperty = minPriceProperty;
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

	public int getStreetID() {
		return streetID;
	}

	public void setStreetID(int streetID2) {
		this.streetID = streetID2;
	}
	
	public int getManagerID() {
		return managerID;
	}
	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

}
