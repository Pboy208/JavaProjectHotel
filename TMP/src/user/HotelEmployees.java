package user;

public class HotelEmployees extends User {

	private int hotelID;
	private int rank;  //1 for manager, 2 for employee
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public HotelEmployees(int user_id) {
		super(user_id);
	}

	
	public HotelEmployees(int userID,String name, String phone, String email,int hotelID,int rank) {
		super(userID,name, phone, email);
		setRank(rank);
		setHotelID(hotelID);
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int departmentID) {
		this.hotelID = departmentID;
	}

	public void printInfo() {
		System.out.println(this.getName() + "/" + this.getPhoneNumber() + "/" + this.getEmail() + "/" + this.hotelID);
	}
}
