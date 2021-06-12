package model.rooms;

import javafx.beans.property.SimpleStringProperty;

public class Hotels {
	private int star;
	private int hotelID;
	private float rating;
	private String address;
	private String provinceID;
	private String districtID;
	private String streetID;
	private String name;
	private int[] extensions;
	private int minPrice;

	private int numberOfAvailableRooms;
	private SimpleStringProperty nameProperty;
	private SimpleStringProperty addressProperty;
	private SimpleStringProperty roomsAvailableProperty;
	private SimpleStringProperty starProperty;
	private SimpleStringProperty ratingProperty;
	private SimpleStringProperty minPriceProperty;
	public Hotels(int hotel_ID, String name, String address, int star_number,int minPrice, float rating) {
		setHotelID(hotel_ID);
		setAddress(address);
		setName(name);
		setStar(star_number);
		setRating(rating);

		setMinPrice(minPrice);
		
		this.nameProperty = new SimpleStringProperty(this.name);
		this.addressProperty = new SimpleStringProperty(this.address);
		this.ratingProperty = new SimpleStringProperty(Float.toString(this.rating));
		this.starProperty = new SimpleStringProperty(Integer.toString(star));
		
		this.minPriceProperty = new SimpleStringProperty(String.format("%,d", minPrice));
		
	}
	public Hotels(int hotel_ID, String name, String address, int star_number,int[] extensions,int price) {
		setHotelID(hotel_ID);
		setAddress(address);
		setName(name);
		setStar(star_number);
		setExtensions(extensions);
		setMinPrice(price);
	}
	public Hotels() {
		// TODO Auto-generated constructor stub
	}

	// -----------------------------------------------------------------------
	public int getNumberOfAvailableRooms() {
		return numberOfAvailableRooms;
	}
	
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
		this.minPriceProperty = new SimpleStringProperty(String.format("%,d", minPrice));
	}

	public void setNumberOfAvailableRooms(int numberOfAvailableRooms) {
		this.numberOfAvailableRooms = numberOfAvailableRooms;
	}
	
	public void printInfo() {
		System.out.println(name + "/" + address);
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
	public String getMinPriceProperty() {
		return minPriceProperty.get();
	}
	public void setMinPriceProperty(SimpleStringProperty minPriceProperty) {
		this.minPriceProperty = minPriceProperty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
