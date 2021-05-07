package controller;


import java.sql.SQLException;

import javax.security.auth.login.FailedLoginException;

import database.CheckPasswordDB;
import database.ClientDB;
import database.HotelDB;
import database.HotelEmployeesDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user.Clients;

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
	
	//----------------------------------- C for Clients
	@FXML
	private Pane paneC;
	@FXML
	private TextField usernameC;
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
	//----------------------------------- H for Hotel managers
	@FXML
	private Pane paneH;
	@FXML
	private TextField usernameH;
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
	//-----------------------------------
	public void back(ActionEvent event) {
		changeScene(event,"Login.fxml");
	}

	public void typeClients(ActionEvent event) throws SQLException {
		alertExistAccount.setVisible(false);
		alertMissing.setVisible(false);
		alertPW.setVisible(false);
		succesfullySignUp.setVisible(false);
		beginLabel.setVisible(false);
		paneH.setVisible(false);
		paneC.setVisible(true);
	}
	
	public void typeHotelManagers(ActionEvent event) {
		alertExistAccount.setVisible(false);
		alertMissing.setVisible(false);
		alertPW.setVisible(false);
		succesfullySignUp.setVisible(false);
		beginLabel.setVisible(false);
		paneH.setVisible(true);
		paneC.setVisible(false);
	}
	
	public void signUpClient(ActionEvent event) throws SQLException {
		alertExistAccount.setVisible(false);
		alertMissing.setVisible(false);
		alertPW.setVisible(false);
		succesfullySignUp.setVisible(false);
		
		if(nameC.getText().trim().isEmpty() ||
		   phoneC.getText().trim().isEmpty() ||
		   emailC.getText().trim().isEmpty() ||
		   pwC.getText().trim().isEmpty() ||
		   pwConfirmationC.getText().trim().isEmpty() ||
		   usernameC.getText().trim().isEmpty()
		   ) 
		{
			alertMissing.setVisible(true);
			return;
		}
		
		String user_Name = nameC.getText();
		String phone=phoneC.getText();
		String email= emailC.getText();
		
		String password = pwC.getText();
		String passwordConfirm = pwConfirmationC.getText();
		String username = usernameC.getText();
		
		if(ClientDB.checkExistAccount(username,phone)==1) {
			alertExistAccount.setVisible(true);
			return;
		}
		else if (ClientDB.checkExistAccount(username,phone)==2) {
			alertExistPhone.setVisible(true);
			return;
		}
		
		if(!password.equals(passwordConfirm)) {
			alertPW.setVisible(true);
			return;
		}
		

		ClientDB.insertClients(user_Name, phone, email, username, passwordConfirm);
//		Clients tmp=ClientDB.queryClientInfo(CheckPasswordDB.checkPasswordClients(username, passwordConfirm));
//		tmp.printInfo();
		
		//After Sign Up
		succesfullySignUp.setVisible(true);
		usernameC.clear();
		phoneC.clear();
		emailC.clear();
		pwC.clear();
		pwConfirmationC.clear();
		nameC.clear();		
	}
	
	public void signUpHotelManager(ActionEvent event) throws SQLException {
		alertExistAccount.setVisible(false);
		alertMissing.setVisible(false);
		alertPW.setVisible(false);
		succesfullySignUp.setVisible(false);
		
		if(nameH.getText().trim().isEmpty() ||
				   phoneH.getText().trim().isEmpty() ||
				   emailH.getText().trim().isEmpty() ||
				   pwH.getText().trim().isEmpty() ||
				   pwConfirmationH.getText().trim().isEmpty() ||
				   usernameH.getText().trim().isEmpty() ||
				   hotelNameH.getText().trim().isEmpty() ||
				   hotelAddressH.getText().trim().isEmpty()
				   ) 
		{
			alertMissing.setVisible(true);
			return;
		}
		
		String user_Name = nameH.getText();
		String phone=phoneH.getText();
		String email= emailH.getText();
		
		String password = pwH.getText();
		String passwordConfirm = pwConfirmationH.getText();
		String username = usernameH.getText();
		
		String hotelName=hotelNameH.getText();
		String hotelAddress=hotelAddressH.getText();
		
		int hotel_id = 0;
		
		if(HotelEmployeesDB.checkExistAccount(username,phone)==1) {
			alertExistAccount.setVisible(true);
			return;
		}
		else if (HotelEmployeesDB.checkExistAccount(username,phone)==2) {
			alertExistPhone.setVisible(true);
			return;
		}
		
		if(!password.equals(passwordConfirm)) {
			alertPW.setVisible(true);
			return;
		}
		
		//create hotel first
		hotel_id=HotelDB.insertHotel(hotelName,hotelAddress);
		if(hotel_id==-1) {
			alertExistHotelName.setVisible(true);
			return;
		}
		//create employees with given hotel_id
		HotelEmployeesDB.insertHotelEmployees(hotel_id, username, phone, email, username, passwordConfirm);
		
		//After Sign Up
		succesfullySignUp.setVisible(true);
		usernameC.clear();
		phoneC.clear();
		emailC.clear();
		pwC.clear();
		pwConfirmationC.clear();
		nameC.clear();	
	}
	

}
