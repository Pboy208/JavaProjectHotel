package model.users;

import model.database.DBInterface;

public abstract class Visitors implements DBInterface {

	private String email;
	
	public Visitors(String email) {
		setEmail(email);
	}
	
	public Visitors() {
		
	}
	
	// -----------------------------------------------------------------

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
