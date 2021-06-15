package model.locations;

public class Street {

	int streetID;
	String streetName;
	
	public Street(int streetID,String streetName) {
		this.streetID=streetID;
		this.streetName=streetName;
	}
	
	public int getStreetID() {
		return streetID;
	}
	public void setStreetID(int provinceID) {
		this.streetID = provinceID;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String provinceName) {
		this.streetName = provinceName;
	}
	
	

}
