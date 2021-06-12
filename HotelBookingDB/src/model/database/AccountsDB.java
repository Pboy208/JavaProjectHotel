package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountsDB {

	public static int checkPassword(String accountName, String password) throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet user_id = statement.executeQuery("SELECT id FROM user WHERE username = '" + accountName
				+ "' AND password = '" + password + "'");
		if (user_id.next()) // co tra ve
			return user_id.getInt("id");
		return -1;
	}

	public static int checkExistAccount(String accountName, String phone) throws SQLException {
		String queryStatement = "SELECT * FROM user WHERE username = '" + accountName + "' OR phone = '" + phone + "'";
		Connection connection = Mysql.makeConnection();
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
		// if accountname trùng -> 1,phone trùng ->2
	}
	


}
