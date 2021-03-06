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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
import model.database.AccountsDB;
import model.database.HotelsDB;
import model.database.HotelEmployeesDB;
import model.database.ReceiptsDB;
import model.database.RoomsDB;
import model.database.UsersDB;
import model.library.Functions;
import model.receipts.Receipts;
import model.rooms.Hotels;
import model.rooms.Rooms;
import model.users.HotelEmployees;
import model.users.Users;

public class HotelInfoController implements Initializable {
	private String starString[] = { "* ", "* * ", "* * * ", "* * * * ", "* * * * * " };

	// -------------------------------------------------------------------
	@FXML
	private Label accountName;
	@FXML
	private TextField name;
	@FXML
	private TextField phone;
	@FXML
	private TextField email;
	@FXML
	private PasswordField oldPW;
	@FXML
	private PasswordField newPW;
	@FXML
	private PasswordField newPWConfirm;
	@FXML
	private Label alert;
	// -------------------------------------------------------------------
	@FXML
	private TableView<Receipts> receiptsListTable;
	@FXML
	private TableColumn<Receipts, String> roomIDColumn;
	@FXML
	private TableColumn<Receipts, Date> checkinDateColumn;
	@FXML
	private TableColumn<Receipts, String> checkoutDateColumn;
	@FXML
	private TableColumn<Receipts, String> orderDateColumn;
	@FXML
	private TableColumn<Receipts, String> receiptStatusColumn;
	@FXML
	private TableColumn<Receipts, String> receiptIDColumn;
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
	private TableColumn<Rooms, String> priceInRoomColumn;
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
	private Button signUp;

	// -------------------------------------------------------------------
	@FXML
	private Pane detailPane;
	@FXML
	private Pane infoPane;
	@FXML
	private Pane managerPart;
	@FXML
	private Label guestName;
	@FXML
	private Label guestEmail;
	@FXML
	private Label guestPhoneNumber;

	// -------------------------------------------------------------------
	public void signOut(ActionEvent event) {
		new SceneChanging().changeScene(event, "Login.fxml");
		LoginController.signOut();
	}

	public void saveChange(ActionEvent event) throws SQLException {
		alertAdjustRoom.setText("");
		alert.setText("");
		alertViewDetails.setText("");
		// -------------------------------------- User info
		HotelEmployees user = (HotelEmployees) LoginController.getUser();
		boolean visibleFlag = false;
		if (oldPW.isVisible() == true) {
			visibleFlag = true;
		}

		if (name.getText().trim().isEmpty() || email.getText().trim().isEmpty() || phone.getText().trim().isEmpty()
				|| hotelName.getText().trim().isEmpty() || hotelAddress.getText().trim().isEmpty()) {
			alert.setText("Some fields is Missing");
			return;
		}
		if (visibleFlag) {
			if (oldPW.getText().trim().isEmpty() || newPW.getText().trim().isEmpty()
					|| newPWConfirm.getText().trim().isEmpty()) {
				alert.setText("Some fields is Missing");
				return;
			}
		}
		String newName = name.getText();
		String newEmail = email.getText();
		String newPhone = phone.getText();
		
		if(!Functions.checkPhoneNumber(newPhone)) {
			alert.setText("Phone number must be a sequence of 10 numbers");
			phone.clear();
			return;
		}
		
		if(!Functions.checkEmail(newEmail)) {
			alert.setText("Invalid email address");
			email.clear();
			return;
		}
		
		String oldPwString = null;
		String newPWString = null;
		String newPWConfirmString = null;
		
		if (visibleFlag) {
			oldPwString = oldPW.getText();
			newPWString = newPW.getText();
			newPWConfirmString = newPWConfirm.getText();
			if (AccountsDB.checkPassword(user.getUsername(), oldPwString) == -1) {
				alert.setText("Current password is incorrect");
				return;
			}
			if (!newPWString.equals(newPWConfirmString)) {
				alert.setText("Comfirmation of password is wrong");
				return;
			}
			if (user.getPassword().equals(newPWString)) {
				alert.setText("New password must be different than current password");
				return;
			}
		}
		if (visibleFlag)
			HotelEmployeesDB.updateHotelEmployees(user.getUserID(), newName, newPhone, newEmail, newPWString);
		else
			HotelEmployeesDB.updateHotelEmployees(user.getUserID(), newName, newPhone, newEmail, user.getPassword());

		alert.setText("Information changed");
		oldPW.clear();
		newPW.clear();
		newPWConfirm.clear();
		// -------------------------------------- Hotel info
		String hotelAddressString = hotelAddress.getText();
		String hotelNameString = hotelName.getText();
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
		// -------------------------------------- Update hotel info

		new HotelsDB().updateInstance(
				new Hotels(user.getHotelID(), hotelNameString, hotelAddressString, starInt, extensions));
	}

	public void changePassword(ActionEvent event) {
		alertAdjustRoom.setText("");
		alert.setText("");
		alertViewDetails.setText("");
		oldPW.setVisible(!oldPW.isVisible());
		newPW.setVisible(!newPW.isVisible());
		newPWConfirm.setVisible(!newPWConfirm.isVisible());
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
		ReceiptsDB.cancelReciepts(chosenReceipt.getReceiptID());
		ReceiptsDB.updateReceiptStatus(LoginController.getUser());
		ArrayList<Receipts> receipts = ReceiptsDB.queryReceipts(LoginController.getUser());
		if (receipts == null) {
			Label noResult = new Label("You have no receipt");
			receiptsListTable.setPlaceholder(noResult);
			ObservableList<Receipts> tableListNull = FXCollections.observableArrayList();
			receiptsListTable.setItems(tableListNull);
			return;
		}
		ObservableList<Receipts> receiptList = FXCollections.observableArrayList(receipts);
		receiptsListTable.setItems(receiptList);
		alertViewDetails.setText("Booking cancelled");
	}

	public void viewDetails(ActionEvent event) throws SQLException {
		alertAdjustRoom.setText("");
		alert.setText("");
		alertViewDetails.setText("");
		Receipts chosenReceipt = receiptsListTable.getSelectionModel().getSelectedItem();
		if (chosenReceipt == null) {
			alertViewDetails.setText("Choose a room");
			return;
		}
		Receipts chosenReceipts = (Receipts) (new ReceiptsDB().queryInstance(chosenReceipt.getReceiptID()));
		// --------------------------------------------------------------
		infoPane.setEffect(new GaussianBlur(20));
		detailPane.setVisible(true);
		detailPane.toFront();
		detailPane.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		detailPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		// --------------------------------------------------------------
		System.out.println("guest ID: " + chosenReceipts.getUserID());
		Users guest = (Users) (new UsersDB().queryInstance(chosenReceipts.getUserID()));
		System.out.print("guest name: ");
		guest.printInfo();
		guestName.setText("Guest's Name: " + guest.getName());
		guestEmail.setText("Guest's Email: " + guest.getEmail());
		guestPhoneNumber.setText("Guest's Phone Number: " + guest.getPhoneNumber());
	}

	public void back(ActionEvent event) {
		alertAdjustRoom.setText("");
		alert.setText("");
		alertViewDetails.setText("");
		detailPane.setVisible(false);
		infoPane.setEffect(null);
	}

	public void signUp(ActionEvent event) {

		new SceneChanging().changeScene(event, "EmployeeSignUp.fxml");
	}

	public void deleteRoom(ActionEvent event) {
		alertAdjustRoom.setText("");
		if (roomsListTable.getSelectionModel().getSelectedItem() == null) {
			alertAdjustRoom.setText("Choose a room to delete");
			return;
		}
		refreshRoom();
	}

	public void addRoom(ActionEvent event) throws SQLException {
		alertAdjustRoom.setText("");
		if (price.getText().trim().isEmpty()) {
			alertAdjustRoom.setText("Insert price of new room");
			return;
		}
		int priceInt = 0;
		try {
			priceInt = Integer.parseInt(price.getText());
		} catch (Exception e) {
			alertAdjustRoom.setText("Price must be a number");
			return;
		}
		Rooms room = new Rooms(0, ((HotelEmployees) LoginController.getUser()).getHotelID(), priceInt);
		new RoomsDB().insertInstance(room);
		refreshRoom();
	}

	public void adjustPrice(ActionEvent event) throws SQLException {
		alertAdjustRoom.setText("");
		Rooms chosenRoom = roomsListTable.getSelectionModel().getSelectedItem();
		if (chosenRoom == null) {
			alertAdjustRoom.setText("Choose a room to adjust");
			return;
		}
		if (price.getText().trim().isEmpty()) {
			alertAdjustRoom.setText("Insert new price of room");
			return;
		}
		int priceInt = 0;
		try {
			priceInt = Integer.parseInt(price.getText());
		} catch (Exception e) {
			alertAdjustRoom.setText("Price must be a number");
			return;
		}
		Rooms room = new Rooms(chosenRoom.getRoomID(), chosenRoom.getHotelID(), priceInt);
		new RoomsDB().updateInstance(room);
		refreshRoom();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// ------------------------------------- User part
		HotelEmployees user = (HotelEmployees) LoginController.getUser();
		accountName.setText("Username: " + user.getUsername());
		name.setText(user.getName());
		email.setText(user.getEmail());
		phone.setText(user.getPhoneNumber());
		// ------------------------------------- Star
		ObservableList<String> starList = FXCollections.observableArrayList();
		for (int i = 0; i < 5; i++) {
			String star = "";
			for (int j = 0; j < i + 1; j++) {
				star = star.concat("* ");
			}
			starList.add(star);
		}
		star.getItems().addAll(starList);
		// ------------------------------------- TableView For Receipt
		try { // update before show it
			ReceiptsDB.updateReceiptStatus(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<Receipts> receipts = null;
		try {
			receipts = ReceiptsDB.queryReceipts(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (receipts == null) {
			Label noResult = new Label("You have no receipt");
			receiptsListTable.setPlaceholder(noResult);
			ObservableList<Receipts> tableListNull = FXCollections.observableArrayList();
			receiptsListTable.setItems(tableListNull);
		} else {
			ObservableList<Receipts> receiptsList = FXCollections.observableArrayList(receipts);
			roomIDColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("roomIDProperty"));
			checkinDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, Date>("checkinDate")); // ("checkinDateProperty")
			checkoutDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("checkoutDateProperty"));
			orderDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("orderDateProperty"));
			receiptStatusColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("statusProperty"));
			receiptIDColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("receiptIDProperty"));
			priceColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("priceProperty"));
			receiptsListTable.setItems(receiptsList);
		}
		// ------------------------------------- TableView For Rooms
		if (user.getRank() == 1) {
			System.out.println("WELCOME RANK: " + user.getRank());
			managerPart.setVisible(true);
			signUp.setVisible(true);
			refreshRoom();
		} else {
			System.out.println("Dont WELCOME RANK: " + user.getRank());
		}
		// ------------------------------------- Filter
		Hotels hotelInfo = null;
		try {
			hotelInfo = (Hotels) new HotelsDB().queryInstance(user.getHotelID());
		} catch (SQLException e) {
			System.out.println("Error getting hotel Info in HotelInfoController");
			e.printStackTrace();
		}
		hotelName.setText(hotelInfo.getName());
		hotelAddress.setText(hotelInfo.getAddress());
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
		// ------------------------------------- Locations

	}

	private void refreshRoom() {
		ArrayList<Rooms> allRooms = null;
		try {
			allRooms = RoomsDB.queryAllRooms(((HotelEmployees) LoginController.getUser()).getHotelID());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (allRooms == null) {
			Label noResult = new Label("You have no room");
			roomsListTable.setPlaceholder(noResult);
			ObservableList<Rooms> tableListNull = FXCollections.observableArrayList();
			roomsListTable.setItems(tableListNull);
			return;
		}
		ObservableList<Rooms> roomsList = FXCollections.observableArrayList(allRooms);
		roomIDInRoomColumn.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("roomID"));
		priceInRoomColumn.setCellValueFactory(new PropertyValueFactory<Rooms, String>("priceProperty"));
		roomsListTable.setItems(roomsList);
	}
}
