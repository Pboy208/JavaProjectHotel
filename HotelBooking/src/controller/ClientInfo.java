package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import database.BookAndCancelDB;
import database.CheckPasswordDB;
import database.ClientDB;
import database.HotelEmployeesDB;
import database.ReceiptDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import orders.Receipts;
import user.Clients;
import user.HotelEmployees;

public class ClientInfo implements Initializable {

	public void changeScene(ActionEvent event, String source) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource(source));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			windowStage.setScene(scene);
			windowStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//-----------------------------------------------------------------------
	@FXML
	private Pane infoPane;
	@FXML
	private Pane detailPane;
	@FXML
	private Label employeeName;
	@FXML
	private Label employeeEmail;
	@FXML
	private Label employeePhoneNumber;

	//-----------------------------------------------------------------------
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
	@FXML
	private Label viewDetailsLabel;
	//-----------------------------------------------------------------------
	@FXML
	private TableView<Receipts> receiptsListTable;
	@FXML
	private TableColumn<Receipts, String> hotelNameColumn;
	@FXML
	private TableColumn<Receipts, String> hotelAddressColumn;
	@FXML
	private TableColumn<Receipts, String> checkinDateColumn;
	@FXML
	private TableColumn<Receipts, String> checkoutDateColumn;
	@FXML
	private TableColumn<Receipts, String> orderDateColumn;
	@FXML
	private TableColumn<Receipts, String> priceColumn;
	@FXML
	private TableColumn<Receipts, String> receiptStatusColumn;
	@FXML
	private TableColumn<Receipts, String> receiptIDColumn;
	//-----------------------------------------------------------------------
	public void backToFilter(ActionEvent event) {
		changeScene(event, "Filter.fxml");
	}

	public void signOut(ActionEvent event) {
		changeScene(event, "Login.fxml");
		LoginController.signOut();
	}

	public void saveChange(ActionEvent event) throws SQLException {

		alert.setText("");

		Clients user = (Clients) LoginController.getUser();
		boolean visibleFlag = false;
		if (oldPW.isVisible() == true) {
			visibleFlag = true;
		}

		if (name.getText().trim().isEmpty() || email.getText().trim().isEmpty() || phone.getText().trim().isEmpty()) {
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

		String oldPwString = null;
		String newPWString = null;
		String newPWConfirmString = null;

		if (visibleFlag) {
			oldPwString = oldPW.getText();
			newPWString = newPW.getText();
			newPWConfirmString = newPWConfirm.getText();
			if (CheckPasswordDB.checkPasswordClients(user.getUsername(), oldPwString) == -1) {
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
		ClientDB.updateClients(user.getId(), newName, newPhone, newEmail, newPWString);
		alert.setText("Information changed");
	}

	public void changePassword(ActionEvent event) {
		oldPW.setVisible(!oldPW.isVisible());
		newPW.setVisible(!newPW.isVisible());
		newPWConfirm.setVisible(!newPWConfirm.isVisible());
	}

	public void cancelReceipt(ActionEvent event) throws SQLException {
		alert.setText("");
		viewDetailsLabel.setText("");
		
		Receipts chosenReceipt = receiptsListTable.getSelectionModel().getSelectedItem();
		if (chosenReceipt == null) {
			viewDetailsLabel.setText("Choose a receipt");
			return;
		}
		if (chosenReceipt.getStatus() == -1) {
			viewDetailsLabel.setText("The receipt has already been finished");
			return;
		}
		if (chosenReceipt.getStatus() == 1) {
			viewDetailsLabel.setText("Can not cancel this receipt");
			return;
		}
		if (chosenReceipt.getStatus() == 2) {
			viewDetailsLabel.setText("The receipt has already been cancelled");
			return;
		}
		BookAndCancelDB.cancelReciepts(chosenReceipt.getReceiptID());
		ReceiptDB.updateReceiptStatusClients(LoginController.getUser().getId());
		ArrayList<Receipts> receipts = ReceiptDB.queryReceiptsForClient(LoginController.getUser().getId());
		if (receipts == null) {
			Label noResult = new Label("You have no room");
			receiptsListTable.setPlaceholder(noResult);
			ObservableList<Receipts> tableListNull = FXCollections.observableArrayList();
			receiptsListTable.setItems(tableListNull);
			return;
		}
		ObservableList<Receipts> roomsList = FXCollections.observableArrayList(receipts);
		receiptsListTable.setItems(roomsList);
		viewDetailsLabel.setText("Booking cancelled");
	}
	
	public void back(ActionEvent event) {
		alert.setText("");
		viewDetailsLabel.setText("");
		detailPane.setVisible(false);
		infoPane.setEffect(null);
	}
	
	public void viewDetails(ActionEvent event) throws SQLException {
		alert.setText("");
		viewDetailsLabel.setText("");
		Receipts chosenReceipt = receiptsListTable.getSelectionModel().getSelectedItem();
		if (chosenReceipt == null) {
			viewDetailsLabel.setText("Choose a receipt");
			return;
		}
		Receipts chosenReceipts= ReceiptDB.queryRooms(chosenReceipt.getReceiptID());

		chosenReceipt.printInfo();
		//--------------------------------------------------------------
		infoPane.setEffect(new GaussianBlur(20));
		detailPane.setVisible(true);
		detailPane.toFront();
		detailPane.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		detailPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		//--------------------------------------------------------------
		System.out.println("guest ID: "+chosenReceipts.getUserID());
		HotelEmployees employee = HotelEmployeesDB.queryEmployeeInfoByHotelID(chosenReceipts.getHotelID());
		System.out.print("guest name: " );
		employee.printInfo();
		employeeName.setText("Employee's Name: "+employee.getName());
		employeeEmail.setText("Employee's Email: "+employee.getEmail());
		employeePhoneNumber.setText("Employee's Phone Number: "+employee.getPhoneNumber());
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		alert.setText("");
		Clients user = (Clients) LoginController.getUser();
		user.printInfo();
		accountName.setText("Username: " + user.getUsername());
		name.setText(user.getName());
		email.setText(user.getEmail());
		phone.setText(user.getPhoneNumber());
		//-------------------------------------------------------
		try {
			ReceiptDB.updateReceiptStatusClients(user.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Receipts> receipts = null;
		try {
			receipts = ReceiptDB.queryReceiptsForClient(user.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (receipts == null) {
			Label noResult = new Label("You have no receipt");
			receiptsListTable.setPlaceholder(noResult);
			ObservableList<Receipts> tableListNull = FXCollections.observableArrayList();
			receiptsListTable.setItems(tableListNull);
			return;
		}
		
		ObservableList<Receipts> receiptsList = FXCollections.observableArrayList(receipts);
		hotelAddressColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("hotelAddressProperty"));
		hotelNameColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("hotelNameProperty"));
		checkinDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("checkinDateProperty"));
		checkoutDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("checkoutDateProperty"));
		orderDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("orderDateProperty"));
		receiptStatusColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("statusProperty"));
		receiptIDColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("receiptIDProperty"));
		receiptsListTable.setItems(receiptsList);
		System.out.println("Done");
	}

}
