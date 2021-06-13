package model.database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.locations.Districts;
import model.locations.Provinces;
import model.locations.Street;

public class LocationDB {

	public static ArrayList<Provinces> queryProvince() throws SQLException {
		ArrayList<Provinces> provincesList= new ArrayList<>();
		String queryStatement = "SELECT * FROM province";
		ResultSet provincesSet = Mysql.executeQuery(queryStatement);
		while(provincesSet.next())
			provincesList.add(new Provinces(provincesSet.getInt(1),provincesSet.getString(2)));

		return provincesList;
	}
	
	public static ArrayList<Districts> queryDistrict(int provinceID) throws SQLException {
		ArrayList<Districts> districtList = new ArrayList<>();
		String queryStatement="SELECT * FROM district WHERE province_id = "+provinceID;
		ResultSet districtsSet = Mysql.executeQuery(queryStatement);
		while(districtsSet.next())
			districtList.add(new Districts(districtsSet.getInt(1), districtsSet.getString(2)));
		
		return districtList;
	}
	
	public static ArrayList<Street> queryStreet(int districtID) throws SQLException {
		ArrayList<Street> streetList = new ArrayList<>();
		String queryStatement="SELECT * FROM street WHERE district_id = "+districtID;
		ResultSet streetsSet = Mysql.executeQuery(queryStatement);
		
		while(streetsSet.next())
			streetList.add(new Street(streetsSet.getInt(1),streetsSet.getString(2)));

		return streetList;
	}
	
	public static int queryStreetIDByName(String street) throws SQLException {
		String queryStatement="SELECT id FROM street WHERE name = '"+street+"'";
		ResultSet streetsSet = Mysql.executeQuery(queryStatement);
		if(!streetsSet.next()) 
			return -1;
		return streetsSet.getInt(1);
	}
	
	public static int queryProvinceIDByStreetID(int streetID) throws SQLException {
		String queryStatement=("SELECT province.id FROM province "
				+ "JOIN district ON(province.id = district.province_id")
				+ "JOIN street ON (street.id=district.street_id)"
				+ "WHERE street.id = "+streetID;
		ResultSet provinceSet = Mysql.executeQuery(queryStatement);
		provinceSet.next();
		return provinceSet.getInt(1);
	}
}
