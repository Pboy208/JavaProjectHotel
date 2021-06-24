package model.rooms;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.database.DBInterface;
import model.database.Mysql;

public class HotelInfo implements DBInterface {

	public static final String[] extensionLibrary = { "have_breakfast", "free_wifi", "have_car_park",
			"transport_airport", "have_restaurant", "have_deposit", "baby_service", "have_bar", "have_laundry",
			"have_tour", "have_spa", "have_pool" };

	private int extensions[] = new int[12]; // 1,0 ?

	private int hotelID;

	public HotelInfo(int hotelID, int extensions[]) {
		setHotelID(hotelID);
		setExtensions(extensions);
	}

	public HotelInfo(int hotelID) {
		setHotelID(hotelID);
	}

	public HotelInfo() {

	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void insertInstance() throws SQLException {
		String insertStatement = String.format("INSERT INTO hotelinfo VALUES(%s)", extensionsToStringForInsert());
		Mysql.executeUpdate(insertStatement);
	}

	@Override
	public void queryInstance(int pk) throws SQLException {
		hotelID = pk;
		String queryStatement = String.format("SELECT * FROM hotelinfo WHERE id = %d", pk);
		ResultSet result = Mysql.executeQuery(queryStatement);
		result.next();
		for (int i = 0; i < 12; i++) {
			extensions[i] = result.getInt(i + 2);
		}
	}

	@Override
	public void updateInstance() throws SQLException {
		String updateStatement = String.format("UPDATE hotelinfo SET %s WHERE id = ", extensionsToStringForUpdate(),
				hotelID);
		Mysql.executeUpdate(updateStatement);
	}

	public String extensionsToStringForInsert() {
		String values = "id = " + hotelID;
		int length = extensions.length;
		String library[] = getExtensionlibrary();
		for (int i = 0; i < length; i++)
			if (extensions[i] == 1) {
				values = values.concat(library[i]);
				values = values.concat(" , = 1");
			} else {
				values = values.concat(library[i]);
				values = values.concat(" , = 0");
			}

		return values;
	}

	public String extensionsToStringForUpdate() {
		String values = "";
		int length = extensions.length;
		String library[] = getExtensionlibrary();
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

	public String extensionsToStringForQuery() {
		String values = "";
		int length = extensions.length;
		int flag = 0;
		String library[] = getExtensionlibrary();
		for (int i = 0; i < length; i++) {
			if (extensions[i] == 1) {
				values = values.concat(library[i]);
				values = values.concat(" = 1 AND ");
				flag = 1;
			}
		}
		if (flag == 1)
			values = values.substring(0, values.length() - 5);
		return values;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------

	public static String[] getExtensionlibrary() {
		return extensionLibrary;
	}

	public int[] getExtensions() {
		return extensions;
	}

	public void setExtensions(int[] extensions) {
		this.extensions = extensions;
	}

	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

}
