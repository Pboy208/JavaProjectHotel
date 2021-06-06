package model.locations;

public class Districts {

	int districtID;
	String districtName;
	
	public Districts(int districtID,String districtName) {
		this.districtID=districtID;
		this.districtName=districtName;
	}
	
	public int getDistrictID() {
		return districtID;
	}
	public void setDistrictID(int provinceID) {
		this.districtID = provinceID;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String provinceName) {
		this.districtName = provinceName;
	}
	
	

}
