package view;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.EmployeeSignUpController;
import controller.LoginController;
import javafx.collections.FXCollections;
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
import model.database.HotelEmployeesDB;
import model.users.HotelEmployees;

public class EmployeeSignUpView implements Initializable {
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
	private TableView<HotelEmployees> employeeListTable;
	@FXML
	private TableColumn<HotelEmployees, String> nameColumn;
	@FXML
	private TableColumn<HotelEmployees, String> phoneColumn;
	@FXML
	private TableColumn<HotelEmployees, String> emailColumn;
	@FXML
	private TableColumn<HotelEmployees, String> rankColumn;
	// ----------------------------------------------------------
	@FXML
	private TextField name;
	@FXML
	private TextField email;
	@FXML
	private TextField phone;
	@FXML
	private TextField accountName;
	@FXML
	private PasswordField pw;
	@FXML
	private PasswordField pwConfirmation;
	@FXML
	private ComboBox<String> rankComboBox;

	// ----------------------------------------------------------
	public void signOut(ActionEvent event) {
		LoginController.signOut();
		new SceneChanging().changeScene(event, "Login.fxml");
	}

	public void back(ActionEvent event) {
		new SceneChanging().changeScene(event, "HotelInfo.fxml");
	}

	public void backToMainPane(ActionEvent event) {
		secondPaneAlertLabel.setText("");
		mainPaneAlertLabel.setText("");
		mainPane.setEffect(null);
		secondPane.setVisible(false);
	}

	public void addEmployee(ActionEvent event) {
		secondPaneAlertLabel.setText("");
		mainPaneAlertLabel.setText("");
		mainPane.setEffect(new GaussianBlur(20));
		secondPane.setVisible(true);
		secondPane.toFront();
		secondPane.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		secondPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public void deleteEmployee(ActionEvent event) throws SQLException {
		secondPaneAlertLabel.setText("");
		mainPaneAlertLabel.setText("");
		EmployeeSignUpController.deleteEmployee(employeeListTable.getSelectionModel().getSelectedItem().getUserID());
		reloadPage();
	}

	public void signUp(ActionEvent event) throws SQLException {
		secondPaneAlertLabel.setText("");
		mainPaneAlertLabel.setText("");

		if (name.getText().trim().isEmpty() || phone.getText().trim().isEmpty() || email.getText().trim().isEmpty()
				|| pw.getText().trim().isEmpty() || pwConfirmation.getText().trim().isEmpty()
				|| accountName.getText().trim().isEmpty()) {
			secondPaneAlertLabel.setVisible(true);
			secondPaneAlertLabel.setText("Some fields are missing");
			return;
		}
		String nameString = name.getText();
		String phoneString = phone.getText();
		String emailString = email.getText();
		String pwString = pw.getText();
		String pwConfirmationString = pwConfirmation.getText();
		String accountNameString = accountName.getText();
		 try {
	            Integer.parseInt(phoneString);
	     } catch (Exception e) {
	    	 secondPaneAlertLabel.setText("Phone must be a sequence of number");
			return;
	     }
		int rank = 0;
		if (rankComboBox.getValue().equals("Manager"))
			rank = 1;
		else
			rank = 2;
		secondPaneAlertLabel = EmployeeSignUpController.signUpEmployeeAndManager(secondPaneAlertLabel, nameString,
				phoneString, emailString, pwString, pwConfirmationString, accountNameString,
				((HotelEmployees) LoginController.getUser()).getHotelID(), rank);
		reloadPage();
		accountName.clear();
		phone.clear();
		email.clear();
		pw.clear();
		pwConfirmation.clear();
		name.clear();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rankComboBox.getItems().add("Employee");
		rankComboBox.getItems().add("Manager");
		rankComboBox.setValue("User's type");
		// ------------------------------------------------------------------
		reloadPage();
	}
	private void reloadPage() {
		HotelEmployees user = (HotelEmployees) LoginController.getUser();
		ArrayList<HotelEmployees> employeeList = null;
		try {
			employeeList = HotelEmployeesDB.queryAllEmployeeAndManagerInfoByHotelID(user.getHotelID());
		} catch (SQLException e) {
			System.out.println("NO EMPLOYEE IN THE HOTEL,CHECK FOR ERROR!");
			e.printStackTrace();
		}
		nameColumn.setCellValueFactory(new PropertyValueFactory<HotelEmployees, String>("nameProperty"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<HotelEmployees, String>("emailProperty"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory<HotelEmployees, String>("phoneProperty"));
		rankColumn.setCellValueFactory(new PropertyValueFactory<HotelEmployees, String>("rankProperty"));
		employeeListTable.setItems(FXCollections.observableArrayList(employeeList));
	}
}
