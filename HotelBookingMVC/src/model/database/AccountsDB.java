package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountsDB {

	public static int checkPassword(String accountName, String password) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet user_id = statement.executeQuery("SELECT user_id FROM accounts WHERE username = '" + accountName
				+ "' AND password = '" + password + "'");
		if (user_id.next()) // co tra ve
			return user_id.getInt("user_id");
		return -1;
	}

	public static int checkExistAccount(String accountName, String phone) throws SQLException {
		String queryStatement = "SELECT * FROM accounts JOIN users on users.user_id = accounts.user_id"
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

	public static void insertAccount(int userID, String accountName, String password) throws SQLException {
		String updateStatement = "INSERT INTO accounts values(" + userID + ",'" + accountName + "','" + password + "')";
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(updateStatement);
	}

}
