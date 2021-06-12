package model.database;

import java.sql.SQLException;

public interface DBInterface {
	void insertInstance(Object object) throws SQLException;
	Object queryInstance(int pk) throws SQLException;
	void updateInstance(Object object) throws SQLException;
}