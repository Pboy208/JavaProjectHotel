package model.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelQualityDB {
	public static float queryOverallScore(int hotelID) throws SQLException {
		String queryStatement = "SELECT overall_score FROM hotelquality where id = " + hotelID;
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if(!tmp.next()) {
			return -1;
		}
		float result = tmp.getFloat("overall_score");
		return result;
	}
}
