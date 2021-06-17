package model.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.LoginController;
import model.database.Mysql;

public class Users {

	private int userID;
	private String name;
	private String email;
	private String phoneNumber;
	private String username;
	private String password;

	public Users(int userID, String name, String phoneNumber, String email) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.userID = userID;
	}

	public Users(String name, String phoneNumber, String email) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public Users(int userID, String name, String phoneNumber, String email, String username, String password) {
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.password = password;
	}

	public Users(String name, String phoneNumber, String email, String username, String password) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.password = password;
	}

	public Users(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Users() {

	}
	// -------------------------------------------------------------------------------------------------------------------
	public static int signUpUserProcedure(Users user) throws SQLException {
		String queryStatement = String.format("CALL SignUpUser('%s','%s','%s','%s','%s')",
				user.getName(),user.getPhoneNumber(),user.getEmail(),user.getUsername(),user.getPassword());
		System.out.println(String.format("CALL SignUpUser('%s','%s','%s','%s','%s')",
				user.getName(),user.getPhoneNumber(),user.getEmail(),user.getUsername(),user.getPassword()));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		return tmp.getInt(1);
	}
	
	public static Users loginProcedure(String username,String password) throws SQLException {
		String queryStatement=String.format("CALL Login('%s','%s')",username,password);
		System.out.println(String.format("CALL Login('%s','%s')",username,password));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		Users user = new Users();
		tmp.next();
		
		if(tmp.getInt(1)==-1)
			return null;
		
		try {
			user = new HotelManager(tmp.getInt("user.id"), tmp.getInt("hotelmanager.id"), tmp.getString("name"),
					tmp.getString("phone"), tmp.getString("email"),tmp.getString("username"), tmp.getString("password"));
		} catch (Exception e) {
			user = new Users(tmp.getInt("user.id"), tmp.getString("name"), tmp.getString("phone"), tmp.getString("email"),
					tmp.getString("username"), tmp.getString("password"));
			e.printStackTrace();
		}
		return user;
	}
	
	public static Users managerViewUserDetail(int receiptID) throws SQLException {
		String queryStatement=String.format("CALL ManagerViewUserDetail(%d)",receiptID);
		System.out.println(String.format("CALL ManagerViewUserDetail(%d)",receiptID));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if(!tmp.next())
			return null;
		return new Users(tmp.getString("name"),tmp.getString("phone"),tmp.getString("email"));
	}
	
	public static void updateUserInfo(Users user) throws SQLException {
		String updateStatement=String.format("CALL UpdateUserData(%d,'%s','%s','%s','%s')",
				LoginController.getUser().getUserID(),user.getName(),user.getPhoneNumber(),user.getEmail(),user.getPassword());
		System.out.println(String.format("CALL UpdateUserData(%d,'%s','%s','%s','%s')",
				LoginController.getUser().getUserID(),user.getName(),user.getPhoneNumber(),user.getEmail(),user.getPassword()));
		Mysql.executeQuery(updateStatement);
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------

	public static int checkPassword(String accountName, String password) throws SQLException {
		String queryStatement=String.format("SELECT id FROM user WHERE username = '%s' "
				+ "AND password = '%s'", accountName,password);
		ResultSet user_id = Mysql.executeQuery(queryStatement);
		if (user_id.next()) 
			return user_id.getInt("id");
		return -1;
	}
	// -------------------------------------------------------------------------------------------------------------------
	public void printInfo() {
		System.out.println(this.getName() + "/" + this.getPhoneNumber() + "/" + this.getEmail());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int id) {
		this.userID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	// --------------------------------------------------------------------------------------------------------------------------------

}
