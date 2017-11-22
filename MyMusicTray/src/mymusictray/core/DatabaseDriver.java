package mymusictray.core;

import java.sql.*;

public class DatabaseDriver {

	private Connection conn = null;
	private Statement stmt = null;

	public DatabaseDriver(Config config) throws SQLException {
		try {
			Class.forName("org.mariadb.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		conn = DriverManager.getConnection(
				config.databaseUrl + config.databaseName,
				config.databaseUser,
				config.databasePassword
		);
		stmt = conn.createStatement();
	}

	public Connection getConnection() {
		return conn;
	}

	public Statement getStatement() {
		return this.stmt;
	}

	public void close() {
		try {
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
