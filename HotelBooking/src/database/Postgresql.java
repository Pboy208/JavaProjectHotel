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
		String password="Nguyentuandung2901";
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
}

