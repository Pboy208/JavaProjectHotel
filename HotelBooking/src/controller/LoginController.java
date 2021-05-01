package controller;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import database.CheckPasswordDB;
import database.ClientDB;
import database.HotelEmployeesDB;
import javafx.stage.Stage;
import user.User;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginController {
	
	
	private static User user= null;
	
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
	private Label lblMessage;
	
	@FXML
	private TextField txtUserName;
	
	@FXML 
	private PasswordField txtPassword;
	
	@FXML
	private Button signInButton;
	
	@FXML
	private Button signUpButton;
	
	@FXML
	private CheckBox clientType;
	
	@FXML
	private CheckBox employeesType;
	
	
	@FXML
	public void signInButtonOnAction(ActionEvent event) throws Exception{
		String password=txtPassword.getText();
		String username=txtUserName.getText();
		int user_id = CheckPasswordDB.checkPassword(username, password);
		if(!clientType.isSelected() && !employeesType.isSelected())
		{
			lblMessage.setText("Please choose a user type");
			return;
		}
		if(user_id==-1) {
			lblMessage.setText("Wrong user name or password");
			return;
		}
		
		if(clientType.isSelected()) {
			user=ClientDB.queryClientInfo(user_id);
			System.out.println("Login succesfully!");
		}
		else if(employeesType.isSelected()) {
			user=HotelEmployeesDB.queryEmployeeInfo(user_id);
			System.out.println("Login succesfully!");
		}
		user.printInfo();
		changeScene(event,"Filter.fxml");
		System.out.println("ChangedScene");
	}
	
	@FXML 
	public void signUpButtonOnAction(ActionEvent e) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/application/SignUp.fxml"));
		Stage window = (Stage)signUpButton.getScene().getWindow();
		Scene scene = new Scene(root, 500, 400);
		window.setScene(scene);
	}
}
