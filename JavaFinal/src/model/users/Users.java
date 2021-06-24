package model.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.database.DBInterface;
import model.database.Mysql;

public class Users implements DBInterface {

	private int userID;
	private String name;
	private String email;
	private String phone;
	private String username;
	private String password;

	public Users(int userID, String name, String phoneNumber, String email) {
		this.name = name;
		this.email = email;
		this.phone = phoneNumber;
		this.userID = userID;
	}

	public Users(int userID, String name, String phoneNumber, String email, String username, String password) {
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.phone = phoneNumber;
		this.username = username;
		this.password = password;
	}

	public Users(String name, String phoneNumber, String email, String username, String password) {
		this.name = name;
		this.email = email;
		this.phone = phoneNumber;
		this.username = username;
		this.password = password;
	}

	public Users() {

	}
	// -------------------------------------------------------------------------------------------------------------------
	public  int signUpUserProcedure() throws SQLException {
		String queryStatement = String.format("CALL SignUpUser('%s','%s','%s','%s','%s')",
				getName(),getPhone(),getEmail(),getUsername(),getPassword());
		System.out.println(String.format("CALL SignUpUser('%s','%s','%s','%s','%s')",
				getName(),getPhone(),getEmail(),getUsername(),getPassword()));
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
	
	
	@Override
	public void insertInstance() throws SQLException {
			String insertStatement =String.format("INSERT INTO users(name,phone,email) "
					+ "VALUES ('%s','%s','%s','%s','%s'",this.name,this.phone,this.email,this.username,this.password);
			Mysql.executeUpdate(insertStatement);
	}

	@Override
	public void queryInstance(int receiptID) throws SQLException {
		String queryStatement=String.format("CALL ManagerViewUserDetail(%d)",receiptID);
		System.out.println(String.format("CALL ManagerViewUserDetail(%d)",receiptID));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		setName(tmp.getString("name"));
		setPhone(tmp.getString("phone"));
		setEmail(tmp.getString("email"));
	}

	@Override
	public void updateInstance() throws SQLException {
		String updateStatement=String.format("CALL UpdateUserData(%d,'%s','%s','%s','%s')",
				getUserID(),getName(),getPhone(),getEmail(),getPassword());
		System.out.println(String.format("CALL UpdateUserData(%d,'%s','%s','%s','%s')",
				getUserID(),getName(),getPhone(),getEmail(),getPassword()));
		Mysql.executeQuery(updateStatement);
		
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------

	public int checkPassword() throws SQLException {
		String queryStatement=String.format("SELECT id FROM user WHERE username = '%s' "
				+ "AND password = '%s'", username,password);
		ResultSet user_id = Mysql.executeQuery(queryStatement);
		if (user_id.next()) 
			return user_id.getInt("id");
		return -1;
	}
	// -------------------------------------------------------------------------------------------------------------------
	public void printInfo() {
		System.out.println(this.getName() + "/" + this.getPhone() + "/" + this.getEmail());
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phoneNumber) {
		this.phone = phoneNumber;
	}


	// --------------------------------------------------------------------------------------------------------------------------------

}
