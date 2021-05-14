package rooms;

public class Filter {
	private int star;
	private int extensions[] = new int[12]; // 1,0 ?
	private String location;
	private String destination;
	private String hotelName;
	public static final String[] extensionLibrary = { "have_breakfast", "free_wifi", "car_park", "airport_transport",
			"restaurant", "deposit", "baby_service", "bar", "laundry", "tour", "spa", "pool" };

	public Filter() {

	}

	// -------------------------------------------------------------------------------

	public void setExtensions(int[] extension) {
		this.extensions = extension;
	}

	public int[] getExtensions() {
		return this.extensions;
	}

	public static String[] getExtensionsLibrary() {
		return Filter.extensionLibrary;
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
