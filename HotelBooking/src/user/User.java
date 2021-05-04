package user;


public class User {
	
	private int userID;
	private String name;
	private String email;
	private String phoneNumber;
	private String username;
	private String password;
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
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
	public User(String name,String phoneNumber,String email) {
		this.name=name;
		this.email=email;
		this.phoneNumber=phoneNumber;
	}
	public User(int userID) {
		this.userID=userID;
	}
	public User() {
		
	}
	public int getId() {
		return userID;
	}

	public void setId(int id) {
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

	//--------------------------------------------------------------------------------------------------------------------------------
	public void checkReservedRoom() {
		
	}
	public void cancelRoom() {
		
	}
	public void viewAvailableRoom() {
		
	}
	public void checkReservingStatus() {
		
	}
	public void getUserInfo() {
		
	}
	public void printInfo() {
		
	}
	
	

}
