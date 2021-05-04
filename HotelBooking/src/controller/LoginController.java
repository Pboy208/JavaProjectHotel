package controller;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import database.CheckPasswordDB;
import database.ClientDB;
import database.HotelEmployeesDB;
import javafx.stage.Stage;
import user.User;
import javafx.scene.Node;
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
	
	
	public static void signOut() {
		user = null;
	}
	
	@FXML
	public void signInButtonOnAction(ActionEvent event) throws Exception{
		String password=txtPassword.getText();
		String username=txtUserName.getText();
		int user_id = 0;
		if(!clientType.isSelected() && !employeesType.isSelected())
		{
			lblMessage.setText("Please choose a user type");
			return;
		}
		
		
		if(clientType.isSelected()) {
			user_id=CheckPasswordDB.checkPasswordClients(username, password);
			if(user_id==-1) {
				lblMessage.setText("Wrong user name or password");
				return;
			}
			user=ClientDB.queryClientInfo(user_id);
			user.setUsername(username);
			user.setPassword(password);
			System.out.println("Login succesfully client!");
			user.printInfo();
			changeScene(event,"Filter.fxml");
		}
		else if(employeesType.isSelected()) {
			user_id=CheckPasswordDB.checkPasswordHotels(username, password);
			if(user_id==-1) {
				lblMessage.setText("Wrong user name or password");
				return;
			}
			user=HotelEmployeesDB.queryEmployeeInfo(user_id);
			user.setUsername(username);
			user.setPassword(password);
			System.out.println("Login succesfully manager!");
			user.printInfo();
			changeScene(event,"HotelPage.fxml");
			
		}

	}
	
	public static User getUser() {
		return user;
	}
	
	public static void setUser(String name,String phone,String email,String password) {
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setPhoneNumber(phone);
	}
	
	@FXML 
	public void signUpButtonOnAction(ActionEvent event) throws Exception{
		changeScene(event,"SignUp.fxml");
	}
}
