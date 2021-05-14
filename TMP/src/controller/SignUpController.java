package controller;

import java.sql.SQLException;

import database.AccountsDB;
import database.ClientDB;
import database.HotelDB;
import database.HotelEmployeesDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SignUpController {

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

	@FXML
	private Label beginLabel;
	@FXML
	private Label alertMissing;
	@FXML
	private Label alertPW;
	@FXML
	private Label alertExistAccount;
	@FXML
	private Label alertExistPhone;
	@FXML
	private Label succesfullySignUp;
	@FXML
	private Label alertExistHotelName;

	// ----------------------------------- C for Clients
	@FXML
	private Pane paneC;
	@FXML
	private TextField accountNameC;
	@FXML
	private TextField nameC;
	@FXML
	private TextField emailC;
	@FXML
	private TextField phoneC;
	@FXML
	private PasswordField pwC;
	@FXML
	private PasswordField pwConfirmationC;
	// ----------------------------------- H for Hotel managers
	@FXML
	private Pane paneH;
	@FXML
	private TextField accountNameH;
	@FXML
	private TextField nameH;
	@FXML
	private TextField emailH;
	@FXML
	private TextField phoneH;
	@FXML
	private TextField hotelNameH;
	@FXML
	private TextField hotelAddressH;
	@FXML
	private PasswordField pwH;
	@FXML
	private PasswordField pwConfirmationH;

	// -----------------------------------


	public static Label signUpClient(Label alertLabel,String name,String phone,String email,String password,String passwordConfirm,String accountName) throws SQLException {
		if (AccountsDB.checkExistAccount(accountName, phone) == 1) {
			alertLabel.setText("Account already exists");
			alertLabel.setVisible(true);
			return alertLabel;
		} else if (AccountsDB.checkExistAccount(accountName, phone) == 2) {
			alertLabel.setText("Phone is currently being used in other account ");
			alertLabel.setVisible(true);
			return alertLabel;
		}

		if (!password.equals(passwordConfirm)) {
			alertLabel.setText("Password confirmation is not match");
			alertLabel.setVisible(true);
			return alertLabel;
		}

		ClientDB.insertClients(name, phone, email, accountName, passwordConfirm);

		alertLabel.setText("Your account is ready");
		alertLabel.setVisible(true);
		return alertLabel;
	}

	public static Label signUpHotelManager(Label alertLabel,String name,String phone,String email,
			String password,String passwordConfirm,String accountName,String hotelName,String hotelAddress) throws SQLException {

		if (AccountsDB.checkExistAccount(accountName, phone) == 1) {
			alertLabel.setText("Account already exists");
			alertLabel.setVisible(true);
			return alertLabel;
		} else if (AccountsDB.checkExistAccount(accountName, phone) == 2) {
			alertLabel.setText("Phone is currently being used in other account ");
			alertLabel.setVisible(true);
			return alertLabel;
		}

		if (!password.equals(passwordConfirm)) { }

		// create hotel first
		int hotelID = HotelDB.insertHotel(hotelName, hotelAddress);
		if (hotelID == -1) {
			alertLabel.setText("Hotel already exists in the system");
			alertLabel.setVisible(true);
			return alertLabel;
		}
		// create employees with given hotel_id
		HotelEmployeesDB.insertHotelEmployees(hotelID, name, phone, email, accountName, passwordConfirm);

		// After Sign Up
		alertLabel.setText("Your account is ready");
		alertLabel.setVisible(true);
		return alertLabel;
	}

}
