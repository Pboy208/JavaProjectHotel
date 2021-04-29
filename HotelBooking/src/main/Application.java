package main;

import java.sql.SQLException;
import java.util.ArrayList;

import database.Postgresql;
import rooms.Filter;
import rooms.Hotel;
import user.Clients;
import user.HotelEmployees;

public class Application {

	public static int CheckPassword(String username, String password) throws SQLException {
		// checking account in DB
		int user_id = Postgresql.queryCheckPassword(username, password);
		return user_id;
	}
	
	public static Clients SignInClients(String username,String password) throws SQLException {
		int user_id = CheckPassword(username, password);
		if(user_id==-1) {
			System.out.println("Wrong username or password in SignInClients, please try again");
			return null;
		}
		Clients user = Postgresql.queryClientInfo(user_id);
		return user;
	}
	
	public static HotelEmployees SignInHotel(String username,String password) throws SQLException {
		int user_id = CheckPassword(username, password);
		if(user_id==-1) {
			System.out.println("Wrong username or password in SignInHotel, please try again");
			return null;
		}
		HotelEmployees user = Postgresql.queryEmployeeInfo(user_id);
		return user;
	}
	
	public static boolean SignUp(int type) throws SQLException {
//		String user_name = null;
//		String phoneNumber = null;
//		String email = null;
//		String password = null;
//		String username = null;
		int hotelID = 1;
		String user_name = "Nguyen Tuan Dung";
		String phoneNumber = "0912345678";
		String email = "tuandung@gmail.com";
		String password = "123456789";
		String username = "tuandung2k";
		
//		String user_name = "Pham Phuong Thao";
//		String phoneNumber = "0969929273";
//		String email = "phamthaohaha@gmail.com";
//		String password = "123456789";
//		String username = "phamthao2k";
		
		
		
		if(type==1) {
			Postgresql.insertClients(user_name,phoneNumber,email,username,password);
		}
		else if(type==2) {
			Postgresql.insertHotelEmployees(hotelID, user_name, phoneNumber, email, username, password);
		}
		else {
			System.out.println("Wrong type of user!");
		}
		return false;
	}
	
	public static void recommendRooms(Filter filter) throws SQLException {
		// khi ma attributes trong db dang text,dang int phai sua
		//Tao chuoi values de query trong postgresql
		String values="";
		int extensions[] = filter.getExtensions();
		int length = extensions.length;
		String library[]=filter.getExtensionsLibrary();
		for(int i=0;i<length;i++) {
			if(extensions[i]==1 ) {
				values=values.concat(library[i]);
				//values=values.concat(" = '1' AND "); // attributes dang text for testing
				values=values.concat(" = 1 AND "); //sua khi ma attributes dang int
			}
		}
		values=values.substring(0,values.length()-5);
		//
		int hotel_IDs[]=Postgresql.queryExtension(values);

		ArrayList<Hotel> hotels= Postgresql.queryHotelInfo(hotel_IDs);
		if(hotels==null)
			System.out.println("array of hotel = NULL/ There is no hotel meets this filter");
		for(Hotel i : hotels) {
			i.prinInfo();
		}
		
	}
	
	public static Clients SignOut(Clients user) {
		return null;
	}
	
	public static HotelEmployees SignOut(HotelEmployees user) {
		return null;
	}
	
}
