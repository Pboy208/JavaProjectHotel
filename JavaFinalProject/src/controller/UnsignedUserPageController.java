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
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
import model.library.Functions;
import model.rooms.Filters;
import model.rooms.Hotels;
import model.users.UnsignedUser;

public class UnsignedUserPageController implements Initializable {
	
	@FXML
	private Pane searchPane;
	@FXML
	private Pane secondPane;
	@FXML
	private Label label;
	@FXML
	private TextField email;
	// -------------------------------------------------------------------
	@FXML
	private TextField destination;
	@FXML
	private TextField hotelName;
	// -------------------------------------------------------------------
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
	// -------------------------------------------------------------------
	@FXML
	private ComboBox<String> star;
	// -------------------------------------------------------------------
	@FXML
	private TableView<Hotels> recommendHotels;
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

	public void back(ActionEvent event) {
		LoginController.signOut();
		new SceneChanging().changeScene(event, "Login.fxml");
	}
	
	public void toSecondPane(ActionEvent e) {
		label.setText("");
		searchPane.setEffect(new GaussianBlur(20));
		secondPane.setVisible(true);
		secondPane.toFront();
		secondPane.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		secondPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public void toSearchPane(ActionEvent event) {
		label.setText("");
		secondPane.setVisible(false);
		searchPane.setEffect(null);
	}
	
	public void signUpForNews(ActionEvent event) throws SQLException {
		label.setText("");
		String emailString = email.getText();
		if(emailString.trim().isEmpty()) {
			label.setText("Your email field is empty");
			email.clear();
			return;
		}
		
		if (!Functions.checkEmail(emailString)) {
			label.setText("Invalid email address");
			email.clear();
			return;
		}
		
		UnsignedUser user = new UnsignedUser(emailString);
		user.insertInstance();
		label.setText("You have signed up for news!");
		email.clear();
		return;
	}
	
	public void searchButton(ActionEvent e) throws SQLException {
		int array[] = new int[12];
		Filters filter = new Filters();
		// ----------------------------------------- Destination part
		if (destination.getText().trim().isEmpty()) {
			filter.setDestination("");
		} else {
			filter.setDestination(destination.getText());
		}
		// ----------------------------------------- HotelName part
		if (hotelName.getText().trim().isEmpty()) {
			filter.setHotelName("");
		} else {
			filter.setHotelName(hotelName.getText());
		}
		
		// ----------------------------------------- Extension part
		RadioButton[] rbs = { radio1, radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9, radio10, radio11,
				radio12 };
		for (int i = 0; i < 12; i++)
			if (rbs[i].isSelected())
				array[i] = 1;
		filter.setExtensions(array);
		// ----------------------------------------- Star part
		String starString[] = { "* ", "* * ", "* * * ", "* * * * ", "* * * * * " };
		if(star.getValue()!=null) {
			for (int i = 0; i < 5; i++) {
				if (star.getValue().equals(starString[i])) {
					filter.setStar(i+1);
				}
			}
		} else filter.setStar(0);
		// ----------------------------------------- Gui part
		
		ArrayList<Hotels> recommendedHotelsList = filter.filterSearching(); 

		if (recommendedHotelsList == null) {
			Label noResult = new Label("No hotel meets your filter");
			recommendHotels.setPlaceholder(noResult);
			ObservableList<Hotels> tableListNULL = FXCollections.observableArrayList();
			recommendHotels.setItems(tableListNULL);
			return;
		}
		
		ObservableList<Hotels> tableList = FXCollections.observableArrayList(recommendedHotelsList);
		nameHotel.setCellValueFactory(new PropertyValueFactory<Hotels, String>("name"));
		addressHotel.setCellValueFactory(new PropertyValueFactory<Hotels, String>("address"));
		starHotel.setCellValueFactory(new PropertyValueFactory<Hotels, Integer>("star"));
		ratingHotel.setCellValueFactory(new PropertyValueFactory<Hotels, Float>("rating"));
		priceHotel.setCellValueFactory(new PropertyValueFactory<Hotels, String>("priceProperty"));
		recommendHotels.setItems(tableList);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		recommendHotels.setPlaceholder(new Label("Please fill in your filter"));
		// ----------------------------------------- Star part
		ObservableList<String> starList = FXCollections.observableArrayList();
		for (int i = 0; i < 5; i++) {
			String star = "";
			for (int j = 0; j < i + 1; j++) {
				star = star.concat("* ");
			}
			starList.add(star);
		}
		starList.add("Any Star");
		star.getItems().addAll(starList);
		star.setValue("Any Star");
	}
}
