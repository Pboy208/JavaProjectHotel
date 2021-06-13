package controller;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.database.AccountsDB;
import model.users.HotelEmployees;
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

	public void signInButtonOnAction(ActionEvent event) throws Exception {
		String passwordString = password.getText();
		String accountNameString = accountName.getText();
		
		if(passwordString.trim().isEmpty() || accountNameString.trim().isEmpty()) {
			alertLabel.setText("Please fill all the field");
			return;
		}
		
		int userID = AccountsDB.checkPassword(accountNameString, passwordString);
		if (userID == -1) {
			alertLabel.setText("Wrong user name or password");
			alertLabel.setVisible(true);
			return;
		}
		
		user = (HotelEmployees) new HotelEmployees().queryInstance(userID);
		if(user==null) {
			//case Just a normal user
			user=(Users) new Users().queryInstance(userID);
		}
		user.setPassword(passwordString);
		user.setUsername(accountNameString);
		new SceneChanging().changeScene(event, "Filter.fxml");
		
	}

	public void signUpButtonOnAction(ActionEvent event) throws Exception {
		new SceneChanging().changeScene(event, "SignUp.fxml");
	}
}
