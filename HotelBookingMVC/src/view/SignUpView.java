package view;

import java.sql.SQLException;
import controller.SignUpController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SignUpView {
	@FXML
	private Label beginLabel;
	@FXML
	private Label alertLabel;

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
	public void back(ActionEvent event) {
		new SceneChanging().changeScene(event, "Login.fxml");
	}

	public void typeClients(ActionEvent event) throws SQLException {
		alertLabel.setVisible(false);
		beginLabel.setVisible(false);
		paneH.setVisible(false);
		paneC.setVisible(true);
	}

	public void typeHotelManagers(ActionEvent event) {
		alertLabel.setVisible(false);
		beginLabel.setVisible(false);
		paneH.setVisible(true);
		paneC.setVisible(false);
	}

	public void signUpClient(ActionEvent event) throws SQLException {
		alertLabel.setVisible(false);
		if (nameC.getText().trim().isEmpty() || phoneC.getText().trim().isEmpty() || emailC.getText().trim().isEmpty()
				|| pwC.getText().trim().isEmpty() || pwConfirmationC.getText().trim().isEmpty()
				|| accountNameC.getText().trim().isEmpty()) {
			alertLabel.setVisible(true);
			alertLabel.setText("Some fields are missing");
			return;
		}

		String name = nameC.getText();
		String phone = phoneC.getText();
		String email = emailC.getText();

		String password = pwC.getText();
		String passwordConfirm = pwConfirmationC.getText();
		String accountName = accountNameC.getText();

		alertLabel = SignUpController.signUpClient(alertLabel, name, phone, email, password, passwordConfirm,
				accountName);

		accountNameC.clear();
		phoneC.clear();
		emailC.clear();
		pwC.clear();
		pwConfirmationC.clear();
		nameC.clear();
	}

	public void signUpHotelManager(ActionEvent event) throws SQLException {
		alertLabel.setVisible(false);

		if (nameH.getText().trim().isEmpty() || phoneH.getText().trim().isEmpty() || emailH.getText().trim().isEmpty()
				|| pwH.getText().trim().isEmpty() || pwConfirmationH.getText().trim().isEmpty()
				|| accountNameH.getText().trim().isEmpty() || hotelNameH.getText().trim().isEmpty()
				|| hotelAddressH.getText().trim().isEmpty()) {
			alertLabel.setVisible(true);
			alertLabel.setText("Some fields are missing");
			return;
		}

		String name = nameH.getText();
		String phone = phoneH.getText();
		String email = emailH.getText();

		String password = pwH.getText();
		String passwordConfirm = pwConfirmationH.getText();
		String accountName = accountNameH.getText();

		String hotelName = hotelNameH.getText();
		String hotelAddress = hotelAddressH.getText();

		alertLabel = SignUpController.signUpHotelManager(alertLabel, name, phone, email, password, passwordConfirm,
				accountName, hotelName, hotelAddress);

		accountNameC.clear();
		phoneC.clear();
		emailC.clear();
		pwC.clear();
		pwConfirmationC.clear();
		nameC.clear();
	}

}
