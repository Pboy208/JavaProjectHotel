package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Label;
import model.database.AccountsDB;
import model.database.ReceiptsDB;
import model.database.UsersDB;
import model.receipts.Receipts;
import model.users.Users;

public class ClientInfoController {

	public static Label saveChange(Label alert, String newName, String newEmail, String newPhone, String oldPwString,
			String newPWString, String newPWConfirmString) throws SQLException {
		Users user = LoginController.getUser();
		if (AccountsDB.checkPassword(user.getUsername(), oldPwString) == -1) {
			alert.setText("Current password is incorrect");
			return alert;
		}
		if (!newPWString.equals(newPWConfirmString)) {
			alert.setText("Password confirmation is not match");
			return alert;
		}
		if (user.getPassword().equals(newPWString)) {
			alert.setText("New password must be different than current password");
			return alert;
		}

		UsersDB.updateUser(user.getId(), newName, newPhone, newEmail, newPWString);
		alert.setText("Information changed");
		return alert;
	}

	public static Label saveChange(Label alert, String newName, String newEmail, String newPhone) throws SQLException {
		Users user = LoginController.getUser();
		UsersDB.updateUser(user.getId(), newName, newPhone, newEmail, user.getPassword());
		alert.setText("Information changed");
		return alert;
	}

	public static ArrayList<Receipts> cancelReceipt(Receipts chosenReceipt) throws SQLException {

		ReceiptsDB.cancelReciepts(chosenReceipt.getReceiptID());
		ReceiptsDB.updateReceiptStatusClients(LoginController.getUser().getId());
		ArrayList<Receipts> receipts = ReceiptsDB.queryReceiptsForClient(LoginController.getUser().getId());
		return receipts;
	}
}
