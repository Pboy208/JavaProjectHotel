package model.locations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.database.Mysql;

public class Districts {

	int districtID;
	String districtName;
	
	public Districts(int districtID,String districtName) {
		this.districtID=districtID;
		this.districtName=districtName;
	}
	// ------------------------------------------------------------------------------------------------------------------------

	public static ArrayList<Districts> queryDistrict(int provinceID) throws SQLException {
		ArrayList<Districts> districtList = new ArrayList<>();
		String queryStatement="SELECT * FROM district WHERE province_id = "+provinceID;
		ResultSet districtsSet = Mysql.executeQuery(queryStatement);
		while(districtsSet.next())
			districtList.add(new Districts(districtsSet.getInt(1), districtsSet.getString(2)));
		
		return districtList;
	}
	// ------------------------------------------------------------------------------------------------------------------------
	public int getDistrictID() {
		return districtID;
	}
	public void setDistrictID(int provinceID) {
		this.districtID = provinceID;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String provinceName) {
		this.districtName = provinceName;
	}
	
	

}
