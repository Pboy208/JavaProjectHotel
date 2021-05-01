package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckPasswordDB {

	public static int checkPassword(String username,String password) throws SQLException {
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		ResultSet user_id =statement.executeQuery("SELECT user_id FROM account WHERE username = '"+username+"' AND password = '"+password+"'");
		if(user_id.next()) //co tra ve
			return user_id.getInt("user_id");
		return -1;
	}

}
