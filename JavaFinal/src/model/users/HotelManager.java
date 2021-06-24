package model.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.database.Mysql;
import model.rooms.Hotels;

public class HotelManager extends Users {

	private ArrayList<Integer> hotelID;
	private int managerID;

	public HotelManager(int userID, int managerID, String name, String phone, String email,String username,
			String password) {
		super(userID, name, phone, email);
		setManagerID(managerID);
		setUsername(username);
		setPassword(password);
	}
	
	public HotelManager() {
		super();
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	
	public int signUpHotelManagerProcedure(Hotels hotel) throws SQLException {
		String queryStatement = String.format("CALL SignUpManager('%s','%s','%s','%s','%s','%s','%s',%d)",
				getName(),getPhone(),getEmail(),getUsername(),getPassword(),
				hotel.getName(),hotel.getAddress(),hotel.getStreetID());
		System.out.println(String.format("CALL SignUpManager('%s','%s','%s','%s','%s','%s','%s',%d)",
				getName(),getPhone(),getEmail(),getUsername(),getPassword(),
				hotel.getName(),hotel.getAddress(),hotel.getStreetID()));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		return tmp.getInt(1);
	}
	
	@Override
	public void queryInstance(int receiptID) throws SQLException {
		String queryStatement=String.format("CALL UserViewManagerDetail(%d)",receiptID);
		System.out.println(String.format("CALL UserViewManagerDetail(%d)",receiptID));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		setName(tmp.getString("name"));
		setPhone(tmp.getString("phone"));
		setEmail(tmp.getString("email"));
	}
	

	// ------------------------------------------------------------------------------------------------------------------------------
	public void printInfo() {
		System.out.println(this.getName() + "/" + this.getPhone() + "/" + this.getEmail() + "/" + this.hotelID);
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
