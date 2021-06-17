package model.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.database.Mysql;
import model.rooms.Hotels;

public class HotelManager extends Users {

	private ArrayList<Integer> hotelID;
	private int managerID;

	public HotelManager(int userID, int managerID, String name, String phone, String email, String username,
			String password, ArrayList<Integer> hotelID) {
		super(userID, name, phone, email);
		setHotelID(hotelID);
		setManagerID(managerID);
		setUsername(username);
		setPassword(password);

	}
	
	public HotelManager(int userID, int managerID, String name, String phone, String email,String username,
			String password) {
		super(userID, name, phone, email);
		setManagerID(managerID);
		setUsername(username);
		setPassword(password);
	}
	
	public HotelManager(int userID, int managerID, String name, String phone, String email,
			ArrayList<Integer> hotelID) {
		super(userID, name, phone, email);
		setHotelID(hotelID);
		setManagerID(managerID);
	}
	
	public HotelManager(int userID, int managerID, String name, String phone, String email) {
		super(userID, name, phone, email);
		setManagerID(managerID);
	}
	
	public HotelManager(int userID, String name, String phone, String email) {
		super(userID, name, phone, email);
	}

	public HotelManager(int userID, String name, String phone, String email, String password) {
		super(userID, name, phone, email);
		setPassword(password);
	}
	
	public HotelManager(String name,String phone,String email) {
		super(name,phone,email);
	}
	
	public HotelManager() {
		super();
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	public static int signUpHotelManagerProcedure(HotelManager manager,Hotels hotel) throws SQLException {
		String queryStatement = String.format("CALL SignUpManager('%s','%s','%s','%s','%s','%s','%s',%d)",
				manager.getName(),manager.getPhoneNumber(),manager.getEmail(),manager.getUsername(),manager.getPassword(),
				hotel.getName(),hotel.getAddress(),hotel.getStreetID());
		System.out.println(String.format("CALL SignUpManager('%s','%s','%s','%s','%s','%s','%s',%d)",
				manager.getName(),manager.getPhoneNumber(),manager.getEmail(),manager.getUsername(),manager.getPassword(),
				hotel.getName(),hotel.getAddress(),hotel.getStreetID()));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		return tmp.getInt(1);
	}
	
	public static HotelManager userViewManagerDetail(int receiptID) throws SQLException {
		String queryStatement=String.format("CALL UserViewManagerDetail(%d)",receiptID);
		System.out.println(String.format("CALL UserViewManagerDetail(%d)",receiptID));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if(!tmp.next())
			return null;
		return new HotelManager(tmp.getString("name"),tmp.getString("phone"), tmp.getString("email"));

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
