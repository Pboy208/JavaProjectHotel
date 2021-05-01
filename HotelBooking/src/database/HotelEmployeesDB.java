package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import user.HotelEmployees;

public class HotelEmployeesDB {

	public static HotelEmployees queryEmployeeInfo(int user_id) throws SQLException {
		HotelEmployees user = new HotelEmployees(user_id);
		String queryStatement = "SELECT * FROM employees where user_id = "+user_id;
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		user.setName(tmp.getString("user_name"));
		user.setEmail(tmp.getString("email"));
		user.setPhoneNumber(tmp.getString("phone"));	
		user.setHotelID(tmp.getInt("hotel_id"));
		return user;
	}
	
	
	public static int insertHotelEmployees(int hotel_ID,String user_name,String phoneNumber,String email,String username,String password) throws SQLException {
		String insertInfo="INSERT INTO employees(user_name,phone,email,hotel_ID) VALUES ('"+user_name+"','"+phoneNumber+"','"+email+"',"+hotel_ID+")";
		String query = "SELECT user_id FROM employees WHERE user_name = '"+user_name+"' AND phone='"+phoneNumber+"' AND email='"+email+"'";
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		statement.executeUpdate(insertInfo);
		
		ResultSet tmp = statement.executeQuery(query);
		tmp.next();
		int id=tmp.getInt("user_id");
		
		String updateAccount="INSERT INTO account_employees(user_id,username,password) VALUES ("+id+",'"+username+"','"+password+"')";
		return statement.executeUpdate(updateAccount);
	}
}
