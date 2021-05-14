package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.LoginController;
import user.Clients;
import user.User;

public class UsersDB {

	public static User queryClientInfo(int userID) throws SQLException {
		String queryStatement = "SELECT * FROM users where user_id = " + userID;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		return  new User(userID,tmp.getString("name"),tmp.getString("phone"),tmp.getString("email"));
	}
	
	public static int insertClients(String name, String phoneNumber, String email, String accountName, String password)
			throws SQLException {
		String insertInfo = "INSERT INTO users(name,phone,email) VALUES ('" + name + "','" + phoneNumber + "','"
				+ email + "')";
		
		String query = "SELECT user_id FROM users WHERE name = '" + name + "' AND phone='" + phoneNumber
				+ "' AND email='" + email + "'";
		
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(insertInfo);
		
		ResultSet tmp = statement.executeQuery(query);
		tmp.next();
		int userID = tmp.getInt("user_id");

		String updateAccount = "INSERT INTO accounts(user_id,username,password) VALUES (" + userID + ",'"
				+ accountName + "','" + password + "')";
		return statement.executeUpdate(updateAccount);
	}
	
	public static void updateClients(int userID, String name, String phone, String email, String password)
			throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String updateAccount = "UPDATE users Set name='" + name + "',phone='" + phone + "',email='" + email
				+ "' WHERE user_id = " + userID;
		statement.executeUpdate(updateAccount);
		String updateAccountPW = "UPDATE accounts Set password='" + password + "' WHERE user_id = " + userID;
		statement.executeUpdate(updateAccountPW);
		LoginController.setUser(name, phone, email, password);
	}
}
