package model;

import java.sql.*;

abstract public class Model {

	abstract public void insert(Statement stmt) throws SQLException;
}
