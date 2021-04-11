package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.management.Query;

public class Postgresql {

	private String username="postgres";
	private String password="005868";
	private String query="SELECT * from countries";
	private String jdbcUrl="jdbc:postgresql://localhost:5432/HR_lab01";
	private static Connection connection;
	public Postgresql() {
		try {
			connection= DriverManager.getConnection(jdbcUrl,username,password);
			System.out.println("Connection extablished");
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to DB");
		}
	}
	
	public static void query(String query) throws SQLException {
		Statement statement= connection.createStatement();
		ResultSet rows =statement.executeQuery(query);
		while(rows.next()) {
			String countryID = rows.getString("country_id");
			String name = rows.getString("country_name");
			String regionID = rows.getString("region_id");
			System.out.println(countryID+"/"+name+"/"+regionID);
		}
	}
	
}
