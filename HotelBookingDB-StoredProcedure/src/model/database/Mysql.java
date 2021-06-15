package model.database;

import java.sql.*;

import controller.LoginController;
import model.rooms.Hotels;
import model.users.HotelEmployees;
import model.users.Users;

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
	
	public static void cancelReceiptUser(int receiptID) throws SQLException {
		String queryStatement = String.format("CALL CancelReceiptUser(%d,%d)",receiptID,LoginController.getUser().getUserID());
		Mysql.executeQuery(queryStatement);
	}
	
	public static int signUpUserProcedure(Users user) throws SQLException {
		String queryStatement = String.format("CALL SignUpUser('%s','%s','%s','%s','%s')",
				user.getName(),user.getPhoneNumber(),user.getEmail(),user.getUsername(),user.getPassword());
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		return tmp.getInt(1);
	}
	
	public static int signUpHotelManagerProcedure(HotelEmployees manager,Hotels hotel) throws SQLException {
		String queryStatement = String.format("CALL SignUpManager('%s','%s','%s','%s','%s','%s','%s',%d)",
				manager.getName(),manager.getPhoneNumber(),manager.getEmail(),manager.getUsername(),manager.getPassword(),
				hotel.getName(),hotel.getAddress(),hotel.getStreetID());
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		return tmp.getInt(1);
	}
	
	public static Users loginProcedure(String username,String password) throws SQLException {
		String queryStatement=String.format("CALL Login('%s','%s')",username,password);
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		Users user = new Users();
		
		if(!tmp.next())
			return null;
		
		try {
			user = new HotelEmployees(tmp.getInt("user.id"), tmp.getInt("hotelmanager.id"), tmp.getString("name"),
					tmp.getString("phone"), tmp.getString("email"),tmp.getString("username"), tmp.getString("password"));
		} catch (Exception e) {
			user = new Users(tmp.getInt("user.id"), tmp.getString("name"), tmp.getString("phone"), tmp.getString("email"),
					tmp.getString("username"), tmp.getString("password"));
			e.printStackTrace();
		}
		
		return user;
	}
}
