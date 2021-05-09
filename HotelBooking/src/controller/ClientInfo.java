package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.CheckPasswordDB;
import database.ClientDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import orders.Orders;
import user.Clients;

public class ClientInfo implements Initializable{
	
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
	private Label accountName;
	@FXML
	private TextField name;
	@FXML
	private TextField phone;
	@FXML
	private TextField email;
	@FXML
	private PasswordField oldPW;
	@FXML
	private PasswordField newPW;
	@FXML
	private PasswordField newPWConfirm;
	@FXML
	private Label alert;
	
	@FXML
	private TableView<Orders> orderList;
	
	
	public void back(ActionEvent event) {
		changeScene(event,"Filter.fxml");
	}
	
	public void signOut(ActionEvent event) {
		changeScene(event,"Login.fxml");
		LoginController.signOut();
	}
	
	public void saveChange(ActionEvent event) throws SQLException {
		
		alert.setText("");
		
		Clients user = (Clients)LoginController.getUser();
		boolean visibleFlag=false;
		if(oldPW.isVisible()==true) {
			visibleFlag=true;
		}
		
		if(name.getText().trim().isEmpty() || email.getText().trim().isEmpty() || phone.getText().trim().isEmpty()) {
			alert.setText("Some fields is Missing");
			return;
		}
		if(visibleFlag) {
			if(oldPW.getText().trim().isEmpty() || newPW.getText().trim().isEmpty() || newPWConfirm.getText().trim().isEmpty()) {
				alert.setText("Some fields is Missing");
				return;
			}
		}
		String newName = name.getText();
		String newEmail = email.getText();
		String newPhone = phone.getText();
		
		String oldPwString= null;
		String newPWString = null;
		String newPWConfirmString = null;
		
		if(visibleFlag) {
			oldPwString= oldPW.getText();
			newPWString = newPW.getText();
			newPWConfirmString = newPWConfirm.getText();
			if(CheckPasswordDB.checkPasswordClients(user.getUsername(),oldPwString)==-1){
				alert.setText("Current password is incorrect");
				return;
			}
			if(!newPWString.equals(newPWConfirmString)) {
				alert.setText("Comfirmation of password is wrong");
				return;
			}
			if(user.getPassword().equals(newPWString)) {
				alert.setText("New password must be different than current password");
				return;
			}
		}
		ClientDB.updateClients(user.getId(), newName, newPhone, newEmail, newPWString);
		alert.setText("Information changed");
	}
	
	public void changePassword(ActionEvent event) {
		oldPW.setVisible(!oldPW.isVisible());
		newPW.setVisible(!newPW.isVisible());
		newPWConfirm.setVisible(!newPWConfirm.isVisible());
	}
	public void cancelReceipt(ActionEvent event) {
		alert.setText("");
	}
	
	public void viewHotel(ActionEvent event) {
		alert.setText("");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		alert.setText("");
		Clients user =(Clients) LoginController.getUser();
		user.printInfo();
		accountName.setText("Username: " + user.getUsername());
		name.setText(user.getName());
		email.setText(user.getEmail());
		phone.setText(user.getPhoneNumber());
	}

}
