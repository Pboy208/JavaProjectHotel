package rooms;


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

	public Hotel(int hotel_ID,String name,String address,int star_number) {
		setHotelID(hotel_ID);
		setAddress(address);
		setName(name);
		setStar(star_number);
	}
	
	public void prinInfo() {
		System.out.println(this.name+"/"+this.address+"/"+this.star);
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
