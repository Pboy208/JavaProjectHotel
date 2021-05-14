package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HotelQualityDB {

	public static float queryOverallScore(int hotelID) throws SQLException {
		String queryStatement = "SELECT overall_score FROM hotelquality where hotel_id = " + hotelID;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		float result = tmp.getFloat("overall_score");
		return result;
	}
}
