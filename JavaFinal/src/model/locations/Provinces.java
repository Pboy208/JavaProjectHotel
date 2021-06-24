package model.locations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.database.Mysql;

public class Provinces {

	int provinceID;
	String provinceName;
	
	public Provinces(int provinceID,String provinceName) {
		this.provinceID=provinceID;
		this.provinceName=provinceName;
	}
	
	// ------------------------------------------------------------------------------------------------------------------------

	public static ArrayList<Provinces> queryProvince() throws SQLException {
		ArrayList<Provinces> provincesList= new ArrayList<>();
		String queryStatement = "SELECT * FROM province";
		ResultSet provincesSet = Mysql.executeQuery(queryStatement);
		while(provincesSet.next())
			provincesList.add(new Provinces(provincesSet.getInt(1),provincesSet.getString(2)));

		return provincesList;
	}
	// ------------------------------------------------------------------------------------------------------------------------

	public int getProvinceID() {
		return provinceID;
	}
	public void setProvinceID(int provinceID) {
		this.provinceID = provinceID;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	

}
