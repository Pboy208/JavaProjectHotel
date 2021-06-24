package controller;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.users.Users;
import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginController{
	private static Users user = null;

	public static void setUser(Users user) {
		LoginController.user = user;
	}

	public static Users getUser() {
		return user;
	}

	public static void signOut() {
		user = null;
	}
	
	@FXML
	private ImageView img;

	@FXML
	private Label alertLabel;

	@FXML
	private TextField accountName;

	@FXML
	private PasswordField password;

	@FXML
	private Button signInButton;

	@FXML
	private Button signUpButton;
	
	public void toUnsignedUserPage(ActionEvent event) {
		new SceneChanging().changeScene(event, "UnsignedUserPage.fxml");
	}
	
	public void signInButtonOnAction(ActionEvent event) throws Exception {
		String passwordString = password.getText();
		String accountNameString = accountName.getText();
		
		if(passwordString.trim().isEmpty() || accountNameString.trim().isEmpty()) {
			alertLabel.setText("Please fill all the field");
			return;
		}
		
		user = Users.loginProcedure(accountNameString, passwordString);
		if(user ==null) {
			alertLabel.setText("Wrong user name or password");
			alertLabel.setVisible(true);
			return;
		}
		new SceneChanging().changeScene(event, "Booking.fxml");
		
	}

	public void signUpButtonOnAction(ActionEvent event) throws Exception {
		new SceneChanging().changeScene(event, "SignUp.fxml");
	}
}
