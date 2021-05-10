package rooms;

import javafx.beans.property.SimpleStringProperty;

public class Hotel {
	private int star;
	private int hotelID;
	private int numberOfAvailableRooms;
	private float rating;
	private float latNum;
	private float longNum;
	private String address;
	private String website;
	private String provinceID;
	private String districtID;
	private String streetID;
	private String name;
	private int[] extensions;

	private SimpleStringProperty nameProperty;
	private SimpleStringProperty addressProperty;
	private SimpleStringProperty roomsAvailableProperty;
	private SimpleStringProperty starProperty;
	private SimpleStringProperty ratingProperty;

	public Hotel(int hotel_ID, String name, String address, int star_number, float rating,int numberOfAvailableRooms) {
		setHotelID(hotel_ID);
		setAddress(address);
		setName(name);
		setStar(star_number);
		setRating(rating);
		setNumberOfAvailableRooms(numberOfAvailableRooms);
		
		this.nameProperty = new SimpleStringProperty(this.name);
		this.addressProperty = new SimpleStringProperty(this.address);
		this.roomsAvailableProperty = new SimpleStringProperty(Integer.toString(numberOfAvailableRooms));
		this.ratingProperty = new SimpleStringProperty(Float.toString(this.rating));
		this.starProperty = new SimpleStringProperty(Integer.toString(star));
	}

	public Hotel() {
		// TODO Auto-generated constructor stub
	}

	// -----------------------------------------------------------------------

	public void printInfo() {
		System.out.println(name + "/" + address);
	}
	
	public int getNumberOfAvailableRooms() {
		return numberOfAvailableRooms;
	}

	public void setNumberOfAvailableRooms(int numberOfAvailableRooms) {
		this.numberOfAvailableRooms = numberOfAvailableRooms;
	}

	
	public int[] getExtensions() {
		return extensions;
	}

	public void setExtensions(int[] extensions) {
		this.extensions = extensions;
	}

	public String getNameProperty() {
		return nameProperty.get();
	}

	public void setNameProperty(SimpleStringProperty nameProperty) {
		this.nameProperty = nameProperty;
	}

	public String getAddressProperty() {
		return addressProperty.get();
	}

	public void setAddressProperty(SimpleStringProperty addressProperty) {
		this.addressProperty = addressProperty;
	}

	public String getRoomsAvailableProperty() {
		return roomsAvailableProperty.get();
	}

	public void setRoomsAvailableProperty(SimpleStringProperty roomsAvailableProperty) {
		this.roomsAvailableProperty = roomsAvailableProperty;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getStarProperty() {
		return starProperty.get();
	}

	public void setStarProperty(SimpleStringProperty starProperty) {
		this.starProperty = starProperty;
	}

	public String getRatingProperty() {
		return ratingProperty.get();
	}

	public void setRatingProperty(SimpleStringProperty ratingProperty) {
		this.ratingProperty = ratingProperty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getLatNum() {
		return latNum;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public void setLatNum(float latNum) {
		this.latNum = latNum;
	}

	public float getLongNum() {
		return longNum;
	}

	public void setLongNum(float longNum) {
		this.longNum = longNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String url) {
		this.website = url;
	}

	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	public String getProvinceID() {
		return provinceID;
	}

	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}

	public String getDistrictID() {
		return districtID;
	}

	public void setDistrictID(String district_id) {
		this.districtID = district_id;
	}

	public String getStreetID() {
		return streetID;
	}

	public void setStreetID(String streetID) {
		this.streetID = streetID;
	}

}
