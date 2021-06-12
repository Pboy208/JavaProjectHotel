package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.LoginController;
import model.users.HotelEmployees;
import model.users.Users;

public class HotelEmployeesDB implements DBInterface {

	public static HotelEmployees queryEmployeeInfo(int userID) throws SQLException {

		String queryStatement = "SELECT * FROM user,hotelmanager WHERE user.id = hotelmanager.user_id"
				+ "	AND user.id = " + userID;
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		if (tmp.next() == false) {
			return null;
		}
	
		ArrayList<Integer> hotelIDs = HotelsDB.queryHotelIDByManagerID(tmp.getInt("hotelmanager.id"));
		
		HotelEmployees user = new HotelEmployees(userID,tmp.getInt("hotelmanager.id"),tmp.getString("name"), tmp.getString("phone"),
				tmp.getString("email"),hotelIDs);
		return user;
	}

	public static HotelEmployees queryEmployeeInfoByHotelID(int hotelID) throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT manager_id FROM hotel WHERE id = " +hotelID;
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		int managerID = tmp.getInt(1);
	
		ArrayList<Integer> hotelIDs = HotelsDB.queryHotelIDByManagerID(managerID);
		
		String queryStatement2=String.format("SELET user.* FROM user JOIN hotelmanager ON (hotelmanager.id = %s)",managerID);
		ResultSet tmp2 = statement.executeQuery(queryStatement2);
		tmp2.next();
		HotelEmployees manager = new HotelEmployees(tmp2.getInt("id"),managerID,tmp2.getString("name"),tmp2.getString("phone"),tmp2.getString("email"),hotelIDs);
		return manager;
	}

	@Override
	public void insertInstance(Object object) throws SQLException {
		HotelEmployees user = (HotelEmployees)object;
		String insert = String.format("INSERT INTO user(name,phone,email,username,password) "
				+ "VALUES ('%s','%s','%s','%s','%s')", user.getName(),user.getPhoneNumber(),user.getEmail(),user.getUsername(),user.getPassword());
		
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(insert);	
		
		String query = "SELECT id FROM user WHERE phone='" + user.getPhoneNumber();
		ResultSet tmp = statement.executeQuery(query);
		tmp.next();
		int userID = tmp.getInt("id");
		
		String insertEmployeeIntoHotel= "INSERT INTO hotelmanager(user_id) VALUES ("+userID+")";
		statement.executeUpdate(insertEmployeeIntoHotel);
	}

	@Override
	public Object queryInstance(int pk) throws SQLException {
		// TODO Auto-generated method stub
		return null;
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
