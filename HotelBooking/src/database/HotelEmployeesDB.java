package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.LoginController;
import user.HotelEmployees;

public class HotelEmployeesDB {

	public static HotelEmployees queryEmployeeInfo(int userID) throws SQLException {
		HotelEmployees user = new HotelEmployees(userID);
		String queryStatement = "SELECT * FROM employees where user_id = " + userID;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		user.setName(tmp.getString("name"));
		user.setEmail(tmp.getString("email"));
		user.setPhoneNumber(tmp.getString("phone"));
		user.setHotelID(tmp.getInt("hotel_id"));
		return user;
	}

	public static int insertHotelEmployees(int hotelID, String name, String phone, String email, String accountName,
			String password) throws SQLException {
		String insertInfo = "INSERT INTO employees(name,phone,email,hotel_ID) VALUES ('" + name + "','" + phone + "','"
				+ email + "'," + hotelID + ")";
		String query = "SELECT user_id FROM employees WHERE name = '" + name + "' AND phone='" + phone + "' AND email='"
				+ email + "'";
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(insertInfo);

		ResultSet tmp = statement.executeQuery(query);
		tmp.next();
		int id = tmp.getInt("user_id");

		String updateAccount = "INSERT INTO accounthotels(user_id,username,password) VALUES (" + id + ",'" + accountName
				+ "','" + password + "')";
		return statement.executeUpdate(updateAccount);
	}

	public static int checkExistAccount(String accountName, String phone) throws SQLException {
		String queryStatement = "SELECT * FROM accounthotels JOIN employees on employees.user_id = accounthotels.user_id"
				+ " WHERE username = '" + accountName + "' OR phone = '" + phone + "'";
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);

		if (tmp.next() == false)
			return 0;
		else {
			if (tmp.getString("username").equals(accountName))
				return 1;
			else
				return 2;
		}
		// if already exist -> return 1
	}

	public static void updateHotelEmployees(int user_id, String name, String phone, String email, String password)
			throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String updateAccount = "UPDATE employees Set name='" + name + "',phone='" + phone + "',email='" + email
				+ "' WHERE user_id = " + user_id;
		statement.executeUpdate(updateAccount);
		String updateAccountPW = "UPDATE accounthotels Set password='" + password + "' WHERE user_id = " + user_id;
		statement.executeUpdate(updateAccountPW);
		LoginController.setUser(name, phone, email, password);
	}
}
