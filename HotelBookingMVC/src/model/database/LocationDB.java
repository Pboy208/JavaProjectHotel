package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.locations.Districts;
import model.locations.Provinces;
import model.locations.Street;

public class LocationDB {

	public static ArrayList<Provinces> queryProvince() throws SQLException {
		ArrayList<Provinces> provincesList= new ArrayList<>();
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet provincesSet = statement.executeQuery("SELECT * FROM province");
		while(provincesSet.next())
		{	
			provincesList.add(new Provinces(provincesSet.getInt(1),provincesSet.getString(2)));
		}
		return provincesList;
	}
	
	public static ArrayList<Districts> queryDistrict(int provinceID) throws SQLException {
		ArrayList<Districts> districtList = new ArrayList<>();
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet districtsSet = statement.executeQuery("SELECT * FROM district WHERE province_id = "+provinceID);
		System.out.println("SELECT name FROM district WHERE province_id = "+provinceID);
		while(districtsSet.next())
		{	
			districtList.add(new Districts(districtsSet.getInt(1), districtsSet.getString(2)));
		}
		return districtList;
	}
	
	public static ArrayList<Street> queryStreet(int districtID) throws SQLException {
		ArrayList<Street> streetList = new ArrayList<>();
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet streetsSet = statement.executeQuery("SELECT * FROM street WHERE district_id = "+districtID);
		System.out.println("SELECT name FROM street WHERE district_id = "+districtID);
		while(streetsSet.next())
		{	
			streetList.add(new Street(streetsSet.getInt(1),streetsSet.getString(2)));
		}
		return streetList;
	}
}
