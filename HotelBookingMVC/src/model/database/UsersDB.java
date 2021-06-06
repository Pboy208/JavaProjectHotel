package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.LoginController;
import model.users.Users;

public class UsersDB implements DBInterface{
	@Override
	public void insertInstance(Object object) throws SQLException {
		Users user = (Users)object;
		String insertInfo = "INSERT INTO users(name,phone,email) VALUES ('" + user.getName() + "','" + user.getPhoneNumber() + "','" + user.getEmail()
				+ "')";

		String query = "SELECT user_id FROM users WHERE name = '" + user.getName() + "' AND phone='" + user.getPhoneNumber()
				+ "' AND email='" + user.getEmail() + "'";

		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(insertInfo);

		ResultSet tmp = statement.executeQuery(query);
		tmp.next();
		int userID = tmp.getInt("user_id");
		
		String updateAccount = "INSERT INTO accounts(user_id,username,password) VALUES (" + userID + ",'" + user.getUsername()
				+ "','" + user.getPassword() + "')";
		statement.executeUpdate(updateAccount);
		
	}
	
	@Override
	public Object queryInstance(int userID) throws SQLException {
		String queryStatement = "SELECT * FROM users where user_id = " + userID;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		if (tmp.next() == false) {
			System.out.println("Result set error, userID/queryStatement: " + userID + "/" + queryStatement);
			return null;
		}
		return new Users(userID, tmp.getString("name"), tmp.getString("phone"), tmp.getString("email"));
		
	}

	@Override
	public void updateInstance(Object object) throws SQLException {
		Users user = (Users)object;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String updateAccount = "UPDATE users Set name='" + user.getName() + "',phone='" + user.getPhoneNumber() + "',email='" + user.getEmail()
				+ "' WHERE user_id = " + user.getUserID();
		statement.executeUpdate(updateAccount);
		String updateAccountPW = "UPDATE accounts Set password='" + user.getPassword() + "' WHERE user_id = " + user.getUserID();
		statement.executeUpdate(updateAccountPW);
		LoginController.setUser(user);		
	}

}
