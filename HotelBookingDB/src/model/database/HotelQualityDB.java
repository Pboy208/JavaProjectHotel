package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HotelQualityDB {

	public static float queryOverallScore(int hotelID) throws SQLException {
		String queryStatement = "SELECT overall_score FROM hotelquality where id = " + hotelID;
		// System.out.println(queryStatement);
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		if(!tmp.next()) {
			return -1;
		}
		float result = tmp.getFloat("overall_score");
		return result;
	}
}
