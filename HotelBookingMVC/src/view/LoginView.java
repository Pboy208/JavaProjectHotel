package view;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;
import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginView implements Initializable {
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
	private ToggleButton usersType;

	@FXML
	private ToggleButton employeesType;

	public void signInButtonOnAction(ActionEvent event) throws Exception {
		String passwordString = password.getText();
		String accountNameString = accountName.getText();
		if (!usersType.isSelected() && !employeesType.isSelected()) {
			alertLabel.setText("Please choose a user type");
			return;
		}

		if (usersType.isSelected()) {
			alertLabel = LoginController.signInButtonOnAction(alertLabel, accountNameString, passwordString, 1);
			if (alertLabel.isVisible() == false)
				new SceneChanging().changeScene(event, "Filter.fxml");
		} else {
			alertLabel = LoginController.signInButtonOnAction(alertLabel, accountNameString, passwordString, 2);
			if (alertLabel.isVisible() == false)
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
