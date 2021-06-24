package model.users;

import java.sql.SQLException;

import model.database.Mysql;

public class UnsignedUser extends Visitors{

	public UnsignedUser(String email) {
		super(email);
	}
	
	public UnsignedUser() {
		
	}
	
	// -----------------------------------------------------------------

	@Override
	public void insertInstance() throws SQLException {
		String insertStatement = String.format("INSERT INTO unsigneduser(email) values('%s')", getEmail());
		Mysql.executeUpdate(insertStatement);
	}

	@Override
	public void queryInstance(int pk) throws SQLException {
		return;
	}

	@Override
	public void updateInstance() throws SQLException {
		return;
	}

}
