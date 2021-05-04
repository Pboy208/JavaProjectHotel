package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.LoginController;
import user.Clients;

public class ClientDB {
	
	public static Clients queryClientInfo(int user_id) throws SQLException {
		Clients user = new Clients(user_id);
		String queryStatement = "SELECT * FROM clients where user_id = "+user_id;
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		user.setName(tmp.getString("name"));
		user.setEmail(tmp.getString("email"));
		user.setPhoneNumber(tmp.getString("phone"));		
		return user;
	}
	
	public static int insertClients(String user_name,String phoneNumber,String email,String username,String password) throws SQLException {
		String insertInfo="INSERT INTO clients(name,phone,email) VALUES ('"+user_name+"','"+phoneNumber+"','"+email+"')";
		String query = "SELECT user_id FROM clients WHERE name = '"+user_name+"' AND phone='"+phoneNumber+"' AND email='"+email+"'";
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		statement.executeUpdate(insertInfo);
		//truy nguoc tim` id
		ResultSet tmp = statement.executeQuery(query);
		tmp.next();
		int id=tmp.getInt("user_id");
		//
		String updateAccount="INSERT INTO accountclients(user_id,username,password) VALUES ("+id+",'"+username+"','"+password+"')";
		return statement.executeUpdate(updateAccount);
	}
	
	public static int checkExistAccount(String username,String phone) throws SQLException {
		String queryStatement = "SELECT * FROM accountclients JOIN clients on clients.user_id = accountclients.user_id"
							+ " WHERE username = '"+username+"' OR phone = '"+ phone +"'" ;
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		if(tmp.next()==false)
			return 0;
		else {
			if(tmp.getString("username").equals(username))
				return 1;
			else 
				return 2;
		}
		//if already exist -> return 1
	}

	public static void updateClients(int user_id,String name, String phone,String email,String password) throws SQLException {
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		String updateAccount="UPDATE clients Set name='"+ name+"',phone='"+phone+"',email='"+email+"' WHERE user_id = "+user_id;
		statement.executeUpdate(updateAccount);
		LoginController.setUser(name, phone, email, password);
	}
}
