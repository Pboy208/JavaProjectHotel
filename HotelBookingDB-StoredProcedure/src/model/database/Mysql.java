package model.database;

import java.sql.*;
import java.util.ArrayList;

import controller.HostController;
import controller.LoginController;
import model.rooms.Filters;
import model.rooms.Hotels;
import model.users.HotelEmployees;
import model.users.Users;

public class Mysql {

	private static java.sql.Connection connection;

	public static java.sql.Connection makeConnection() {
		if (connection == null) {
			 try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			String username = "root";
			String password = "";
			String jdbcUrl = "jdbc:mysql://localhost:3306/latestdb?useUnicode=true&characterEncoding=utf-8";
			try {
				connection = DriverManager.getConnection(jdbcUrl, username, password);
				System.out.println("Connection extablished");
			} catch (SQLException e) {
				System.out.println("Error in connecting to DB");
			}
		}
		return connection;
	}
	
	public static ResultSet executeQuery(String queryStatement) throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		return statement.executeQuery(queryStatement);
	}
	
	public static void executeUpdate(String updateStatement) throws SQLException {
		Connection connection = Mysql.makeConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(updateStatement);
	}
	
	public static void cancelReceiptUser(int receiptID) throws SQLException {
		String queryStatement = String.format("CALL CancelReceiptUser(%d,%d)",receiptID,LoginController.getUser().getUserID());
		System.out.println(String.format("CALL CancelReceiptUser(%d,%d)",receiptID,LoginController.getUser().getUserID()));
		Mysql.executeQuery(queryStatement);
	}
	
	public static void cancelReceiptHotel(int receiptID) throws SQLException {
		String queryStatement = String.format("CALL CancelReceiptHotel(%d,%d)",receiptID,HostController.getHotel().getHotelID());
		System.out.println(String.format("CALL CancelReceiptHotel(%d,%d)",receiptID,HostController.getHotel().getHotelID()));
		Mysql.executeQuery(queryStatement);
	}
	
	
	public static int signUpUserProcedure(Users user) throws SQLException {
		String queryStatement = String.format("CALL SignUpUser('%s','%s','%s','%s','%s')",
				user.getName(),user.getPhoneNumber(),user.getEmail(),user.getUsername(),user.getPassword());
		System.out.println(String.format("CALL SignUpUser('%s','%s','%s','%s','%s')",
				user.getName(),user.getPhoneNumber(),user.getEmail(),user.getUsername(),user.getPassword()));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		return tmp.getInt(1);
	}
	
	public static int signUpHotelManagerProcedure(HotelEmployees manager,Hotels hotel) throws SQLException {
		String queryStatement = String.format("CALL SignUpManager('%s','%s','%s','%s','%s','%s','%s',%d)",
				manager.getName(),manager.getPhoneNumber(),manager.getEmail(),manager.getUsername(),manager.getPassword(),
				hotel.getName(),hotel.getAddress(),hotel.getStreetID());
		System.out.println(String.format("CALL SignUpManager('%s','%s','%s','%s','%s','%s','%s',%d)",
				manager.getName(),manager.getPhoneNumber(),manager.getEmail(),manager.getUsername(),manager.getPassword(),
				hotel.getName(),hotel.getAddress(),hotel.getStreetID()));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		return tmp.getInt(1);
	}
	
	public static Users loginProcedure(String username,String password) throws SQLException {
		String queryStatement=String.format("CALL Login('%s','%s')",username,password);
		System.out.println(String.format("CALL Login('%s','%s')",username,password));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		Users user = new Users();
		tmp.next();
		
		if(tmp.getInt(1)==-1)
			return null;
		
		try {
			user = new HotelEmployees(tmp.getInt("user.id"), tmp.getInt("hotelmanager.id"), tmp.getString("name"),
					tmp.getString("phone"), tmp.getString("email"),tmp.getString("username"), tmp.getString("password"));
		} catch (Exception e) {
			user = new Users(tmp.getInt("user.id"), tmp.getString("name"), tmp.getString("phone"), tmp.getString("email"),
					tmp.getString("username"), tmp.getString("password"));
			e.printStackTrace();
		}
		return user;
	}
	
	public static HotelEmployees userViewManagerDetail(int receiptID) throws SQLException {
		String queryStatement=String.format("CALL UserViewManagerDetail(%d)",receiptID);
		System.out.println(String.format("CALL UserViewManagerDetail(%d)",receiptID));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if(!tmp.next())
			return null;
		return new HotelEmployees(tmp.getString("name"),tmp.getString("phone"), tmp.getString("email"));

	}
	
	public static Users managerViewUserDetail(int receiptID) throws SQLException {
		String queryStatement=String.format("CALL ManagerViewUserDetail(%d)",receiptID);
		System.out.println(String.format("CALL ManagerViewUserDetail(%d)",receiptID));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if(!tmp.next())
			return null;
		return new Users(tmp.getString("name"),tmp.getString("phone"),tmp.getString("email"));
	}
	
	public static void updateUserInfo(Users user) throws SQLException {
		String updateStatement=String.format("CALL UpdateUserData(%d,'%s','%s','%s','%s')",
				LoginController.getUser().getUserID(),user.getName(),user.getPhoneNumber(),user.getEmail(),user.getPassword());
		System.out.println(String.format("CALL UpdateUserData(%d,'%s','%s','%s','%s')",
				LoginController.getUser().getUserID(),user.getName(),user.getPhoneNumber(),user.getEmail(),user.getPassword()));
		Mysql.executeQuery(updateStatement);
	}
	
	public static int addHotelProcedure(String name, String address,int streetID,int managerID) throws SQLException {
		String queryStatement=String.format("CALL InsertHotel('%s','%s',%d,%d)",name,address,streetID,managerID);
		System.out.println(String.format("CALL InsertHotel('%s','%s',%d,%d)",name,address,streetID,managerID));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		tmp.next();
		return tmp.getInt(1);
	}
	
	public static void addReceipt(int numberOfRoom,int hotelID,Date checkinDate,Date checkoutDate,int userID) throws SQLException {
		String updateStatement=String.format("CALL ConfirmBooking(%d,%d,'%s','%s',%d)",
				numberOfRoom,hotelID,checkinDate,checkoutDate,userID);
		System.out.println(String.format("CALL ConfirmBooking(%d,%d,'%s','%s',%d)",
				numberOfRoom,hotelID,checkinDate,checkoutDate,userID));
		Mysql.executeQuery(updateStatement);
	}
	
	public static ArrayList<Hotels> filterSearching(Filters filter) throws SQLException {
		String queryStatement=String.format("CALL FilteredSearch('%s','%s',%d,'%s')",
				filter.getDestination(),filter.getHotelName(),filter.getStar(),filter.filterToStringForQuery());
		System.out.println(String.format("CALL FilteredSearch('%s','%s',%d,'%s')",
				filter.getDestination(),filter.getHotelName(),filter.getStar(),filter.filterToStringForQuery()));
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		
		ArrayList<Integer> hotel_IDs = new ArrayList<>();
		
		while (tmp.next()) {
			hotel_IDs.add(tmp.getInt(1));
		}
		
		if (hotel_IDs.size() == 0) {
			System.out.println("array of hotel = NULL/ There is no hotel meets this filter");
			return null;
		}

		ArrayList<Hotels> hotels = Hotels.queryHotelInfo(hotel_IDs);
		return hotels;
	}
	
	public static void managerUpdateHotelInfo(int hotelID,int star,int price,String filterString) throws SQLException {
		String updateStatement=String.format("CALL ManagerUpdateDetail(%d,%d,%d,'%s')",
				hotelID,star,price,filterString);
		System.out.println(String.format("CALL ManagerUpdateDetail(%d,%d,%d,'%s')",
				hotelID,star,price,filterString));
		Mysql.executeUpdate(updateStatement);
	}
	
	public static void managerAddRoom(int hotelID) throws SQLException {
		String updateStatement=String.format("CALL ManagerAddRoom(%d)",hotelID);
		System.out.println(String.format("CALL ManagerAddRoom(%d)",hotelID));
		Mysql.executeUpdate(updateStatement);
	}
}

