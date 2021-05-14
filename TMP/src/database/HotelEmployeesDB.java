package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.LoginController;
import user.HotelEmployees;

public class HotelEmployeesDB {

	public static HotelEmployees queryEmployeeInfo(int userID) throws SQLException {

		String queryStatement = "SELECT * FROM hotel_employees JOIN users ON (users.user_id = hotel_employees.user_id)"
				+ "WHERE users.user_id = " + userID;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		HotelEmployees user = new HotelEmployees(userID,
										tmp.getString("name"),
										tmp.getString("phone"),
										tmp.getString("email"),
										tmp.getInt("hotel_id"),
										tmp.getInt("rank"));
		return user;
	}

	public static void insertHotelEmployees(int hotelID, String name, String phone, String email, String accountName,
			String password) throws SQLException {
		String insertInfo = "INSERT INTO users(name,phone,email,hotel_ID) VALUES ('" + name + "','" + phone + "','"
				+ email + "'," + hotelID + ")";
		String query = "SELECT user_id FROM users WHERE name = '" + name + "' AND phone='" + phone + "' AND email='"
				+ email + "'";
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(insertInfo);

		ResultSet tmp = statement.executeQuery(query);
		tmp.next();
		int userID = tmp.getInt("user_id");
		AccountsDB.insertAccount(userID, accountName, password);
	}


	public static void updateHotelEmployees(int user_id, String name, String phone, String email, String password)
			throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String updateAccount = "UPDATE users Set name='" + name + "',phone='" + phone + "',email='" + email
				+ "' WHERE user_id = " + user_id;
		statement.executeUpdate(updateAccount);
		String updateAccountPW = "UPDATE accounts Set password='" + password + "' WHERE user_id = " + user_id;
		statement.executeUpdate(updateAccountPW);
		LoginController.setUser(name, phone, email, password);
	}

	public static HotelEmployees queryEmployeeInfoByHotelID(int hotelID) throws SQLException {

		String queryStatement = "SELECT * FROM users where user_id = (SELECT user_id FROM hotel_employees"
				+ " where rank = 1 and hotel_id = " + hotelID +")";
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		return new HotelEmployees(tmp.getInt(1),tmp.getString(2),tmp.getString(3),tmp.getString(4),hotelID,1);

	}
}
