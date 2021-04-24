package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
	public static void main(String[] args) throws SQLException {
		String username="postgres";
		String password="005868";
		String query="SELECT * FROM countries WHERE country_name = 'Viet Nam' AND region_id = '10001'";
		String jdbcUrl="jdbc:postgresql://localhost:5432/HR_lab01";
		try {
			Connection connection= DriverManager.getConnection(jdbcUrl,username,password);
			System.out.println("Connection extablished");
			Statement statement= connection.createStatement();
			ResultSet abc =statement.executeQuery(query);
			
			boolean tmp=abc.next();
			System.out.println(tmp);
			
			
//			while(abc.next()) {
//				String countryID = abc.getString("country_id");
////				String name = abc.getString("country_name");
////				String regionID = abc.getString("region_id");
//				System.out.println(countryID);
//			}
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to DB");
		}
	}

}
