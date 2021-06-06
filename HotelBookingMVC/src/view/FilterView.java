package view;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import controller.FilterController;
import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.database.ExtensionsDB;
import model.rooms.Filters;
import model.rooms.Hotels;

public class FilterView implements Initializable {

	// -------------------------------------------------------------------
	@FXML
	private Pane filterPane;
	@FXML
	private Pane bookingPane;
	// -------------------------------------------------------------------
	@FXML
	private DatePicker checkinTime;
	@FXML
	private DatePicker checkoutTime;
	@FXML
	private TextField numberOfRoom;
	@FXML
	private Label informNumberOfRoom;
	@FXML
	private Label alert;
	@FXML
	private Button searchForRooms;
	@FXML
	private Button confirmBooking;
	// -------------------------------------------------------------------
	@FXML
	private TextField destination;
	@FXML
	private TextField hotelName;
	@FXML
	private Label label;
	// -------------------------------------------------------------------
	@FXML
	private RadioButton radio1;
	@FXML
	private RadioButton radio2;
	@FXML
	private RadioButton radio3;
	@FXML
	private RadioButton radio4;
	@FXML
	private RadioButton radio5;
	@FXML
	private RadioButton radio6;
	@FXML
	private RadioButton radio7;
	@FXML
	private RadioButton radio8;
	@FXML
	private RadioButton radio9;
	@FXML
	private RadioButton radio10;
	@FXML
	private RadioButton radio11;
	@FXML
	private RadioButton radio12;
	// -------------------------------------------------------------------
	@FXML
	private ComboBox<String> star;
	// -------------------------------------------------------------------
	@FXML
	private TableView<Hotels> recommendHotels;
	@FXML
	private TableColumn<Hotels, String> nameHotel;
	@FXML
	private TableColumn<Hotels, String> addressHotel;
	@FXML
	private TableColumn<Hotels, String> starHotel;
	@FXML
	private TableColumn<Hotels, String> ratingHotel;

	public void signOut(ActionEvent event) {
		new SceneChanging().changeScene(event, "Login.fxml");
		LoginController.signOut();
	}

	public void clientInfo(ActionEvent event) {
		new SceneChanging().changeScene(event, "ClientInfo.fxml");
	}

	public void book(ActionEvent e) {
		alert.setText("");
		filterPane.setEffect(new GaussianBlur(20));
		bookingPane.setVisible(true);
		bookingPane.toFront();
		bookingPane.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		bookingPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public void confirmBooking(ActionEvent event) throws SQLException {
		if (!informNumberOfRoom.isVisible()) {
			alert.setText("Please choose checkin and check out day");
			return;
		}
		if (numberOfRoom.getText().trim().isEmpty()) {
			alert.setText("Please fill in number of room that you want to book");
			return;
		}
		Hotels hotel = recommendHotels.getSelectionModel().getSelectedItem();
		
		int numberOfRoomInt = 0;
		try {
			numberOfRoomInt = Integer.parseInt(numberOfRoom.getText());
	    } catch (Exception e) {
	    	alert.setText("Number of room must be a number");
			return;
	    }
		
		alert = FilterController.confirmBooking(alert, checkinTime, checkoutTime, numberOfRoomInt, hotel);
		informNumberOfRoom = FilterController.searchForAvailableRooms(informNumberOfRoom, checkinTime, checkoutTime,
				hotel);
	}

	public void searchForAvailableRooms(ActionEvent event) throws SQLException {
		alert.setText("");
		if (checkinTime.getValue().toString().isEmpty() || checkoutTime.getValue().toString().isEmpty()) {
			alert.setText("Please choose checkin and checkout days");
			return;
		}
		if (checkinTime.getValue().isAfter(checkoutTime.getValue())
				|| checkinTime.getValue().isEqual(checkoutTime.getValue())) {
			alert.setText("Checkout day must be after checkin day at least 1 day");
			return;
		}
		if (checkinTime.getValue().isBefore(LocalDate.now())) {
			alert.setText("You can not select day in the past");
			return;
		}
		if (checkinTime.getValue().isEqual(LocalDate.now())) {
			alert.setText("Please book room a day in advanced");
			return;
		}
		Hotels hotel = recommendHotels.getSelectionModel().getSelectedItem();
		informNumberOfRoom = FilterController.searchForAvailableRooms(informNumberOfRoom, checkinTime, checkoutTime,
				hotel);
	}

	public void back(ActionEvent event) {
		bookingPane.setVisible(false);
		filterPane.setEffect(null);
		alert.setText("");
		informNumberOfRoom.setText("");
		checkinTime.setValue(null);
		checkoutTime.setValue(null);
		numberOfRoom.clear();
	}

	public void searchButton(ActionEvent e) throws SQLException {
		int array[] = new int[12];
		Filters filter = new Filters();
		// ----------------------------------------- Destination part
		if (destination.getText().trim().isEmpty()) {
			System.out.println("Null Destination");
			filter.setDestination(null);
		} else {
			System.out.println("Destination " + destination.getText());
			filter.setDestination(destination.getText());
		}
		// ----------------------------------------- HotelName part
		if (hotelName.getText().trim().isEmpty()) {
			System.out.println("Null Hotel Name");
			filter.setHotelName(null);
		} else {
			System.out.println("Hotel " + hotelName.getText());
			filter.setHotelName(hotelName.getText());
		}
		// ----------------------------------------- Extension part
		RadioButton[] rbs = { radio1, radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9, radio10, radio11,
				radio12 };
		for (int i = 0; i < 12; i++)
			if (rbs[i].isSelected())
				array[i] = 1;
		filter.setExtensions(array);

		// ----------------------------------------- Gui part
		ArrayList<Hotels> recommendedHotelsList = ExtensionsDB.queryHotelsByFilter(filter);
		if (recommendedHotelsList == null) {
			Label noResult = new Label("No hotel meets your filter");
			recommendHotels.setPlaceholder(noResult);
			ObservableList<Hotels> tableListNull = FXCollections.observableArrayList();
			recommendHotels.setItems(tableListNull);
			return;
		}
		ObservableList<Hotels> tableList = FXCollections.observableArrayList(recommendedHotelsList);
		nameHotel.setCellValueFactory(new PropertyValueFactory<Hotels, String>("nameProperty"));
		addressHotel.setCellValueFactory(new PropertyValueFactory<Hotels, String>("addressProperty"));
		starHotel.setCellValueFactory(new PropertyValueFactory<Hotels, String>("starProperty"));
		ratingHotel.setCellValueFactory(new PropertyValueFactory<Hotels, String>("ratingProperty"));
		recommendHotels.setItems(tableList);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		recommendHotels.setPlaceholder(new Label("Please fill in your filter"));
		// ----------------------------------------- Star part
		ObservableList<String> starList = FXCollections.observableArrayList();
		for (int i = 0; i < 5; i++) {
			String star = "";
			for (int j = 0; j < i + 1; j++) {
				star = star.concat("* ");
			}
			starList.add(star);
		}
		starList.add("Any Star");
		star.getItems().addAll(starList);
		star.setValue("Any Star");
	}

}
