package model.rooms;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	
	public static String filterToStringForUpdate(int[] extensions) {
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
	
	public String filterToStringForQuery() {
		String values = "";
		int length = extensions.length;
		int flag=0;
		String library[] = Filters.getExtensionsLibrary();
		for (int i = 0; i < length; i++) {
			if (extensions[i] == 1) {
				values = values.concat(library[i]);
				values = values.concat(" = 1 AND ");
				flag=1;
			}
		}
		if(flag==1)
			values = values.substring(0, values.length() - 5);
		return values;
	}

	public static int[] queryExtensions(int hotelID) throws SQLException {
		int[] extensions = new int[12];
		String queryStatement = "SELECT * FROM hotelinfo WHERE id = " + hotelID;
		ResultSet tmp = Mysql.executeQuery(queryStatement);
		if (tmp.next() == false)
			return extensions;
		for (int i = 2; i <= 13; i++)
			if (tmp.getInt(i) == 1)
				extensions[i - 2] = 1;

		return extensions;
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
