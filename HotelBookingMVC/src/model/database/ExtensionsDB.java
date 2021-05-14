package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.rooms.Filter;
import model.rooms.Hotel;

public class ExtensionsDB {

	public static ArrayList<Hotel> queryHotelsByFilter(Filter filter) throws SQLException {
		String values = null;
		String queryStatement = "SELECT hotelinfo.hotel_id FROM hotelinfo";
		ArrayList<Integer> hotel_IDs = new ArrayList<>();
		int flagExtensionEmpty = 1;
		int flagDestinationEmpty = 1;
		int flagHotelNameEmpty = 1;

		// ------------------------- Check whether client left HotelName/Destination
		if (filter.getHotelName() != null || filter.getDestination() != null) {
			if (filter.getHotelName() != null)
				flagHotelNameEmpty = 0;
			if (filter.getDestination() != null)
				flagDestinationEmpty = 0;
			queryStatement = queryStatement.concat(" JOIN hotel ON hotel.hotel_id = hotelinfo.hotel_id"
					+ " JOIN province ON province.province_id = hotel.province_id");
		}
		// ------------------------- Check whether client left Filter empty
		for (int i = 0; i < 12; i++) {
			if (filter.getExtensions()[i] == 1) {
				flagExtensionEmpty = 0;
			}
		}

		if (flagExtensionEmpty == 0) {// not empty
			values = valuesForQuery(filter.getExtensions());
			queryStatement = queryStatement.concat(" WHERE " + values);
			if (flagDestinationEmpty == 0) {
				queryStatement = queryStatement.concat(" AND province.name = '" + filter.getDestination() + "'");
			}
			if (flagHotelNameEmpty == 0) {
				queryStatement = queryStatement.concat(" AND ( hotel.name like '%" + filter.getHotelName() + "%'"
						+ " OR hotel.name LIKE '" + filter.getHotelName() + "%'" + " OR hotel.name LIKE '%"
						+ filter.getHotelName() + "'" + " OR hotel.name LIKE '" + filter.getHotelName() + "')");
			}
			System.out.println("check1");
		}
		// -------------------------
		else { // Extension empty
			if (flagDestinationEmpty == 0 || flagHotelNameEmpty == 0)
				queryStatement = queryStatement.concat(" WHERE");
			if (flagDestinationEmpty == 0)
				queryStatement = queryStatement.concat(" province.name = '" + filter.getDestination() + "'");
			if (flagDestinationEmpty == 0 && flagHotelNameEmpty == 0)
				queryStatement = queryStatement.concat(" AND ( hotel.name like '%" + filter.getHotelName() + "%'"
						+ " OR hotel.name LIKE '" + filter.getHotelName() + "%'" + " OR hotel.name LIKE '%"
						+ filter.getHotelName() + "'" + " OR hotel.name LIKE '" + filter.getHotelName() + "')");
			else if (flagDestinationEmpty == 1 && flagHotelNameEmpty == 0)
				queryStatement = queryStatement.concat(" hotel.name like '%" + filter.getHotelName() + "%'"
						+ " OR hotel.name LIKE '" + filter.getHotelName() + "%'" + " OR hotel.name LIKE '%"
						+ filter.getHotelName() + "'" + " OR hotel.name LIKE '" + filter.getHotelName() + "'");

		}

		// -------------------------
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		System.out.println(queryStatement);
		ResultSet tmp = statement.executeQuery(queryStatement);
		while (tmp.next()) {
			hotel_IDs.add(tmp.getInt("hotel_id"));
		}
		int size = hotel_IDs.size();
		int[] array = new int[size];

		for (int i = 0; i < size; i++) {
			array[i] = hotel_IDs.get(i);
		}

		ArrayList<Hotel> hotels = HotelDB.queryHotelInfo(array);
		System.out.println("Filter File:" + hotels.size());
		if (hotels.size() == 0) {
			System.out.println("array of hotel = NULL/ There is no hotel meets this filter");
			return null;
		}

		return hotels;

	}

	private static String valuesForQuery(int[] extensions) {
		String values = "";
		int length = extensions.length;
		String library[] = Filter.getExtensionsLibrary();
		for (int i = 0; i < length; i++) {
			if (extensions[i] == 1) {
				values = values.concat(library[i]);
				// values=values.concat(" = '1' AND "); // attributes dang text for testing
				values = values.concat(" = 1 AND "); // sua khi ma attributes dang int
			}
		}
		values = values.substring(0, values.length() - 5);
		System.out.println("Values:" + values);
		return values;
	}

	public static int[] queryExtensions(int hotelID) throws SQLException {
		int[] extensions = new int[12];
		String queryStatement = "SELECT * FROM hotelinfo where hotel_id = " + hotelID;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		ResultSet tmp = statement.executeQuery(queryStatement);
		if (tmp.next() == false)
			return null;
		for (int i = 2; i <= 13; i++) {
			if (tmp.getInt(i) == 1) {
				extensions[i - 2] = 1;
			}
		}
		return extensions;
	}

	public static void updateExtensions(int hotelID, int[] extensions) throws SQLException {
		String updateStatement = "UPDATE hotelinfo SET " + valuesForUpdate(extensions) + " where hotel_id=" + hotelID;
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		System.out.println(updateStatement);
		statement.executeUpdate(updateStatement);
	}

	private static String valuesForUpdate(int[] extensions) {
		String values = "";
		int length = extensions.length;
		String library[] = Filter.getExtensionsLibrary();
		for (int i = 0; i < length; i++) {
			if (extensions[i] == 1) {
				values = values.concat(library[i]);
				values = values.concat(" = 1,");
			} else {
				values = values.concat(library[i]);
				values = values.concat(" = 0,");
			}
		}
		values = values.substring(0, values.length() - 1);
		System.out.println("Values:" + values);
		return values;
	}
}