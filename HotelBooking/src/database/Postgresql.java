package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import rooms.Hotel;
import user.Clients;
import user.HotelEmployees;


public interface Postgresql {
//	private static String[][] attributes = {
//   /*Hotel*/{"hotel_id","name","address","lat","long","province","district","street","star"},
///*Province*/{"province_id","name"},
///*District*/{"district_id","province_id","name"},
//  /*Street*/{"street_id","province_id","district_id","name",},
//  /*Domain*/{"domain_id","name"},
//   /*URL*/  {"hotel_id","domain_id","domain_hotel_id","url","min_price"}
//	};
//	private static String[] tables = {
//			"Hotel","Province","District","Street","Domain","URL"
//	};
	
	public static final Connection connection = makeConnection();
	public static Connection makeConnection() {
		String username="postgres";
		String password="005868";
		String jdbcUrl="jdbc:postgresql://localhost:5432/Hotel";
		Connection connectionA = null;
		try {
			connectionA= DriverManager.getConnection(jdbcUrl,username,password);
			System.out.println("Connection extablished");
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to DB");
		}
		return connectionA;
	}
	
	public static ResultSet query(String tableName) throws SQLException {
		String queryStatement = "SELECT * FROM "+ tableName;
		Statement statement= connection.createStatement();
		return statement.executeQuery(queryStatement);
	}
	
	public static int queryCheckPassword(String userName,String password) throws SQLException {
		Statement statement= connection.createStatement();
		ResultSet user_id =statement.executeQuery("SELECT user_id FROM account WHERE username = '"+userName+"' AND password = '"+password+"'");
		if(user_id.next()) //co tra ve
			return user_id.getInt("user_id");
		return -1;
	}
	
	public static Clients queryClientInfo(int user_id) throws SQLException {
		Clients user = new Clients(user_id);
		String queryStatement = "SELECT * FROM clients where user_id = "+user_id;
		Statement statement= connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		user.setName(tmp.getString("user_name"));
		user.setEmail(tmp.getString("email"));
		user.setPhoneNumber(tmp.getString("phone"));		
		return user;
	}
	
	public static HotelEmployees queryEmployeeInfo(int user_id) throws SQLException {
		HotelEmployees user = new HotelEmployees(user_id);
		String queryStatement = "SELECT * FROM employees where user_id = "+user_id;
		Statement statement= connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		tmp.next();
		user.setName(tmp.getString("user_name"));
		user.setEmail(tmp.getString("email"));
		user.setPhoneNumber(tmp.getString("phone"));	
		user.setHotelID(tmp.getInt("hotel_id"));
		return user;
	}
	
	public static void queryRooms(int user_id) throws SQLException {
		HotelEmployees user = new HotelEmployees(user_id);
		ResultSet tmp = query("employees");
		tmp.next();
		user.setName(tmp.getString("user_name"));
		user.setEmail(tmp.getString("email"));
		user.setPhoneNumber(tmp.getString("phone"));	
		user.setHotelID(tmp.getInt("hotel_id"));
	}
	
	public static int[] queryExtension(String values) throws SQLException {
		ArrayList<Integer> hotel_IDs = new ArrayList<>();
		String queryStatement = "SELECT hotel_id FROM info where " +values;
		Statement statement= connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		while(tmp.next()) {
			hotel_IDs.add(tmp.getInt("hotel_id"));
		}
		int size = hotel_IDs.size();
		int[] array = new int[size];
		
		for(int i=0;i<size;i++) {
			array[i]=hotel_IDs.get(i);
		}
		return array;// return list of hotel_id;
	}
	
	public static void queryHotelInfo(int hotel_id) {
		//query by hotelID
		
	}
	
	public static ArrayList<Hotel> queryHotelInfo(int[] hotel_ids) throws SQLException {
		//query by hotelID
		String queryStatement = "SELECT * FROM hotel where hotel_id = '" ;
		Statement statement= connection.createStatement();
		ArrayList<Hotel> hotels = new ArrayList<>();
		for(int i : hotel_ids) {
			String fullQueryStatement=queryStatement+i+"'";
			//System.out.println(fullQueryStatement);
			ResultSet tmp = statement.executeQuery(fullQueryStatement);
			
			tmp.next();
//			Hotel tmpHotel= new Hotel(
//					Integer.parseInt(tmp.getString(1)),
//					tmp.getString(2),
//					tmp.getString(3),
//					Integer.parseInt(tmp.getString(6))); 
// KEEP THIS!
			Hotel tmpHotel= new Hotel(tmp.getInt(1),tmp.getString(2),tmp.getString(3),tmp.getInt(6));
			hotels.add(tmpHotel);
		}
		return hotels;
	}
	
	public static int insertClients(String user_name,String phoneNumber,String email,String username,String password) throws SQLException {
		String insertInfo="INSERT INTO clients(user_name,phone,email) VALUES ('"+user_name+"','"+phoneNumber+"','"+email+"')";
		String query = "SELECT user_id FROM clients WHERE user_name = '"+user_name+"' AND phone='"+phoneNumber+"' AND email='"+email+"'";
		Statement statement= connection.createStatement();
		statement.executeUpdate(insertInfo);
		
		ResultSet tmp = statement.executeQuery(query);
		tmp.next();
		int id=tmp.getInt("user_id");
		
		String updateAccount="INSERT INTO account(user_id,username,password) VALUES ("+id+",'"+username+"','"+password+"')";
		return statement.executeUpdate(updateAccount);
	}
	
	public static int insertHotelEmployees(int hotel_ID,String user_name,String phoneNumber,String email,String username,String password) throws SQLException {
		String insertInfo="INSERT INTO employees(user_name,phone,email,hotel_ID) VALUES ('"+user_name+"','"+phoneNumber+"','"+email+"',"+hotel_ID+")";
		String query = "SELECT user_id FROM employees WHERE user_name = '"+user_name+"' AND phone='"+phoneNumber+"' AND email='"+email+"'";
		Statement statement= connection.createStatement();
		statement.executeUpdate(insertInfo);
		
		ResultSet tmp = statement.executeQuery(query);
		tmp.next();
		int id=tmp.getInt("user_id");
		
		String updateAccount="INSERT INTO account_employees(user_id,username,password) VALUES ("+id+",'"+username+"','"+password+"')";
		return statement.executeUpdate(updateAccount);
	}
}

