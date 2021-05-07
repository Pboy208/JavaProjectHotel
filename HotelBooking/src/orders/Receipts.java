package orders;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import database.Postgre;

public class Receipts {
	
	private int receipt_id;
	private int hotelID;
	private LocalDate orderDate;
	private Date checkinDate;
	private Date checkoutDate;
	private int roomID;
	private int userID;
	
	public Receipts(int hotelID, int userID, int roomID, Date checkinDate,Date checkoutDate, LocalDate orderDate){
		// TODO Auto-generated constructor stub
		this.setOrderDate(orderDate);
		this.setCheckinDate(checkinDate);
		this.setCheckoutDate(checkoutDate);
		this.setHotelID(hotelID);
		this.setRoomID(roomID);
		this.setUserID(userID);
		
	}
	public int getHotelID() {
		return hotelID;
	}
	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}
	public Date getCheckinDate() {
		return checkinDate;
	}
	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}
	public Date getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	
	public void insertReciepts() throws SQLException {
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		String queryStatement = "SELECT max(receipt_id) FROM receipt" ;
		ResultSet count = statement.executeQuery(queryStatement);
		count.next();
		this.receipt_id = count.getInt(1)+1;
		
		String insertStatement = "INSERT INTO receipt(receipt_id, hotel_id, room_id, user_id, checkin, checkout) "
								+ "	VALUES ("+this.receipt_id+",'"+this.getHotelID()+"','"+this.getRoomID()+"','"+this.getUserID()+"', "
								+ "	"+this.getCheckinDate()+","+this.getCheckoutDate()+")";
		statement.executeUpdate(insertStatement);
	}
	
	public void deleteReciepts() throws SQLException {
		Connection connection= Postgre.makeConnection();
		Statement statement= connection.createStatement();
		String queryStatement = "DELETE FROM receipt WHERE receipt_id = " + this.receipt_id +";" ;
		statement.executeQuery(queryStatement);	
	}
	
}
