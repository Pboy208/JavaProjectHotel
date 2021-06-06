package controller;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import model.database.AccountsDB;
import model.database.HotelEmployeesDB;
import model.database.UsersDB;
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

public class LoginController implements Initializable {
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

	@FXML
	private ToggleButton usersType;

	@FXML
	private ToggleButton employeesType;

	public void signInButtonOnAction(ActionEvent event) throws Exception {
		String passwordString = password.getText();
		String accountNameString = accountName.getText();
		
		if(passwordString.trim().isEmpty() || accountNameString.trim().isEmpty()) {
			alertLabel.setText("Please fill all the field");
			return;
		}
		
		if (!usersType.isSelected() && !employeesType.isSelected()) {
			alertLabel.setText("Please choose a user type");
			return;
		}

		if (usersType.isSelected()) {
			int userID = AccountsDB.checkPassword(accountNameString, passwordString);
			if (userID == -1) {
				alertLabel.setText("Wrong user name or password");
				alertLabel.setVisible(true);
				return;
			}
			if(HotelEmployeesDB.queryEmployeeInfo(userID) != null) {
				alertLabel.setText("Wrong user name or password");
				alertLabel.setVisible(true);
				return;
			}
			user = (Users) new UsersDB().queryInstance(userID);
			user.setPassword(passwordString);
			user.setUsername(accountNameString);
			new SceneChanging().changeScene(event, "Filter.fxml");
		} 
		else {
			int userID = AccountsDB.checkPassword(accountNameString, passwordString);
			if (userID == -1) {
				alertLabel.setText("Wrong user name or password");
				alertLabel.setVisible(true);
				return;
			}
			user = HotelEmployeesDB.queryEmployeeInfo(userID);
			if(user ==null) {
				alertLabel.setText("Wrong user name or password");
				alertLabel.setVisible(true);
				return;
			}
			user.setUsername(accountNameString);
			user.setPassword(passwordString);
			new SceneChanging().changeScene(event, "HotelInfo.fxml");
		}
	}

	public void signUpButtonOnAction(ActionEvent event) throws Exception {
		new SceneChanging().changeScene(event, "SignUp.fxml");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ToggleGroup typeGroup = new ToggleGroup();
		usersType.setToggleGroup(typeGroup);
		employeesType.setToggleGroup(typeGroup);
	}

}
