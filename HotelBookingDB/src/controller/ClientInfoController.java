package controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.ClientInfoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
import model.library.Functions;
import model.receipts.Receipts;
import model.users.HotelEmployees;
import model.users.Users;

public class ClientInfoController implements Initializable {

	// -----------------------------------------------------------------------
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

	// -----------------------------------------------------------------------
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
	// -----------------------------------------------------------------------
	@FXML
	private TableView<Receipts> receiptsListTable;
	@FXML
	private TableColumn<Receipts, String> hotelNameColumn;
	@FXML
	private TableColumn<Receipts, String> hotelAddressColumn;
	@FXML
	private TableColumn<Receipts, Date> checkinDateColumn;
	@FXML
	private TableColumn<Receipts, Date> checkoutDateColumn;
	@FXML
	private TableColumn<Receipts, Date> orderDateColumn;
	@FXML
	private TableColumn<Receipts, String> priceColumn;
	@FXML
	private TableColumn<Receipts, String> receiptStatusColumn;
	@FXML
	private TableColumn<Receipts, Integer> receiptIDColumn;

	// -----------------------------------------------------------------------
	public void backToFilter(ActionEvent event) {
		new SceneChanging().changeScene(event, "Filter.fxml");
	}

	public void signOut(ActionEvent event) {
		new SceneChanging().changeScene(event, "Login.fxml");
		LoginController.signOut();
	}

	public void saveChange(ActionEvent event) throws SQLException {
		alert.setText("");
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
		
		Users user = LoginController.getUser();
		user.setName(newName);
		user.setPhoneNumber(newPhone);
		user.setEmail(newEmail);

		if (visibleFlag) {
			String oldPwString = oldPW.getText();
			String newPWString = newPW.getText();
			String newPWConfirmString = newPWConfirm.getText();
			if (AccountsDB.checkPassword(user.getUsername(), oldPwString) == -1) {
				alert.setText("Current password is incorrect");
				return;
			}
			if (!newPWString.equals(newPWConfirmString)) {
				alert.setText("Password confirmation is not match");
				return;
			}
			if (user.getPassword().equals(newPWString)) {
				alert.setText("New password must be different than current password");
				return;
			}
			user.setPassword(newPWString);
		}
		new Users().updateInstance(user);
		alert.setText("Information changed");
	}

	public void changePassword(ActionEvent event) {
		alert.setText("");
		viewDetailsLabel.setText("");
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

		Receipts.cancelReciepts(chosenReceipt.getReceiptID());
		
		Receipts.updateReceiptStatusForUser(LoginController.getUser());
		
		ArrayList<Receipts> receipts = Receipts.queryReceiptsForUser(LoginController.getUser());
		
		if (receipts == null) {
			Label noResult = new Label("You have no room");
			receiptsListTable.setPlaceholder(noResult);
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
		Receipts chosenReceipts = (Receipts) new Receipts().queryInstance(chosenReceipt.getReceiptID());
		// --------------------------------------------------------------
		infoPane.setEffect(new GaussianBlur(20));
		detailPane.setVisible(true);
		detailPane.toFront();
		detailPane.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		detailPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		// --------------------------------------------------------------
		HotelEmployees employee = HotelEmployees.queryEmployeeInfoByHotelID(chosenReceipts.getHotelID());
		try {
			employeeName.setText("Employee's Name: " + employee.getName());
			employeeEmail.setText("Employee's Email: " + employee.getEmail());
			employeePhoneNumber.setText("Employee's Phone Number: " + employee.getPhoneNumber());
		} catch (Exception e) {
			employeeName.setText("Employee's Name: N/A");
			employeeEmail.setText("Employee's Email: N/A");
			employeePhoneNumber.setText("Employee's Phone Number: N/A");
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		alert.setText("");
		Users user = LoginController.getUser();
		accountName.setText("Username: " + user.getUsername());
		name.setText(user.getName());
		email.setText(user.getEmail());
		phone.setText(user.getPhoneNumber());
		// -------------------------------------------------------
		reloadPage();

	}

	private void reloadPage() {
		Users user = LoginController.getUser();
		try {
			Receipts.updateReceiptStatusForUser(user);
		} catch (SQLException e) {
			System.out.println("This user don't have any receipt to udpate status");
			e.printStackTrace();
			System.out.println("damn it");
		}
		System.out.println("Done update receipt list");
		ArrayList<Receipts> receipts = null;
		try {
			receipts = Receipts.queryReceiptsForUser(user);
		} catch (SQLException e) {
			System.out.println("This user don't have any receipt to query out");
			e.printStackTrace();
		}
		if (receipts == null) {
			Label noResult = new Label("You have no receipt");
			receiptsListTable.setPlaceholder(noResult);
		} else {
			ObservableList<Receipts> receiptsList = FXCollections.observableArrayList(receipts);
			hotelAddressColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("hotelAddressProperty"));
			hotelNameColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("hotelNameProperty"));
			checkinDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, Date>("checkinDate"));
			checkoutDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, Date>("checkoutDate"));
			orderDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, Date>("orderDate"));
			receiptStatusColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("statusProperty"));
			receiptIDColumn.setCellValueFactory(new PropertyValueFactory<Receipts, Integer>("receiptID"));
			priceColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("price"));
			receiptsListTable.setItems(receiptsList);
		}
	}
}
