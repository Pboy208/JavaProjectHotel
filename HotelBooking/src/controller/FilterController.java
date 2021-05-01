package controller;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


import database.FilterDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rooms.Filter;
import rooms.Hotel;


public class FilterController implements Initializable {

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
	private TextField destination;
	@FXML
	private TextField hotelName;
	@FXML
	private Button logOut;
	@FXML
	private Button receipts;
	@FXML
	private Button search;
	@FXML
	private Button userInfo;
	
	@FXML
	private Label label;
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
	private ComboBox<String> star;
	//-------------------------------------------------------------------
	@FXML
	private TableView<Hotel> recommendHotels;
	@FXML
	private TableColumn<Hotel, String> nameHotel;
	@FXML
	private TableColumn<Hotel, String> addressHotel;
	@FXML
	private TableColumn<Hotel, String> roomsAvailable;
	@FXML
	private TableColumn<Hotel, String> starHotel;
	@FXML
	private TableColumn<Hotel, String> ratingHotel;
	
	private ObservableList<Filter> filterList= FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		recommendHotels.setPlaceholder(new Label("Please fill in your filter"));
		//-----------------------------------------
		ObservableList<String> starList = FXCollections.observableArrayList();
		for(int i=0;i<5;i++) {
			String star="";
			for(int j=0;j<i+1;j++) {
				star=star.concat("* ");
			}
			starList.add(star);
		}
		starList.add("Any star");
		star.getItems().addAll(starList);
		//-----------------------------------------
//		ObservableList<Hotel> tableList= FXCollections.observableArrayList();
//		nameHotel.setCellValueFactory(new PropertyValueFactory<Hotel,String>("hotelName"));
//		addressHotel.setCellValueFactory(new PropertyValueFactory<Hotel,String>("hotelAddress"));
//		roomsAvailable.setCellValueFactory(new PropertyValueFactory<Hotel,String>("hotelRoomsAvailable"));
//		recommendHotels.setItems(tableList);
	}
	
	public void book(ActionEvent e) {
		Hotel hotel = recommendHotels.getSelectionModel().getSelectedItem();
	}
	
	public void searchButton(ActionEvent e) throws SQLException {
		int array[]= new int[12];
		Filter filter = new Filter();
		//----------------------------------------- Destination part
			
			if(destination.getText().trim().isEmpty())
			{	
				System.out.println("Null Destination");
				filter.setDestination(null);
			}
			else {
				System.out.println("Destination "+destination.getText());
				filter.setDestination(destination.getText());
			}
		//----------------------------------------- HotelName part
			if(hotelName.getText().trim().isEmpty())
			{	
				System.out.println("Null Hotel Name");
				filter.setHotelName(null);
			}
			else {
				System.out.println("Hotel "+hotelName.getText());
				filter.setHotelName(hotelName.getText());
			}
		//----------------------------------------- Extension part
		RadioButton[] rbs = {radio1,radio2,radio3,radio4,radio5,radio6,radio7,radio8,radio9,radio10,radio11,radio12};
		for(int i=0;i<12;i++) 
			if(rbs[i].isSelected()) 
				array[i]=1;
		filter.setExtensions(array);
		
		//----------------------------------------- Gui part
		//ArrayList<Hotel> TMP=ExtensionDB.queryHotelsByExtension(filter);
		ArrayList<Hotel> TMP=FilterDB.queryHotelsByFilter(filter);
//		for(Hotel i: TMP) {
//			i.prinInfo();
//		}
		if(TMP == null) {
			Label noResult =new Label("No hotel meets your filter");
			recommendHotels.setPlaceholder(noResult);
			return;
		}
		ObservableList<Hotel> tableList =FXCollections.observableArrayList(TMP);
		nameHotel.setCellValueFactory(new PropertyValueFactory<Hotel,String>("nameProperty"));
		addressHotel.setCellValueFactory(new PropertyValueFactory<Hotel,String>("addressProperty"));
		roomsAvailable.setCellValueFactory(new PropertyValueFactory<Hotel,String>("roomsAvailableProperty"));
		starHotel.setCellValueFactory(new PropertyValueFactory<Hotel,String>("starProperty"));
		ratingHotel.setCellValueFactory(new PropertyValueFactory<Hotel,String>("ratingProperty"));
		recommendHotels.setItems(tableList);
		System.out.println("Done");
	}
//	
}
