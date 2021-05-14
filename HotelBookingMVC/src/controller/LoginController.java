package controller;

import java.sql.SQLException;
import javafx.scene.control.Label;
import model.database.AccountsDB;
import model.database.HotelEmployeesDB;
import model.database.UsersDB;
import model.users.Users;

public class LoginController {
	private static Users user = null;

	public static void setUser(String name, String phone, String email, String password) {
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setPhoneNumber(phone);
	}

	public static Users getUser() {
		return user;
	}

	public static void signOut() {
		user = null;
	}

	public static Label signInButtonOnAction(Label alertLabel, String accountNameString, String passwordString,
			int type) throws SQLException {
		// type =1 -> user,=2 -> hotel employees
		int userID = AccountsDB.checkPassword(accountNameString, passwordString);
		if (userID == -1) {
			alertLabel.setText("Wrong user name or password");
			alertLabel.setVisible(true);
			return alertLabel;
		}

		alertLabel.setVisible(false);
		if (type == 2) {
			user = HotelEmployeesDB.queryEmployeeInfo(userID);
			user.setUsername(accountNameString);
			user.setPassword(passwordString);
			return alertLabel;
		}
		user = UsersDB.queryUserInfo(userID);
		user.setPassword(passwordString);
		user.setUsername(accountNameString);
		return alertLabel;
	}
}
