package controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
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
import model.library.Functions;
import model.receipts.Receipts;
import model.rooms.Filters;
import model.rooms.Hotels;
import model.rooms.Rooms;
import model.users.HotelManager;
import model.users.Users;

public class HotelInfoController implements Initializable {
	private String starString[] = { "* ", "* * ", "* * * ", "* * * * ", "* * * * * " };

	// -------------------------------------------------------------------
	@FXML
	private Label alert;
	// -------------------------------------------------------------------
	@FXML
	private TableView<Receipts> receiptsListTable;
	@FXML
	private TableColumn<Receipts, Integer> roomIDColumn;
	@FXML
	private TableColumn<Receipts, Date> checkinDateColumn;
	@FXML
	private TableColumn<Receipts, Date> checkoutDateColumn;
	@FXML
	private TableColumn<Receipts, Date> orderDateColumn;
	@FXML
	private TableColumn<Receipts, String> receiptStatusColumn;
	@FXML
	private TableColumn<Receipts, Integer> receiptIDColumn;
	@FXML
	private TableColumn<Receipts, String> priceColumn;
	@FXML
	private Label alertViewDetails;
	// -------------------------------------------------------------------
	@FXML
	private TableView<Rooms> roomsListTable;
	@FXML
	private TableColumn<Rooms, Integer> roomIDInRoomColumn;
	@FXML
	private Label alertAdjustRoom;
	// -------------------------------------------------------------------
	@FXML
	private Label hotelName;
	@FXML
	private Label hotelAddress;
	@FXML
	private TextField price;
	// -------------------------------------------------------------------
	@FXML
	private ComboBox<String> star;
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
	private Pane detailPane;
	@FXML
	private Pane infoPane;
	@FXML
	private Label guestName;
	@FXML
	private Label guestEmail;
	@FXML
	private Label guestPhoneNumber;

	// -------------------------------------------------------------------
	public void signOut(ActionEvent event) {
		LoginController.signOut();
		new SceneChanging().changeScene(event, "Login.fxml");
	}

	public void back(ActionEvent event) {
		new SceneChanging().changeScene(event, "Host.fxml");
	}
	//check
	public void saveChange(ActionEvent event) throws SQLException {
		alertAdjustRoom.setText("");
		alert.setText("");
		alertViewDetails.setText("");

		if (price.getText().trim().isEmpty()) {
			alert.setText("Price field is Missing");
			return;
		}
		
		if(!Functions.checkPrice(price.getText())) {
			alert.setText("Invalid price");
			return;
		}

		// -------------------------------------- Hotel info
		int starInt = 0;
		for (int i = 0; i < 5; i++) {
			if (star.getValue().equals(starString[i]))
				starInt = i + 1;
		}
		// -------------------------------------- Extensions
		int extensions[] = new int[12];
		RadioButton[] rbs = { radio1, radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9, radio10, radio11,
				radio12 };
		for (int i = 0; i < 12; i++)
			if (rbs[i].isSelected())
				extensions[i] = 1;
		
		// -------------------------------------- Price
		int currentPrice = Functions.priceToInt(price.getText());
		// -------------------------------------- Update hotel info
		Hotels.managerUpdateHotelInfo(HostController.getHotel().getHotelID(), starInt, currentPrice, Filters.filterToStringForUpdate(extensions));
		alert.setText("Information changed");
	}

	public void cancelReceipt(ActionEvent event) throws SQLException {
		alertAdjustRoom.setText("");
		alert.setText("");
		alertViewDetails.setText("");

		Receipts chosenReceipt = receiptsListTable.getSelectionModel().getSelectedItem();
		if (chosenReceipt == null) {
			alertViewDetails.setText("Choose a receipt");
			return;
		}
		chosenReceipt.printInfo();
		if (chosenReceipt.getStatus() == -1) {
			alertViewDetails.setText("The receipt has already been finished");
			return;
		}
		if (chosenReceipt.getStatus() == 1) {
			alertViewDetails.setText("Can not cancel this receipt");
			return;
		}
		if (chosenReceipt.getStatus() == 2) {
			alertViewDetails.setText("The receipt has already been cancelled");
			return;
		}
		Receipts.cancelReceiptHotel(chosenReceipt.getReceiptID());
		
		ArrayList<Receipts> receipts = Receipts.queryReceiptsForHotel(LoginController.getUser());
		ObservableList<Receipts> receiptList = FXCollections.observableArrayList(receipts);
		receiptsListTable.setItems(receiptList);
		alertViewDetails.setText("Booking cancelled");
	}
	
	//check
	public void viewDetails(ActionEvent event) throws SQLException {
		infoPane.setEffect(new GaussianBlur(20));
		detailPane.setVisible(true);
		detailPane.toFront();
		detailPane.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		detailPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		// --------------------------------------------------------------
		alertAdjustRoom.setText("");
		alert.setText("");
		alertViewDetails.setText("");
		Receipts chosenReceipt = receiptsListTable.getSelectionModel().getSelectedItem();
		if (chosenReceipt == null) {
			alertViewDetails.setText("Choose a receipt");
			return;
		}

		Users guest = Users.managerViewUserDetail(chosenReceipt.getReceiptID());
		
		guestName.setText("Guest's Name: " + guest.getName());
		guestEmail.setText("Guest's Email: " + guest.getEmail());
		guestPhoneNumber.setText("Guest's Phone Number: " + guest.getPhoneNumber());
	}

	public void backToMainPage(ActionEvent event) {
		alertAdjustRoom.setText("");
		alert.setText("");
		alertViewDetails.setText("");
		detailPane.setVisible(false);
		infoPane.setEffect(null);
	}

	//check
	public void addRoom(ActionEvent event) throws SQLException {
		alertAdjustRoom.setText("");
		Rooms.managerAddRoom(HostController.getHotel().getHotelID());
		alertAdjustRoom.setText("1 room is added");
		refreshRoom();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// ------------------------------------- User part
		HotelManager user = (HotelManager) LoginController.getUser();
		// ------------------------------------- Star
		ObservableList<String> starList = FXCollections.observableArrayList();
		for (int i = 0; i < 5; i++) {
			String star = "";
			for (int j = 0; j < i + 1; j++) 
				star = star.concat("* ");

			starList.add(star);
		}
		star.getItems().addAll(starList);
		// ------------------------------------- TableView For Receipt
		try { //
			Receipts.updateReceiptStatusForHotel(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ArrayList<Receipts> receipts = null;
		try {
			receipts = Receipts.queryReceiptsForHotel(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (receipts == null) {
			Label noResult = new Label("You have no receipt");
			receiptsListTable.setPlaceholder(noResult);
		} else {
			ObservableList<Receipts> receiptsList = FXCollections.observableArrayList(receipts);
			roomIDColumn.setCellValueFactory(new PropertyValueFactory<Receipts, Integer>("roomID"));
			checkinDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, Date>("checkinDate")); 
			checkoutDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, Date>("checkoutDate"));
			orderDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, Date>("orderDate"));
			receiptStatusColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("statusProperty"));
			receiptIDColumn.setCellValueFactory(new PropertyValueFactory<Receipts, Integer>("receiptID"));
			priceColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("priceProperty"));
			receiptsListTable.setItems(receiptsList);
		}
		// ------------------------------------- TableView For Rooms
			refreshRoom();
		// ------------------------------------- Filter
		Hotels hotelInfo = null;
		try {
			hotelInfo = Hotels.queryHotelByID(HostController.getHotel().getHotelID());
		} catch (SQLException e) {
			System.out.println("Error getting hotel Info in HotelInfoController");
			e.printStackTrace();
		}
		
		if(hotelInfo == null)
			System.out.println("bug");
		
		hotelName.setText("Hotel name: "+hotelInfo.getName());
		hotelAddress.setText("Hotel address: "+hotelInfo.getAddress());
		int[] hotelExtensions = hotelInfo.getExtensions();
		RadioButton[] rbs = { radio1, radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9, radio10, radio11,
				radio12 };
		for (int i = 0; i < 12; i++) {
			if (hotelExtensions[i] == 1) {
				rbs[i].setSelected(true);
			} else {
				rbs[i].setSelected(false);
			}
		}
		star.setValue(starString[hotelInfo.getStar() - 1]);
		// ------------------------------------- Price
		int currentPrice = 0;
		try {
			currentPrice = Hotels.queryPriceByID(HostController.getHotel().getHotelID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		price.setText(String.format("%,d", currentPrice));

	}
	
	private void refreshRoom() {
		ArrayList<Rooms> allRooms = null;
		try {
			allRooms = Rooms.queryAllRooms(HostController.getHotel().getHotelID());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (allRooms == null) {
			Label noResult = new Label("You have no room");
			roomsListTable.setPlaceholder(noResult);
			return;
		}
		ObservableList<Rooms> roomsList = FXCollections.observableArrayList(allRooms);
		roomIDInRoomColumn.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("roomID"));
		roomsListTable.setItems(roomsList);

	}
}
