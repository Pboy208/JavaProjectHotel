package model.rooms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.database.Mysql;

public class Filters {
	private int star;
	private int extensions[] = new int[12]; // 1,0 ?
	private String location;
	private String destination;
	private String hotelName;
	public static final String[] extensionLibrary = { "have_breakfast", "free_wifi", "have_car_park",
			"transport_airport", "have_restaurant", "have_deposit", "baby_service", "have_bar", "have_laundry",
			"have_tour", "have_spa", "have_pool" };

	public Filters() {

	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------

	public static ArrayList<Hotels> queryHotelsByFilter(Filters filter) throws SQLException {
		String values = null;
		String queryStatement = "SELECT hotelinfo.id FROM hotelinfo";
		ArrayList<Integer> hotel_IDs = new ArrayList<>();
		int flagExtensionEmpty = 1;
		int flagDestinationEmpty = 1;
		int flagHotelNameEmpty = 1;
		int flagStarEmpty = 1;

	
		String conditionDestination = String.format(" ( province.name like '%%%s%%'"
				+ " OR province.name LIKE '%s%%'"
				+ " OR province.name LIKE '%%%s'"
				+ " OR province.name LIKE '%s')",
				filter.getDestination(), filter.getDestination(), filter.getDestination(), filter.getDestination());
		
		String conditionName = String.format(" ( hotel.name like '%%%s%%'"
				+ " OR hotel.name LIKE '%s%%'"
				+ " OR hotel.name LIKE '%%%s'"
				+ " OR hotel.name LIKE '%s')",
				filter.getHotelName(), filter.getHotelName(), filter.getHotelName(), filter.getHotelName());
		
		String conditionStar = " star = " + filter.getStar();
		
		// ------------------------- Check whether client left HotelName/Destination
		if (filter.getHotelName() != null || filter.getDestination() != null || filter.getStar() != 0) {
			if (filter.getHotelName() != null)
				flagHotelNameEmpty = 0;
			if (filter.getDestination() != null)
				flagDestinationEmpty = 0;
			if (filter.getStar() != 0)
				flagStarEmpty = 0;
			queryStatement = queryStatement.concat(" JOIN hotel ON (hotelinfo.id = hotel.id"
							+ " JOIN street ON (street.id = hotel.street_id)"
							+ " JOIN district ON (district.id = street.district_id)"
							+ " JOIN province ON (province.id = district.province_id)");

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
			if (flagDestinationEmpty == 0)
				queryStatement = queryStatement.concat(" AND " + conditionDestination);

			if (flagHotelNameEmpty == 0)
				queryStatement = queryStatement.concat(" AND " + conditionName);

			if (flagStarEmpty == 0)
				queryStatement = queryStatement.concat(" AND " + conditionStar);
		}

		// -------------------------
		else { // Extension empty
			if (flagDestinationEmpty == 0 || flagHotelNameEmpty == 0 || flagStarEmpty == 0) {
				queryStatement = queryStatement.concat(" WHERE");

				if (flagDestinationEmpty == 0) {
					queryStatement = queryStatement.concat(conditionDestination);

					if (flagHotelNameEmpty == 0)
						queryStatement = queryStatement.concat(" AND " + conditionName);

					if (flagStarEmpty == 0)
						queryStatement = queryStatement.concat(" AND " + conditionStar);
				} else {
					if (flagHotelNameEmpty == 0) {
						queryStatement = queryStatement.concat(conditionName);

						if (flagStarEmpty == 0)
							queryStatement = queryStatement.concat(" AND " + conditionStar);
					} else {
						if (flagStarEmpty == 0)
							queryStatement = queryStatement.concat(conditionStar);
					}
				}
			}
		}

		// -------------------------
		System.out.println(queryStatement);
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		while (tmp.next()) {
			hotel_IDs.add(tmp.getInt("id"));
		}
		int size = hotel_IDs.size();
		int[] array = new int[size];

		for (int i = 0; i < size; i++) {
			array[i] = hotel_IDs.get(i);
		}

		ArrayList<Hotels> hotels = Hotels.queryHotelInfo(array);
		if (hotels.size() == 0) {
			System.out.println("array of hotel = NULL/ There is no hotel meets this filter");
			return null;
		}

		return hotels;

	}

	private static String valuesForQuery(int[] extensions) {
		String values = "";
		int length = extensions.length;
		String library[] = Filters.getExtensionsLibrary();
		for (int i = 0; i < length; i++) {
			if (extensions[i] == 1) {
				values = values.concat(library[i]);
				values = values.concat(" = 1 AND ");
			}
		}
		values = values.substring(0, values.length() - 5);
		System.out.println("Values:" + values);
		return values;
	}

	public static int[] queryExtensions(int hotelID) throws SQLException {
		int[] extensions = new int[12];
		String queryStatement = "SELECT * FROM hotelinfo WHERE id = " + hotelID;
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if (tmp.next() == false)
			return null;
		for (int i = 2; i <= 13; i++)
			if (tmp.getInt(i) == 1)
				extensions[i - 2] = 1;

		return extensions;
	}

	public static void updateExtensions(int hotelID, int[] extensions) throws SQLException {
		String updateStatement = "UPDATE hotelinfo SET " + valuesForUpdate(extensions) + " WHERE id=" + hotelID;
		Mysql.executeUpdate(updateStatement);
	}

	private static String valuesForUpdate(int[] extensions) {
		String values = "";
		int length = extensions.length;
		String library[] = Filters.getExtensionsLibrary();
		for (int i = 0; i < length; i++)
			if (extensions[i] == 1) {
				values = values.concat(library[i]);
				values = values.concat(" = 1,");
			} else {
				values = values.concat(library[i]);
				values = values.concat(" = 0,");
			}

		values = values.substring(0, values.length() - 1);
		return values;
	}

	public static void insertExtensions(int hotelID) throws SQLException {
		String updateStatement = "INSERT INTO hotelinfo VALUES(" + hotelID + ",0,0,0,0,0,0,0,0,0,0,0,0)";
		Mysql.executeUpdate(updateStatement);
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------

	public void setExtensions(int[] extension) {
		this.extensions = extension;
	}

	public int[] getExtensions() {
		return this.extensions;
	}

	public static String[] getExtensionsLibrary() {
		return Filters.extensionLibrary;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

}
