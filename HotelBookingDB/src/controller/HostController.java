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
import javafx.scene.control.PasswordField;
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
import model.database.AccountsDB;
import model.database.ExtensionsDB;
import model.database.HotelEmployeesDB;
import model.database.HotelsDB;
import model.library.Functions;
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

	public void addHotel(ActionEvent event) {
		secondPaneAlertLabel.setText("");
		mainPaneAlertLabel.setText("");
		mainPane.setEffect(new GaussianBlur(20));
		secondPane.setVisible(true);
		secondPane.toFront();
		secondPane.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		secondPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
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
		// ------------------------------------------------------------------
		try {
			reloadPage();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void reloadPage() throws SQLException {
		HotelEmployees user = (HotelEmployees) LoginController.getUser();
		System.out.println(user.getUserID());
		ArrayList<Hotels> hotels = HotelsDB.queryHotelsByManagerID(user.getManagerID());
		if(hotels==null) {
			Label noResult = new Label("You have no hotel on the list");
			hotelsTable.setPlaceholder(noResult);
			ObservableList<Hotels> tableListNull = FXCollections.observableArrayList();
			hotelsTable.setItems(tableListNull);
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
