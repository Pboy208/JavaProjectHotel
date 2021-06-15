package model.locations;

public class Provinces {

	int provinceID;
	String provinceName;
	
	public Provinces(int provinceID,String provinceName) {
		this.provinceID=provinceID;
		this.provinceName=provinceName;
	}
	
	public int getProvinceID() {
		return provinceID;
	}
	public void setProvinceID(int provinceID) {
		this.provinceID = provinceID;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	

}
