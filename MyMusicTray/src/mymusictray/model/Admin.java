package mymusictray.model;

import mymusictray.core.Context;
import mymusictray.exception.NotFoundException;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Admin extends StrongTypeModel {

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

		super("admin");
		this.id = id;
		this.accountId = accountId;
		this.password = password;
		this.name = name;
		this.createdDate = createdDate;

	}

	public Admin(String accountId,
				 String password,
				 String name) {

		this(-1, accountId, password, name, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
	}

	@Override
	public Map<String, String> getSubAttributes() {
		Map<String, String > ret = new HashMap<>();
		ret.put("account_id", accountId);
		ret.put("password", password);
		ret.put("name", name);
		ret.put("created_date", createdDate);
		return ret;
	}
}