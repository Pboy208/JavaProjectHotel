package controller;

import java.sql.SQLException;
import javafx.scene.control.Label;
import model.database.AccountsDB;
import model.database.HotelEmployeesDB;

public class EmployeeSignUpController {


	public static Label signUpEmployeeAndManager(Label alertLabel, String name, String phone, String email, String password,
			String passwordConfirm, String accountName,int hotelID,int rank) throws SQLException {
		if (AccountsDB.checkExistAccount(accountName, phone) == 1) {
			alertLabel.setText("Account already exists");
			alertLabel.setVisible(true);
			return alertLabel;
		} else if (AccountsDB.checkExistAccount(accountName, phone) == 2) {
			alertLabel.setText("Phone is currently being used in other account ");
			alertLabel.setVisible(true);
			return alertLabel;
		}
		if (!password.equals(passwordConfirm)) {
			alertLabel.setText("Password confirmation is not match");
			alertLabel.setVisible(true);
			return alertLabel;
		}
		HotelEmployeesDB.insertHotelEmployees(hotelID, name, phone, email,rank, accountName, passwordConfirm);
		// After Sign Up
		alertLabel.setText("Your account is ready");
		alertLabel.setVisible(true);
		return alertLabel;
	}
	
	public static void deleteEmployee(int userID) throws SQLException {
		HotelEmployeesDB.deleteEmployees(userID);
	}
}
