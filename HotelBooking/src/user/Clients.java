package user;

public class Clients extends User {

	public Clients(String name, String phone, String email) {
		super(name, phone, email);
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
