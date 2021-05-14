package model.users;

public class HotelEmployees extends Users {

	private int hotelID;
	private int rank; // 1 for manager, 2 for employee

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public HotelEmployees(int userID, String name, String phone, String email, int hotelID, int rank) {
		super(userID, name, phone, email);
		setHotelID(hotelID);
		setRank(rank);
	}

	public HotelEmployees() {
		super();
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
