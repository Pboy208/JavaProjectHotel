package database;

import user.Clients;

public class AccountClientsDB {

	public static int createClient(String name,String phoneNumber,String email,String username,String password) {
		int user_id = 0;
		Clients user = new Clients(name,phoneNumber,email);
		
		
		
		return user_id;
	}
	
	private static int createClientAccount(String username,String password,String user_id) {
		
		return 0;
	}
}
