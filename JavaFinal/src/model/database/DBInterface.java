package model.database;

import java.sql.SQLException;

public interface DBInterface {
	void insertInstance() throws SQLException;
	void queryInstance(int pk) throws SQLException;
	void updateInstance() throws SQLException;
}