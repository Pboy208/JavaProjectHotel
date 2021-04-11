package rooms;

import java.sql.Date;

public class Room {
	
	private int roomNumber;
	private int roomID; // id == roomNumber?
	//----------------------------------------------------------------------------------------------
	//rating library
	private float rating[] = new float[7]; //0-10 ?
	private static String[] ratingLibrary = {"clean_score","meal_score","location_score","sleep_score",
				"room_score","service_score","facility_score"};
	
	private int extensions[] = new int[7]; //1,0 ?
	private static String[] extensionLibrary = {"breakfast","wifi","carpark","airport",
			"restaurant","depositbox","baby","bar","laundry","tour","spa","pool"};
	//----------------------------------------------------------------------------------------------
	private float overallScore;
	private float numberOfReview;
	private float reviewScore;
	//----------------------------------------------------------------------------------------------
	private float price;
	//
	private int status;		//1,0 
	//
	private Date checkinDate;
	private Date checkoutDate;
	
	public Room() {
		// TODO Auto-generated constructor stub
	}
	
	
}
