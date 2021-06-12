package model.users;

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
	public Users(int userID,String name, String phoneNumber, String email,String username,String password) {
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.password = password;
	}
	public Users(String name, String phoneNumber, String email,String username,String password) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.password = password;
	}
	public Users(String username,String password) {
		this.username = username;
		this.password = password;
	}
	public Users() {
		
	}
	// -------------------------------------------------------------
	public void printInfo() {
		System.out.println(this.getName() + "/" + this.getPhoneNumber() + "/" + this.getEmail());
	}

	// -------------------------------------------------------------
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
