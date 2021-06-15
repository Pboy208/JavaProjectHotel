package model.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.database.DBInterface;
import model.database.Mysql;
import model.rooms.Hotels;

public class HotelEmployees extends Users implements DBInterface {

	private ArrayList<Integer> hotelID;
	private int managerID;

	public HotelEmployees(int userID, int managerID, String name, String phone, String email, String username,
			String password, ArrayList<Integer> hotelID) {
		super(userID, name, phone, email);
		setHotelID(hotelID);
		setManagerID(managerID);
		setUsername(username);
		setPassword(password);

	}
	
	public HotelEmployees(int userID, int managerID, String name, String phone, String email,String username,
			String password) {
		super(userID, name, phone, email);
		setManagerID(managerID);
		setUsername(username);
		setPassword(password);
	}
	
	public HotelEmployees(int userID, int managerID, String name, String phone, String email,
			ArrayList<Integer> hotelID) {
		super(userID, name, phone, email);
		setHotelID(hotelID);
		setManagerID(managerID);
	}
	
	public HotelEmployees(int userID, int managerID, String name, String phone, String email) {
		super(userID, name, phone, email);
		setManagerID(managerID);
	}
	
	public HotelEmployees(int userID, String name, String phone, String email) {
		super(userID, name, phone, email);
	}

	public HotelEmployees(int userID, String name, String phone, String email, String password) {
		super(userID, name, phone, email);
		setPassword(password);
	}

	public HotelEmployees() {
		super();
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	public static HotelEmployees queryEmployeeInfoByHotelID(int hotelID) throws SQLException {
		String queryStatement = String.format(
				"SELECT user.*,hotelmanager.id FROM user,hotelmanager "
				+ " WHERE hotelmanager.id = (SELECT manager_id FROM hotel WHERE id = %s)"
				+ " AND hotelmanager.user_id = user.id",
				hotelID);
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();

		ArrayList<Integer> hotelIDs = Hotels.queryHotelIDByManagerID(tmp.getInt(7));

		HotelEmployees manager = new HotelEmployees(tmp.getInt("id"), tmp.getInt(7), tmp.getString("name"),
				tmp.getString("phone"), tmp.getString("email"), hotelIDs);
		return manager;
	}

	public static int queryManagerIDByPhone(String phone) throws SQLException {

		String queryStatement = String.format(
				"SELECT hotelmanager.id FROM user JOIN hotelmanager ON (user.id=hotelmanager.user_id) WHERE user.phone = '%s'",
				phone);
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if (tmp.next() == false) 
			return -1;
	
		return tmp.getInt(1);
	}

	@Override
	public void insertInstance(Object object) throws SQLException {
		
		HotelEmployees user = (HotelEmployees) object;

		String updateStatement = String.format(
				"INSERT INTO user(name,phone,email,username,password) " + "VALUES ('%s','%s','%s','%s','%s')",
				user.getName(), user.getPhoneNumber(), user.getEmail(), user.getUsername(), user.getPassword());
		Mysql.executeUpdate(updateStatement);

		String queryStatement = "SELECT id FROM user WHERE phone='" + user.getPhoneNumber() + "'";
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		int userID = tmp.getInt("id");

		String insertEmployeeIntoHotel = "INSERT INTO hotelmanager(user_id) VALUES (" + userID + ")";
		Mysql.executeUpdate(insertEmployeeIntoHotel);
	}

	@Override
	public Object queryInstance(int userID) throws SQLException {
		String queryStatement = "SELECT * FROM user,hotelmanager WHERE user.id = hotelmanager.user_id"
				+ "	AND user.id = " + userID;
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if (tmp.next() == false)
			return null;

		ArrayList<Integer> hotelIDs = Hotels.queryHotelIDByManagerID(tmp.getInt("hotelmanager.id"));

		HotelEmployees user = new HotelEmployees(userID, tmp.getInt("hotelmanager.id"), tmp.getString("name"),
				tmp.getString("phone"), tmp.getString("email"), hotelIDs);
		return user;
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	public void printInfo() {
		System.out.println(this.getName() + "/" + this.getPhoneNumber() + "/" + this.getEmail() + "/" + this.hotelID);
	}

	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public ArrayList<Integer> getHotelID() {
		return hotelID;
	}

	public void setHotelID(ArrayList<Integer> hotelID) {
		this.hotelID = hotelID;
	}
}
