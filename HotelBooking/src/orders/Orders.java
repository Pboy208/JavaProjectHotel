package orders;

import java.sql.Date;
import java.time.LocalDate;

public class Orders {
	private int orderID;
	private Date orderDate;
	private Date checkinDate;
	private Date checkoutDate;
	
	private int hotelID;
	private int roomID;
	
	private int userID;
	private int roomIDs[];
	private int paymentVerify; //1,0;
	
	public Orders(Date checkinDate,Date checkoutDate,int hotelID,int userID,int numberOfRooms){
		// TODO Auto-generated constructor stub
		LocalDate dateOrdered = LocalDate.now();
		this.orderDate = new Date(dateOrdered.getYear(),dateOrdered.getMonthValue(), dateOrdered.getDayOfMonth());
		this.checkinDate=checkinDate;
		this.checkoutDate=checkoutDate;
		this.hotelID=hotelID;
		//this.roomID
		this.userID=userID;
		
	}
	
	
	public void printReceipt(){
		
	}
}
