package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.HostController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.database.LocationDB;
import model.locations.Districts;
import model.locations.Provinces;
import model.locations.Street;
import model.rooms.Hotels;
import model.users.HotelEmployees;

public class HostController implements Initializable {	
	private static Hotels hotel;
	
	public static Hotels getHotel() {
		return hotel;
	}

	public static void setHotel(Hotels hotel) {
		HostController.hotel = hotel;
	}
	
	@FXML
	private Pane mainPane;
	@FXML
	private Pane secondPane;
	@FXML
	private Label mainPaneAlertLabel;
	@FXML
	private Label secondPaneAlertLabel;
	// ----------------------------------------------------------
	@FXML
	private TableView<Hotels> hotelsTable;
	@FXML
	private TableColumn<Hotels, String> nameHotel;
	@FXML
	private TableColumn<Hotels, String> addressHotel;
	@FXML
	private TableColumn<Hotels, Integer> starHotel;
	@FXML
	private TableColumn<Hotels, Float> ratingHotel;
	@FXML
	private TableColumn<Hotels, String> priceHotel;
	// ----------------------------------------------------------
	private ArrayList<Provinces> provincesList = null;
	private ArrayList<Districts> districtsList = null;
	private ArrayList<Street> streetsList = null;
	private String province="";
	private String district="";
	private String street="";
	
	@FXML
	private ComboBox<String> districtBox;
	@FXML
	private ComboBox<String> provinceBox;
	@FXML
	private ComboBox<String> streetBox;
	@FXML
	private TextField specificAddress;
	@FXML
	private Label hotelAddressLabel;
	@FXML
	private TextField hotelNameH;
	@FXML
	private TextField hotelAddressH;

	// ----------------------------------------------------------
	public void signOut(ActionEvent event) {
		LoginController.signOut();
		new SceneChanging().changeScene(event, "Login.fxml");
	}

	public void back(ActionEvent event) {
		new SceneChanging().changeScene(event, "Filter.fxml");
	}

	public void backToMainPane(ActionEvent event) {
		secondPaneAlertLabel.setText("");
		mainPaneAlertLabel.setText("");
		mainPane.setEffect(null);
		secondPane.setVisible(false);
	}

	public void toAddHotel(ActionEvent event) {
		secondPaneAlertLabel.setText("");
		mainPaneAlertLabel.setText("");
		mainPane.setEffect(new GaussianBlur(20));
		secondPane.setVisible(true);
		secondPane.toFront();
		secondPane.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		secondPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public void addHotel(ActionEvent event) throws SQLException {
		secondPaneAlertLabel.setText("");
		if (hotelNameH.getText().trim().isEmpty()|| hotelAddressH.getText().trim().isEmpty()) {
			secondPaneAlertLabel.setText("Some fields are missing");
			return;
		}
		String hotelName = hotelNameH.getText();
		String hotelAddress = hotelAddressH.getText();
		String hotelAddressFull = hotelAddress + "," + street + "," + district + "," + province;
		
		int streetID = LocationDB.queryStreetIDByName(street);
		if(streetID == -1) {
			secondPaneAlertLabel.setText("Street invalid");
			secondPaneAlertLabel.setVisible(true);
			return ;
		}
		
		int hotelID = Hotels.insertHotel(hotelName, hotelAddressFull,streetID,((HotelEmployees)LoginController.getUser()).getManagerID());
		
		if (hotelID == -1) {
			secondPaneAlertLabel.setText("Hotel already exists in the system");
			secondPaneAlertLabel.setVisible(true);
			hotelNameH.clear();
			return ;
		}
		
		secondPaneAlertLabel.setText("A hotel is added");
		hotelAddressLabel.setText("Select below fields for address");
		hotelAddressH.clear();
		hotelNameH.clear();
		
		reloadPage();
	}
	public void toMainPane(ActionEvent event) {
			secondPane.setVisible(false);
			mainPane.setEffect(null);
	}
	public void viewDetail(ActionEvent event) {
		mainPaneAlertLabel.setText("");
		Hotels chosenHotel = hotelsTable.getSelectionModel().getSelectedItem();
		if(chosenHotel==null) {
			mainPaneAlertLabel.setText("Please choose a hotel to view detail");
			return;
		}
		setHotel(chosenHotel);
		new SceneChanging().changeScene(event, "HotelInfo.fxml");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		reloadPage();
		
		hotelAddressLabel.setText("Select below fields for address");
		hotelAddressH.textProperty().addListener((observable, oldValue, newValue) -> {
			hotelAddressLabel.setText(hotelAddressH.getText() + " " + street + " " + district + " " + province);
		});
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
			hotelAddressLabel.setText(hotelAddressH.getText() + " " + street + " " + district + " " + province);
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
				hotelAddressLabel.setText(hotelAddressH.getText() + " " + street + " " + district + " " + province);
				streetBox.getSelectionModel().selectedItemProperty().addListener((v3,oldStreet,newStreet)->{
					street = newStreet;
					if (street == null)
						street="";
					hotelAddressLabel.setText(hotelAddressH.getText() + " " + street + " " + district + " " + province);
				});
			});
		});
		
	}
	
	private void reloadPage(){
		HotelEmployees user = (HotelEmployees) LoginController.getUser();
		System.out.println(user.getUserID());
		ArrayList<Hotels> hotels = null;
		try {
			hotels = Hotels.queryHotelsByManagerID(user.getManagerID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(hotels==null) {
			Label noResult = new Label("You have no hotel on the list");
			hotelsTable.setPlaceholder(noResult);
			return;
		}
		ObservableList<Hotels> tableList = FXCollections.observableArrayList(hotels);
		nameHotel.setCellValueFactory(new PropertyValueFactory<Hotels, String>("name"));
		addressHotel.setCellValueFactory(new PropertyValueFactory<Hotels, String>("address"));
		starHotel.setCellValueFactory(new PropertyValueFactory<Hotels, Integer>("star"));
		ratingHotel.setCellValueFactory(new PropertyValueFactory<Hotels, Float>("rating"));
		priceHotel.setCellValueFactory(new PropertyValueFactory<Hotels, String>("minPriceProperty"));
		hotelsTable.setItems(tableList);
	}
}
