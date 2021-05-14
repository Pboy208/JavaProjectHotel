package user;

public class Clients extends User {

	public Clients(int userID,String name, String phone, String email) {
		super(userID,name, phone, email);
	}

	public Clients(int userID) {
		super(userID);
	}

	public Clients() {
		super();
	}

	// -------------------------------------------------------------------------------------------------------
	public void printInfo() {
		System.out.println(this.getName() + "/" + this.getPhoneNumber() + "/" + this.getEmail());
	}
}
