package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import user.Clients;

public class ClientDB {
	
	public static Clients queryClientInfo(int user_id) throws SQLException {
		Clients user = new Clients(user_id);
		String queryStatement = "SELECT * FROM clients where user_id = "+user_id;
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		user.setName(tmp.getString("user_name"));
		user.setEmail(tmp.getString("email"));
		user.setPhoneNumber(tmp.getString("phone"));		
		return user;
	}
	
	public static int insertClients(String user_name,String phoneNumber,String email,String username,String password) throws SQLException {
		String insertInfo="INSERT INTO clients(user_name,phone,email) VALUES ('"+user_name+"','"+phoneNumber+"','"+email+"')";
		String query = "SELECT user_id FROM clients WHERE user_name = '"+user_name+"' AND phone='"+phoneNumber+"' AND email='"+email+"'";
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		statement.executeUpdate(insertInfo);
		
		ResultSet tmp = statement.executeQuery(query);
		tmp.next();
		int id=tmp.getInt("user_id");
		
		String updateAccount="INSERT INTO account(user_id,username,password) VALUES ("+id+",'"+username+"','"+password+"')";
		return statement.executeUpdate(updateAccount);
	}
}
