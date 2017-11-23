package mymusictray.model;

import mymusictray.core.Context;
import mymusictray.exception.NotFoundException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class User extends StrongTypeModel {

	static public void initTable() throws SQLException {
		Context.getDatabaseDriver().getStatement().executeUpdate(
			"CREATE TABLE IF NOT EXISTS `user` (" +
				"  `id` int(11) NOT NULL AUTO_INCREMENT," +
				"  `account_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
				"  `password` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
				"  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL," +
				"  `birthday` date NOT NULL," +
				"  `register_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP," +
				"  `email_address` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
				"  `phone_number` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
				"  PRIMARY KEY (`id`)" +
			") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=1 ;"
		);
	}

	static public User selectByAccountId(String accountId) {
		try {
			ResultSet rs = Context.getDatabaseDriver().getStatement().executeQuery("SELECT * FROM user WHERE account_id = '" + accountId + "';");
			if (!rs.next()) {
				throw new NotFoundException("Cannot find user by accountId '" + accountId + "'");
			}
			return new User(
					rs.getInt("id"),
					rs.getString("account_id"),
					rs.getString("password"),
					rs.getString("name"),
					rs.getString("birthday"),
					rs.getString("register_date"),
					rs.getString("email_address"),
					rs.getString("phone_number")
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String accountId;
	public String password;
	public String name;
	public String birthday;
	public String registerDate;
	public String emailAddress;
	public String phoneNumber;

	public User(int id,
				String accountId,
				String password,
				String name,
				String birthday,
				String registerDate,
				String emailAddress,
				String phoneNumber) {

		super("user");
		this.id = id;
		this.accountId = accountId;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.registerDate = registerDate;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
	}

	public User(String accountId,
				String password,
				String name,
				String birthday,
				String emailAddress,
				String phoneNumber) {
		this(-1, accountId, password, name, birthday, null, emailAddress, phoneNumber);
	}

	@Override
	public Map<String, String> getSubAttributes() {
		Map<String, String > ret = new HashMap<>();
		ret.put("account_id", accountId);
		ret.put("password", password);
		ret.put("name", name);
		ret.put("birthday", birthday);
		ret.put("email_address", emailAddress);
		ret.put("phone_number", phoneNumber);
		return ret;
	}
}
