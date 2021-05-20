package controller;

import java.sql.Date;
import java.sql.SQLException;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.database.ReceiptsDB;
import model.database.RoomsDB;
import model.rooms.Hotels;

public class FilterController {

	public static Label confirmBooking(Label alert, DatePicker checkinTime, DatePicker checkoutTime,
			int numberOfRoomInt, Hotels hotel) throws SQLException {

		@SuppressWarnings("deprecation")
		Date checkinDate = new Date(checkinTime.getValue().getYear() - 1900, checkinTime.getValue().getMonthValue() - 1,
				checkinTime.getValue().getDayOfMonth());
		@SuppressWarnings("deprecation")
		Date checkoutDate = new Date(checkoutTime.getValue().getYear() - 1900,
				checkoutTime.getValue().getMonthValue() - 1, checkoutTime.getValue().getDayOfMonth());
		int numberOfRoomAvailable = RoomsDB.queryNumberOfAvailableRooms(hotel.getHotelID(), checkinDate, checkoutDate);
		if (numberOfRoomAvailable < numberOfRoomInt) {
			alert.setText("Not enough available rooms left");
			return alert;
		}
		ReceiptsDB.insertReciepts(numberOfRoomInt, hotel.getHotelID(), checkinDate, checkoutDate);
		alert.setText("Booking is succesfully done");
		return alert;
	}

	public static Label searchForAvailableRooms(Label informNumberOfRoom, DatePicker checkinTime,
			DatePicker checkoutTime, Hotels hotel) throws SQLException {

		@SuppressWarnings("deprecation")
		Date checkinDate = new Date(checkinTime.getValue().getYear() - 1900, checkinTime.getValue().getMonthValue() - 1,
				checkinTime.getValue().getDayOfMonth());
		@SuppressWarnings("deprecation")
		Date checkoutDate = new Date(checkoutTime.getValue().getYear() - 1900,
				checkoutTime.getValue().getMonthValue() - 1, checkoutTime.getValue().getDayOfMonth());
		int numberOfRoomAvailable = RoomsDB.queryNumberOfAvailableRooms(hotel.getHotelID(), checkinDate, checkoutDate);
		informNumberOfRoom.setText("Number Of Available Rooms: " + numberOfRoomAvailable);
		informNumberOfRoom.setVisible(true);
		return informNumberOfRoom;
	}

}
