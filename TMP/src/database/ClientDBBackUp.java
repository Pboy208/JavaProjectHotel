package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.LoginController;
import user.Clients;

public class ClientDBBackUp {

	public static Clients queryClientInfo(int userID) throws SQLException {
		Clients user = new Clients(userID);
		String queryStatement = "SELECT * FROM clients where user_id = " + userID;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		user.setName(tmp.getString("name"));
		user.setEmail(tmp.getString("email"));
		user.setPhoneNumber(tmp.getString("phone"));
		return user;
	}

	public static int insertClients(String name, String phoneNumber, String email, String accountName, String password)
			throws SQLException {
		String insertInfo = "INSERT INTO clients(name,phone,email) VALUES ('" + name + "','" + phoneNumber + "','"
				+ email + "')";
		String query = "SELECT user_id FROM clients WHERE name = '" + name + "' AND phone='" + phoneNumber
				+ "' AND email='" + email + "'";
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(insertInfo);

		ResultSet tmp = statement.executeQuery(query);
		tmp.next();
		int userID = tmp.getInt("user_id");

		String updateAccount = "INSERT INTO accountclients(user_id,username,password) VALUES (" + userID + ",'"
				+ accountName + "','" + password + "')";
		return statement.executeUpdate(updateAccount);
	}

	public static int checkExistAccount(String accountName, String phone) throws SQLException {
		String queryStatement = "SELECT * FROM accountclients JOIN clients on clients.user_id = accountclients.user_id"
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

	public static void updateClients(int userID, String name, String phone, String email, String password)
			throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String updateAccount = "UPDATE clients Set name='" + name + "',phone='" + phone + "',email='" + email
				+ "' WHERE user_id = " + userID;
		statement.executeUpdate(updateAccount);
		String updateAccountPW = "UPDATE accountclients Set password='" + password + "' WHERE user_id = " + userID;
		statement.executeUpdate(updateAccountPW);
		LoginController.setUser(name, phone, email, password);
	}
}
