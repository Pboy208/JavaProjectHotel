package model.rooms;

public class Filters {
	private int star;
	private int extensions[] = new int[12]; // 1,0 ?
	private String location;
	private String destination;
	private String hotelName;
	public static final String[] extensionLibrary = { "have_breakfast", "free_wifi", "have_car_park", "transport_airport",
			"have_restaurant", "have_deposit", "baby_service", "have_bar", "have_laundry", "have_tour", "have_spa", "have_pool" };

	public Filters() {

	}

	// -------------------------------------------------------------------------------

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
