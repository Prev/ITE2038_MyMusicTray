package model;

import java.sql.*;

public interface Model {

	void insert(Statement stmt) throws SQLException;
}
