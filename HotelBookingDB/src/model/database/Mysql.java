package model.database;

import java.sql.*;

public class Mysql {

	private static java.sql.Connection connection;

	public static java.sql.Connection makeConnection() {
		if (connection == null) {
			 try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			String username = "root";
			String password = "";
			String jdbcUrl = "jdbc:mysql://localhost:3306/latestdb?useUnicode=true&characterEncoding=utf-8";
			try {
				connection = DriverManager.getConnection(jdbcUrl, username, password);
				System.out.println("Connection extablished");
			} catch (SQLException e) {
				System.out.println("Error in connecting to DB");
			}
		}
		return connection;
	}
	
	public static ResultSet executeQuery(String queryStatement) throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		return statement.executeQuery(queryStatement);
	}
	
	public static void executeUpdate(String updateStatement) throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(updateStatement);
	}
}
