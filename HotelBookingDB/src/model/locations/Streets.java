package model.locations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.database.Mysql;

public class Streets {

	int streetID;
	String streetName;
	
	public Streets(int streetID,String streetName) {
		this.streetID=streetID;
		this.streetName=streetName;
	}
	// ------------------------------------------------------------------------------------------------------------------------

	public static ArrayList<Streets> queryStreet(int districtID) throws SQLException {
		ArrayList<Streets> streetList = new ArrayList<>();
		String queryStatement="SELECT * FROM street WHERE district_id = "+districtID;
		ResultSet streetsSet = Mysql.executeQuery(queryStatement);
		
		while(streetsSet.next())
			streetList.add(new Streets(streetsSet.getInt(1),streetsSet.getString(2)));

		return streetList;
	}
	// ------------------------------------------------------------------------------------------------------------------------

	public int getStreetID() {
		return streetID;
	}
	public void setStreetID(int provinceID) {
		this.streetID = provinceID;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String provinceName) {
		this.streetName = provinceName;
	}
	
	

}
