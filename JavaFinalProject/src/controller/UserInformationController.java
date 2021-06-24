package controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.UserInformationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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
import model.library.Functions;
import model.receipts.Receipts;
import model.users.HotelManager;
import model.users.Users;

public class UserInformationController implements Initializable {

	@FXML
	private Button closeConfirmation;
	@FXML
	private Button continueConfirmation;
	@FXML
	private Pane confirmationPane;
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
		new SceneChanging().changeScene(event, "Booking.fxml");
	}

	public void signOut(ActionEvent event) {
		new SceneChanging().changeScene(event, "Login.fxml");
		LoginController.signOut();
	}

	public void saveChange(ActionEvent event) throws SQLException {
		clearAlerts();
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
		user.setPhone(newPhone);
		user.setEmail(newEmail);

		if (visibleFlag) {
			String oldPwString = oldPW.getText();
			String newPWString = newPW.getText();
			String newPWConfirmString = newPWConfirm.getText();
			
			if (oldPwString.equals(newPWString)) {
				alert.setText("New password must be different than current password");
				return;
			}
			
			if (!newPWString.equals(newPWConfirmString)) {
				alert.setText("Password confirmation is not match");
				return;
			}
			
			user.setPassword(oldPwString);
			
			if (user.checkPassword() == -1) {
				alert.setText("Current password is incorrect");
				return;
			}
			user.setPassword(newPWString);
		}
		
		openConfirmationPane();
		//Dealing async
		continueConfirmation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					user.updateInstance();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				LoginController.setUser(user);
				alert.setText("Information changed");;
				closeConfirmationPane();
			}
		});
		
	}

	public void changePassword(ActionEvent event) {
		clearAlerts();
		oldPW.setVisible(!oldPW.isVisible());
		newPW.setVisible(!newPW.isVisible());
		newPWConfirm.setVisible(!newPWConfirm.isVisible());
	}

	public void cancelReceipt(ActionEvent event) throws SQLException {
		clearAlerts();

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
		
		openConfirmationPane();
		//Dealing async
		continueConfirmation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Receipts.cancelReceiptUser(chosenReceipt.getReceiptID());
					ArrayList<Receipts> receipts = Receipts.queryReceiptsForUser();
					ObservableList<Receipts> roomsList = FXCollections.observableArrayList(receipts);
					receiptsListTable.setItems(roomsList);
					viewDetailsLabel.setText("Booking cancelled");
					closeConfirmationPane();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	public void back(ActionEvent event) {
		clearAlerts();
		detailPane.setVisible(false);
		infoPane.setEffect(null);
	}

	public void viewDetails(ActionEvent event) throws SQLException {
		clearAlerts();
		// -------------------------------------------------------------- Pop up
				infoPane.setEffect(new GaussianBlur(20));
				detailPane.setVisible(true);
				detailPane.toFront();
				detailPane.setBorder(new Border(
						new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
				detailPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		// --------------------------------------------------------------
		alert.setText("");
		viewDetailsLabel.setText("");
		Receipts chosenReceipt = receiptsListTable.getSelectionModel().getSelectedItem();
		if (chosenReceipt == null) {
			viewDetailsLabel.setText("Choose a receipt");
			return;
		}	
		
		HotelManager manager = new HotelManager();
		manager.queryInstance(chosenReceipt.getReceiptID());
		
		try {
			employeeName.setText("Manager's Name: " + manager.getName());
			employeeEmail.setText("Manager's Email: " + manager.getEmail());
			employeePhoneNumber.setText("Manager's Phone Number: " + manager.getPhone());
		} catch (Exception e) {
			employeeName.setText("Manager's Name: N/A");
			employeeEmail.setText("Manager's Email: N/A");
			employeePhoneNumber.setText("Manager's Phone Number: N/A");
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clearAlerts();
		Users user = LoginController.getUser();
		accountName.setText("Username: " + user.getUsername());
		name.setText(user.getName());
		email.setText(user.getEmail());
		phone.setText(user.getPhone());
		// -------------------------------------------------------
		reloadPage();
		// -------------------------------------------------------
		closeConfirmation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				closeConfirmationPane();
			}
		});
	}
	
	private void reloadPage() {
		clearAlerts();
		try {
			Receipts.updateReceiptStatusForUser();
		} catch (SQLException e) {
			System.out.println("This user don't have any receipt to udpate status");
			e.printStackTrace();
		}
		System.out.println("Done update receipt list");
		ArrayList<Receipts> receipts = null;
		try {
			receipts = Receipts.queryReceiptsForUser();
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
			orderDateColumn.setCellValueFactory(new PropertyValueFactory<Receipts, Date>("bookingDate"));
			receiptStatusColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("statusProperty"));
			receiptIDColumn.setCellValueFactory(new PropertyValueFactory<Receipts, Integer>("receiptID"));
			priceColumn.setCellValueFactory(new PropertyValueFactory<Receipts, String>("payment"));
			receiptsListTable.setItems(receiptsList);
		}
	}
	
	private void clearAlerts() {
		alert.setText("");
		viewDetailsLabel.setText("");
	}
	
	private void openConfirmationPane() {
		clearAlerts();
		infoPane.setEffect(new GaussianBlur(20));
		confirmationPane.setVisible(true);
		confirmationPane.toFront();
		confirmationPane.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		confirmationPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	private void closeConfirmationPane() {
		confirmationPane.setVisible(false);
		infoPane.setEffect(null);
	}
}
