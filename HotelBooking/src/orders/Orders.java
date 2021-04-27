package orders;

import java.sql.Date;

public class Orders {
	private int orderID;
	private Date orderDate;
	private Date checkinDate;
	private Date checkoutDate;
	
	private int hotelID;
	private int roomID;
	
	private int userID;
	
	private int paymentVerify; //1,0;
	
	public Orders() {
		// TODO Auto-generated constructor stub
	}
	
	public void printReceipt(){
		
	}
}
