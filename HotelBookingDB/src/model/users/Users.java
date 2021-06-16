package model.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import controller.LoginController;
import model.database.DBInterface;
import model.database.Mysql;
import sun.security.jgss.LoginConfigImpl;

public class Users implements DBInterface {

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

	@Override
	public void insertInstance(Object object) throws SQLException {
		Users user = (Users) object;
		String updateStatement = String.format(
				"INSERT INTO user(name,phone,email,username,password) " + "VALUES ('%s','%s','%s','%s','%s')",
				user.getName(), user.getPhoneNumber(), user.getEmail(), user.getUsername(), user.getPassword());
		Mysql.executeUpdate(updateStatement);
	}

	@Override
	public Object queryInstance(int userID) throws SQLException {
		String queryStatement = "SELECT * FROM user where id = " + userID;
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if (tmp.next() == false) {
			return null;
		}
		return new Users(userID, tmp.getString("name"), tmp.getString("phone"), tmp.getString("email"),
				tmp.getString("username"), tmp.getString("password"));
	}

	@Override
	public void updateInstance(Object object) throws SQLException {
		Users user = (Users) object;
		String updateStatement = String.format("UPDATE user Set name= '%s', phone='%s',email='%s',password='%s' WHERE id = %d",
				user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword(),LoginController.getUser().getUserID());
		Mysql.executeUpdate(updateStatement);
		LoginController.setUser(user);
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
