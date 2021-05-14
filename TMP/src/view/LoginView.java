package view;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

import database.AccountsDB;
import database.ClientDB;
import database.HotelEmployeesDB;
import javafx.stage.Stage;
import user.User;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginView implements Initializable {
	private static User user = null;

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
	private Pane loginPane;
	
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
	private ToggleButton clientType;

	@FXML
	private ToggleButton employeesType;

	public static User getUser() {
		return user;
	}

	public static void setUser(String name, String phone, String email, String password) {
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setPhoneNumber(phone);
	}

	public static void signOut() {
		user = null;
	}

	public void signInButtonOnAction(ActionEvent event) throws Exception {
		System.out.println("event source: " + event.getSource());
		String passwordString = password.getText();
		String accountNameString = accountName.getText();
		int user_id = 0;

		if (!clientType.isSelected() && !employeesType.isSelected()) {
			alertLabel.setText("Please choose a user type");
			return;
		}

		if (clientType.isSelected()) {
			user_id = AccountsDB.checkPassword(accountNameString, passwordString);
			if (user_id == -1) {
				alertLabel.setText("Wrong user name or password");
				return;
			}
			user = ClientDB.queryClientInfo(user_id);
			user.setUsername(accountNameString);
			user.setPassword(passwordString);
			System.out.println("Login succesfully client!");
			user.printInfo();
			changeScene(event, "Filter.fxml");
		} else if (employeesType.isSelected()) {
			user_id = AccountsDB.checkPassword(accountNameString, passwordString);
			if (user_id == -1) {
				alertLabel.setText("Wrong user name or password");
				return;
			}
			user = HotelEmployeesDB.queryEmployeeInfo(user_id);
			user.setUsername(accountNameString);
			user.setPassword(passwordString);
			System.out.println("Login succesfully manager!");
			user.printInfo();
			changeScene(event, "HotelInfo.fxml");
		}

	}

	public void signUpButtonOnAction(ActionEvent event) throws Exception {
		//changeScene(event, "SignUp.fxml");
		new SceneChanging().changeScene(event,"SignUp.fxml");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		user = null;
		ToggleGroup typeGroup = new ToggleGroup();
		clientType.setToggleGroup(typeGroup);
		employeesType.setToggleGroup(typeGroup);
		//-------------------------------------------------------------
		
		//-------------------------------------------------------------
	}

}
