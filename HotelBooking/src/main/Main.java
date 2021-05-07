//package main;
//
//import java.sql.SQLException;
//
//import rooms.Filter;
//import user.Clients;
//public class Main {
//
//	public static void main(String[] args) throws SQLException {
//		Clients user = Application.SignInClients("phuonghoang2k","123456789");
//		// bien user nay se tuong trung cho visitor hien tai cua he thong
//		
//		if(user==null) {
//			System.out.println("Wrong username or password, please try again");
//		}
//		else {
//			user.printInfo();
//		}
//		
//		//Recommend Rooms
//		System.out.println("Input your filter");
//		Filter filter = new Filter();
//		int a[]= {-1,1,0,0,1,1,0,1,1,0,0,0};
//		filter.setExtensions(a);
//		
//		Application.recommendRooms(filter);
//		
////		boolean result = Application.SignUp(2);
////		System.out.println(result);
////		
////		Clients user2 = Postgresql.queryClientInfo(Application.checkPassword("phamthao2k","123456789"));
////		user2.printInfo();
//		
//		user=Application.SignOut(user);
//
//	}
//}
