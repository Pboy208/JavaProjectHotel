package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LocationDB {

	public static ArrayList<String> queryProvince() throws SQLException {
		ArrayList<String> provinceNames = new ArrayList<>();
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet provinceNameSet = statement.executeQuery("SELECT name FROM province");
		while(provinceNameSet.next())
		{	
			provinceNames.add(provinceNameSet.getString(1));
		}
		return provinceNames;
	}
	
}
