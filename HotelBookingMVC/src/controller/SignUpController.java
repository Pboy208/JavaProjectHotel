package controller;

import java.sql.SQLException;

import javafx.scene.control.Label;
import model.database.AccountsDB;
import model.database.HotelDB;
import model.database.HotelEmployeesDB;
import model.database.UsersDB;

public class SignUpController {

	public static Label signUpClient(Label alertLabel, String name, String phone, String email, String password,
			String passwordConfirm, String accountName) throws SQLException {
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
		UsersDB.insertUser(name, phone, email, accountName, passwordConfirm);
		alertLabel.setText("Your account is ready");
		alertLabel.setVisible(true);
		return alertLabel;
	}

	public static Label signUpHotelManager(Label alertLabel, String name, String phone, String email, String password,
			String passwordConfirm, String accountName, String hotelName, String hotelAddress) throws SQLException {
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
		int hotelID = HotelDB.insertHotel(hotelName, hotelAddress);
		if (hotelID == -1) {
			alertLabel.setText("Hotel already exists in the system");
			alertLabel.setVisible(true);
			return alertLabel;
		}
		HotelEmployeesDB.insertHotelEmployees(hotelID, name, phone, email, accountName, passwordConfirm);
		// After Sign Up
		alertLabel.setText("Your account is ready");
		alertLabel.setVisible(true);
		return alertLabel;

	}
}
