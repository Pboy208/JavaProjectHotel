package rooms;

import javafx.beans.property.SimpleStringProperty;

public class Hotel {
	private float latNum;
	private float longNum;
	private String address;
	private String url;
	private int hotelID;
	private String provinceID;
	private String districtID;
	private String streetID;
	private String name;
	private int star;
	private float rating;
	
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
	private SimpleStringProperty nameProperty;
	private SimpleStringProperty addressProperty;
	private SimpleStringProperty roomsAvailableProperty;
	private SimpleStringProperty starProperty;
	private SimpleStringProperty ratingProperty;
	
	public Hotel(int hotel_ID,String name,String address,int star_number,float rating) {
		setHotelID(hotel_ID);
		setAddress(address);
		setName(name);
		setStar(star_number);
		setRating(rating);
		
		this.nameProperty=new SimpleStringProperty(this.name);
		this.addressProperty=new SimpleStringProperty(this.address);
		this.roomsAvailableProperty=new SimpleStringProperty(Integer.toString(getAvailableRooms()));
		this.ratingProperty=new SimpleStringProperty(Float.toString(this.rating));
		this.starProperty=new SimpleStringProperty(Integer.toString(star));
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

	public void prinInfo() {
		System.out.println(this.name+"/"+this.address+"/"+this.star);
	}
	
	public int getAvailableRooms() {
		return 1;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public Hotel() {
		// TODO Auto-generated constructor stub
	}

}
