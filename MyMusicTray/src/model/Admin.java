package model;

import core.Context;
import exception.NotFoundException;

import java.sql.*;

public class Admin implements Model {

	static public void initTable() throws SQLException {
		Context.getDatabaseDriver().getStatement().executeUpdate(
				"CREATE TABLE IF NOT EXISTS `admin` (\n" +
						"  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
						"  `account_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
						"  `password` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
						"  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
						"  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
						"  PRIMARY KEY (`id`)\n" +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=1 ;\n"
		);
	}

	public int id;
	public String accountId;
	public String password;
	public String createdDate;

	/**
	 * Constructor of User Model
	 * @param id
	 * @param accountId
	 * @param password
	 * @param createdDate
	 */
	public Admin(int id,
				String accountId,
				String password,
				String createdDate) {
		this.id = id;
		this.accountId = accountId;
		this.password = password;
		this.createdDate = createdDate;
	}


	static public Admin selectByAccountId(String accountId) {
		try {
			ResultSet rs = Context.getDatabaseDriver().getStatement().executeQuery(
					"SELECT * FROM admin WHERE account_id = '"+accountId+"';"
			);

			if (!rs.next())
				throw new NotFoundException("Cannot find admin by account_id '"+accountId+"'");

			return new Admin(
					rs.getInt("id"),
					rs.getString("account_id"),
					rs.getString("password"),
					rs.getString("created_date")
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void insert() throws SQLException {
		PreparedStatement stmt = Context.getConnection().prepareStatement(
				"INSERT INTO account (account_id, password, created_date) values(?, ?, ?);",
				Statement.RETURN_GENERATED_KEYS
		);

		stmt.setString(1, accountId);
		stmt.setString(2, password);
		stmt.setString(3, createdDate);
		stmt.executeUpdate();

		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		this.id = rs.getInt(1); // Auto-incremented value
	}
}