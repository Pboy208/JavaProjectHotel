package model.database;

import java.sql.*;

public class Postgre {

	private static java.sql.Connection connection;

	public static java.sql.Connection makeConnection() {
		if (connection == null) {
			String username = "postgres";
			String password = "005868";
			String jdbcUrl = "jdbc:postgresql://localhost:5432/bk";
//			String password = "Nguyentuandung2901";
//			String jdbcUrl = "jdbc:postgresql://localhost:5432/tmp";
			try {
				connection = DriverManager.getConnection(jdbcUrl, username, password);
				System.out.println("Connection extablished");
			} catch (SQLException e) {
				System.out.println("Error in connecting to DB");
			}
		}
		return connection;
	}
}
