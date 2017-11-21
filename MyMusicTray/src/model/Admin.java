package model;

import core.Context;
import exception.ModelMisuseException;
import exception.NotFoundException;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	public int id = -1;
	public String accountId;
	public String password;
	public String name;
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
				String name,
				String createdDate) {

		this.id = id;
		this.accountId = accountId;
		this.password = password;
		this.name = name;
		this.createdDate = createdDate;
	}

	public Admin(String accountId,
				 String password,
				 String name) {
		this.accountId = accountId;
		this.password = password;
		this.name = name;
		this.createdDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
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
					rs.getString("name"),
					rs.getString("created_date")
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void insert() {
		if (this.id != -1) {
			throw new ModelMisuseException(ModelMisuseException.INSERT_MISUSE);
		}
		try {
			PreparedStatement stmt = Context.getConnection().prepareStatement(
					"INSERT INTO admin (account_id, password, name, created_date) values(?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS
			);

			stmt.setString(1, accountId);
			stmt.setString(2, password);
			stmt.setString(3, name);
			stmt.setString(4, createdDate);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			this.id = rs.getInt(1); // Auto-incremented value


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		if (this.id == -1) {
			throw new ModelMisuseException(ModelMisuseException.UPDATE_MISUSE);
		}

		try {
			PreparedStatement stmt = Context.getConnection().prepareStatement(
					"UPDATE admin SET account_id = ?, password = ?, name = ? WHERE id = ?;"
			);

			stmt.setString(1, this.accountId);
			stmt.setString(2, this.password);
			stmt.setString(3, this.name);
			stmt.setString(4, Integer.toString(this.id));
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove() {
		if (this.id == -1) {
			throw new ModelMisuseException(ModelMisuseException.REMOVE_MISUSE);
		}

		try {
			PreparedStatement stmt = Context.getConnection().prepareStatement(
					"DELETE FROM admin WHERE id = ?;"
			);
			stmt.setString(1, Integer.toString(this.id));
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}