package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.database.AccountsDB;
import model.database.HotelEmployeesDB;
import model.database.HotelsDB;
import model.database.LocationDB;
import model.database.UsersDB;
import model.library.Functions;
import model.locations.Districts;
import model.locations.Provinces;
import model.locations.Street;
import model.users.Users;

public class SignUpController implements Initializable {
	
	private ArrayList<Provinces> provincesList = null;
	private ArrayList<Districts> districtsList = null;
	private ArrayList<Street> streetsList = null;
	private String province="";
	private String district="";
	private String street="";
	
	//------------------------------------
	@FXML
	private ComboBox<String> districtBox;
	
	@FXML
	private ComboBox<String> provinceBox;
	
	@FXML
	private ComboBox<String> streetBox;
	
	@FXML
	private TextField specificAddress;
	
	@FXML
	private Label hotelAddress;
	
	//------------------------------------
	@FXML
	private Label beginLabel;
	@FXML
	private Label alertLabel;
	// ----------------------------------- C for Clients
	@FXML
	private Pane paneC;
	@FXML
	private TextField accountNameC;
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
	// ----------------------------------- H for Hotel managers
	@FXML
	private Pane paneH;
	@FXML
	private TextField accountNameH;
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
	// -----------------------------------
	@FXML
	private Button back;
	// -----------------------------------
	
	public void back(ActionEvent event) {
		new SceneChanging().changeScene(event, "Login.fxml");
	}

	public void typeClients(ActionEvent event) {
		alertLabel.setVisible(false);
		beginLabel.setVisible(false);
		paneH.setVisible(false);
		paneC.setVisible(true);
	}

	public void typeHotelManagers(ActionEvent event) {
		alertLabel.setVisible(false);
		beginLabel.setVisible(false);
		paneH.setVisible(true);
		paneC.setVisible(false);
	}
	
	private void clearTextFields() {
		phoneH.clear();
		accountNameH.clear();
		emailH.clear();
		pwH.clear();
		pwConfirmationH.clear();
		nameH.clear();
		hotelAddress.setText("Select below fields for address");
		hotelAddressH.clear();
		provinceBox.setValue("Choose a province");
		districtBox.setValue("Choose a district");
		streetBox.setValue("Choose a street");
		
		accountNameC.clear();
		phoneC.clear();
		emailC.clear();
		pwC.clear();
		pwConfirmationC.clear();
		nameC.clear();
		
		
	}
	
	public void signUpClient(ActionEvent event) throws SQLException {
		alertLabel.setVisible(false);
		if (nameC.getText().trim().isEmpty() || phoneC.getText().trim().isEmpty() || emailC.getText().trim().isEmpty()
				|| pwC.getText().trim().isEmpty() || pwConfirmationC.getText().trim().isEmpty()
				|| accountNameC.getText().trim().isEmpty()) {
			alertLabel.setVisible(true);
			alertLabel.setText("Some fields are missing");
			return;
		}

		String name = nameC.getText();
		String phone = phoneC.getText();
		String email = emailC.getText();
		String password = pwC.getText();
		String passwordConfirm = pwConfirmationC.getText();
		String accountName = accountNameC.getText();

		if(!Functions.checkPhoneNumber(phone)) {
			alertLabel.setVisible(true);
			alertLabel.setText("Phone number must be a sequence of 10 numbers");
			phoneC.clear();
			return;
		}
		
		if(!Functions.checkEmail(email)) {
			alertLabel.setVisible(true);
			alertLabel.setText("Invalid email address");
			emailC.clear();
			return;
		}

		if (AccountsDB.checkExistAccount(accountName, phone) == 1) {
			alertLabel.setText("Account already exists");
			alertLabel.setVisible(true);
			accountNameC.clear();
			return ;
		} else if (AccountsDB.checkExistAccount(accountName, phone) == 2) {
			alertLabel.setText("Phone is currently being used in other account ");
			alertLabel.setVisible(true);
			phoneC.clear();
			return ;
		}
		
		if (!password.equals(passwordConfirm)) {
			alertLabel.setText("Password confirmation is not match");
			alertLabel.setVisible(true);
			pwC.clear();
			pwConfirmationC.clear();
			return ;
		}
		
		Users user = new Users(name, phone, email, accountName, passwordConfirm);
		new UsersDB().insertInstance(user);
		alertLabel.setText("Your account is ready");
		alertLabel.setVisible(true);
		clearTextFields();
		return ;
	}

	public void signUpHotelManager(ActionEvent event) throws SQLException {
		alertLabel.setVisible(false);
		if (nameH.getText().trim().isEmpty() || phoneH.getText().trim().isEmpty() || emailH.getText().trim().isEmpty()
				|| pwH.getText().trim().isEmpty() || pwConfirmationH.getText().trim().isEmpty()
				|| accountNameH.getText().trim().isEmpty() || hotelNameH.getText().trim().isEmpty()
				|| hotelAddressH.getText().trim().isEmpty()) {
			alertLabel.setVisible(true);
			alertLabel.setText("Some fields are missing");
			return;
		}

		String name = nameH.getText();
		String phone = phoneH.getText();
		String email = emailH.getText();
		String password = pwH.getText();
		String passwordConfirm = pwConfirmationH.getText();
		String accountName = accountNameH.getText();
		String hotelName = hotelNameH.getText();
		String hotelAddress = hotelAddressH.getText();
		String hotelAddressFull = hotelAddress + "," + street + "," + district + "," + province;

		if(!Functions.checkPhoneNumber(phone)) {
			alertLabel.setVisible(true);
			alertLabel.setText("Phone number must be a sequence of 10 numbers");
			phoneH.clear();
			return;
		}
		
		if(!Functions.checkEmail(email)) {
			alertLabel.setVisible(true);
			alertLabel.setText("Invalid email address");
			emailH.clear();
			return;
		}
				
		if (AccountsDB.checkExistAccount(accountName, phone) == 1) {
			alertLabel.setText("Account already exists");
			alertLabel.setVisible(true);
			accountNameH.clear();
			return ;
		} else if (AccountsDB.checkExistAccount(accountName, phone) == 2) {
			alertLabel.setText("Phone is currently being used in other account ");
			alertLabel.setVisible(true);
			phoneH.clear();
			return ;
		}
		
		if (!password.equals(passwordConfirm)) {
			alertLabel.setText("Password confirmation is not match");
			alertLabel.setVisible(true);
			pwConfirmationH.clear();
			pwH.clear();
			return ;
		}
		
		int hotelID = HotelsDB.insertHotel(hotelName, hotelAddressFull);
		
		if (hotelID == -1) {
			alertLabel.setText("Hotel already exists in the system");
			alertLabel.setVisible(true);
			hotelNameH.clear();
			return ;
		}
		
		HotelEmployeesDB.insertHotelEmployees(hotelID, name, phone, email, 1, accountName, passwordConfirm);
		alertLabel.setText("Your account is ready");
		alertLabel.setVisible(true);
		clearTextFields();
		return ;

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		hotelAddress.setText("Select below fields for address");
//		hotelAddressH.textProperty().addListener((observable, oldValue, newValue) -> {
//		    hotelAddress.setText(hotelAddressH.getText() + " " + street + " " + district + " " + province);
//		});
		//-------------------------------- Callback function for locations
		try {
			provincesList = LocationDB.queryProvince();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Problems in query province-signup");
		}
		
		ObservableList<String> provincesCollection = FXCollections.observableArrayList();
		for(Provinces p : provincesList) {
			provincesCollection.add(p.getProvinceName());
		}
		provinceBox.getItems().addAll(provincesCollection);
		provinceBox.getSelectionModel().selectedItemProperty().addListener((v,oldProvince,newProvince)->{
			province=newProvince;
			int provinceID=0;
			for(Provinces p : provincesList) {
				if(p.getProvinceName().equals(newProvince))
					provinceID=p.getProvinceID();
			}
			try {
				districtsList = LocationDB.queryDistrict(provinceID);
				System.out.println(districtsList.size()+" size");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Problems in query district-signup");
			}
			ObservableList<String> districtsCollection = FXCollections.observableArrayList();
			for(Districts d : districtsList) {
				districtsCollection.add(d.getDistrictName());
			}
			districtBox.getItems().clear();
			districtBox.getItems().addAll(districtsCollection);
			hotelAddress.setText(hotelAddressH.getText() + " " + street + " " + district + " " + province);
			districtBox.getSelectionModel().selectedItemProperty().addListener((v2,oldDistrict,newDistrict)->{
				district= newDistrict;
				if (district == null)
					district="";
				int districtID=0;
				for(Districts d : districtsList) {
					if(d.getDistrictName().equals(newDistrict))
						districtID=d.getDistrictID();
				}
				try {
					streetsList = LocationDB.queryStreet(districtID);
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Problems in query street-signup");
				}
				ObservableList<String> streetsCollection = FXCollections.observableArrayList();
				for(Street d : streetsList) {
					streetsCollection.add(d.getStreetName());
				}
				streetBox.getItems().clear();
				streetBox.getItems().addAll(streetsCollection);
				hotelAddress.setText(hotelAddressH.getText() + " " + street + " " + district + " " + province);
				streetBox.getSelectionModel().selectedItemProperty().addListener((v3,oldStreet,newStreet)->{
					street = newStreet;
					if (street == null)
						street="";
					hotelAddress.setText(hotelAddressH.getText() + " " + street + " " + district + " " + province);
				});
			});
		});
	}
}
