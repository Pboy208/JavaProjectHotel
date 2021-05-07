package orders;

import java.util.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import orders.Receipts;

public class Orders {
	private int orderID;
	private LocalDate orderDate;
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
		this.orderDate = LocalDate.of(dateOrdered.getYear(),dateOrdered.getMonthValue(), dateOrdered.getDayOfMonth());
		this.checkinDate=checkinDate;
		this.checkoutDate=checkoutDate;
		this.hotelID=hotelID;
		this.userID=userID;
		
	}
	
	
	public void makeReceipt(Orders order) {
		Receipts receipt = new Receipts(order.hotelID, order.userID, order.roomID, order.checkinDate, order.checkoutDate, order.orderDate);
		try {
			receipt.insertReciepts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in connecting to DB");
			e.printStackTrace();
		} 
		System.out.println(receipt);
	}
	
	



}
