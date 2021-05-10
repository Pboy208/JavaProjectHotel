package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import orders.Receipts;

public class ReceiptDB {

	public static Receipts queryRooms(int receiptID) throws SQLException {
		Connection connection = Postgre.makeConnection();
		Statement statement = connection.createStatement();
		String queryStatement = "SELECT * FROM receipt where receipt_id = " + receiptID;
		ResultSet receiptSet = statement.executeQuery(queryStatement);
		if (receiptSet.next() == false) {
			System.out.println(receiptID+ " Doest not exists");
			return null;
		}
		else {
			Receipts tmpReceipt= new Receipts(receiptSet.getInt(1), receiptSet.getInt(2), receiptSet.getInt(3), receiptSet.getInt(4),
					receiptSet.getDate(5), receiptSet.getDate(6), receiptSet.getDate(7));
			System.out.print("ReceiptDB :");
			tmpReceipt.printInfo();
			return tmpReceipt;
		}
			

	}

}
