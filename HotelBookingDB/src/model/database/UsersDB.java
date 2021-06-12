package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.LoginController;
import model.users.HotelEmployees;
import model.users.Users;

public class UsersDB implements DBInterface{
	
	@Override
	public void insertInstance(Object object) throws SQLException {
		Users user = (Users)object;
		String insert = String.format("INSERT INTO user(name,phone,email,username,password) "
				+ "VALUES ('%s','%s','%s','%s','%s')", user.getName(),user.getPhoneNumber(),user.getEmail(),user.getUsername(),user.getPassword());
		
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(insert);		
	}
	
	@Override
	public Object queryInstance(int userID) throws SQLException {
		String queryStatement = "SELECT * FROM user where id = " + userID;
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		if (tmp.next() == false) {
			System.out.println("Result set error, userID/queryStatement: " + userID + "/" + queryStatement);
			return null;
		}
		return new Users(userID, tmp.getString("name"), tmp.getString("phone"),tmp.getString("email"),tmp.getString("username"),tmp.getString("password"));
		
	}
	
	@Override
	public void updateInstance(Object object) throws SQLException {
		Users user = (Users)object;
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String updateAccount = String.format("UPDATE user Set name= '%s', phone='%s',email='%s',password='%s'",
				user.getName(),user.getPhoneNumber(),user.getEmail(),user.getPassword());

		statement.executeUpdate(updateAccount);
		LoginController.setUser(user);		
	}
	
	
}
