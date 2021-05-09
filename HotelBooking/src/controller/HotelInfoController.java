package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.CheckPasswordDB;
import database.HotelEmployeesDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import orders.Orders;

import user.HotelEmployees;

public class HotelInfoController implements Initializable {
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
	
	//-------------------------------------------------------------------
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
	//-------------------------------------------------------------------
	@FXML
	private TableView<Orders> orderList;
	//-------------------------------------------------------------------
	@FXML
	private TextField hotelName;
	@FXML
	private TextField hotelAddress;
	@FXML
	private TextField website;
	@FXML
	private TextField price;
	//-------------------------------------------------------------------
	@FXML
	private ComboBox<String> star;
	//-------------------------------------------------------------------
	@FXML
	private RadioButton radio1;
	@FXML
	private RadioButton radio2;
	@FXML
	private RadioButton radio3;
	@FXML
	private RadioButton radio4;
	@FXML
	private RadioButton radio5;
	@FXML
	private RadioButton radio6;
	@FXML
	private RadioButton radio7;
	@FXML
	private RadioButton radio8;
	@FXML
	private RadioButton radio9;
	@FXML
	private RadioButton radio10;
	@FXML
	private RadioButton radio11;
	@FXML
	private RadioButton radio12;
	//-------------------------------------------------------------------
	@FXML
	private TextField roomID;
	//-------------------------------------------------------------------
	public void signOut(ActionEvent e) {
		changeScene(e,"Login.fxml");
	}
	
	public void saveChange(ActionEvent event) throws SQLException {
		alert.setText("");
		//-------------------------------------- User info
		HotelEmployees user = (HotelEmployees)LoginController.getUser();
		boolean visibleFlag=false;
		if(oldPW.isVisible()==true) {
			visibleFlag=true;
		}
		
		if(name.getText().trim().isEmpty() || email.getText().trim().isEmpty() || phone.getText().trim().isEmpty() ||
		hotelName.getText().trim().isEmpty() || hotelAddress.getText().trim().isEmpty()|| website.getText().trim().isEmpty()|| price.getText().trim().isEmpty()) {
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
			if(CheckPasswordDB.checkPasswordHotels(user.getUsername(),oldPwString)==-1){
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
		HotelEmployeesDB.updateHotelEmployees(user.getId(), newName, newPhone, newEmail, newPWString);
		alert.setText("Information changed");
		oldPW.clear();
		newPW.clear();
		newPWConfirm.clear();
		//-------------------------------------- Hotel info
		String hotelAddressString=hotelAddress.getText();
		String hotelNameString= hotelName.getText();
		String websiteString=website.getText();
		float priceFloat=Float.parseFloat(price.getText());
		int starInt=0;
		
		String starString[]= {"* ","* * ","* * * ","* * * * ","* * * * * "};
		for(int i=0;i<5;i++) {
			if(star.getValue().equals(starString[i]))
				starInt=i+1;
		}
		//-------------------------------------- Extensions
		int array[] = new int[12];
		RadioButton[] rbs = {radio1,radio2,radio3,radio4,radio5,radio6,radio7,radio8,radio9,radio10,radio11,radio12};
		for(int i=0;i<12;i++) 
			if(rbs[i].isSelected()) 
				array[i]=1;
		//-------------------------------------- Update hotel info
		
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
		//------------------------------------- User part
		HotelEmployees user =(HotelEmployees) LoginController.getUser();
		user.printInfo();
		System.out.println(user.getPassword());
		accountName.setText("Username: " + user.getUsername());
		name.setText(user.getName());
		email.setText(user.getEmail());
		phone.setText(user.getPhoneNumber());
		//------------------------------------- Star
		ObservableList<String> starList = FXCollections.observableArrayList();
		for(int i=0;i<5;i++) {
			String star="";
			for(int j=0;j<i+1;j++) {
				star=star.concat("* ");
			}
			starList.add(star);
		}
		star.getItems().addAll(starList);
		//-------------------------------------
	}
	

}
