package model.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountsDB {

	public static int checkPassword(String accountName, String password) throws SQLException {
		String queryStatement=String.format("SELECT id FROM user WHERE username = '%s' "
				+ "AND password = '%s'", accountName,password);
		ResultSet user_id = Mysql.executeQuery(queryStatement);
		if (user_id.next()) 
			return user_id.getInt("id");
		return -1;
	}

	public static int checkExistAccount(String accountName, String phone) throws SQLException {
		String queryStatement =String.format("SELECT * FROM user WHERE username = '%s' "
				+ "OR phone = '%s'", accountName,phone);
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if (tmp.next() == false)
			return 0;
		else {
			if (tmp.getString("username").equals(accountName))
				return 1; //exist user name
			else
				return 2; //exist phone
		}
	}
}
