package controller;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import model.database.AccountsDB;
import model.database.HotelEmployeesDB;
import model.database.UsersDB;
import model.users.HotelEmployees;
import model.users.Users;

import java.net.URL;
import java.util.ResourceBundle;

import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginController{
	private static Users user = null;

	public static void setUser(String name, String phone, String email, String password) {
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setPhoneNumber(phone);
	}

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
		
		user= HotelEmployeesDB.queryEmployeeInfo(userID);
		if(user==null) {
			System.out.println("welcome user");
			user=(Users) new UsersDB().queryInstance(userID);
		}
		user.setPassword(passwordString);
		user.setUsername(accountNameString);
		new SceneChanging().changeScene(event, "Filter.fxml");
		
	}

	public void signUpButtonOnAction(ActionEvent event) throws Exception {
		new SceneChanging().changeScene(event, "SignUp.fxml");
	}
}
