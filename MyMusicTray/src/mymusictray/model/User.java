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
				"  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL," +
				"  `password` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
				"  `birthday` date NOT NULL," +
				"  `register_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP," +
				"  `email_address` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
				"  `phone_number` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
				"  PRIMARY KEY (`id`)" +
			") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=1 ;"
		);
	}

	String name;
	String password;
	String birthday;
	String registerDate;
	String emailAddress;
	String phoneNumber;

	/**
	 * Constructor of User Model
	 * @param id
	 * @param name
	 * @param password
	 * @param birthday
	 * @param emailAddress
	 * @param phoneNumber
	 */
	public User(int id,
				String name,
				String password,
				String birthday,
				String registerDate,
				String emailAddress,
				String phoneNumber) {

		super("user");
		this.id = id;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
		this.registerDate = registerDate;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
	}

	static public User selectById(int id) throws SQLException {
		ResultSet rs = Context.getDatabaseDriver().getStatement().executeQuery("SELECT * FROM user WHERE id = '"+id+"';");

		if (!rs.next()) {
			throw new NotFoundException("Cannot find user by id '"+id+"'");
		}

		return new User(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getString("password"),
				rs.getString("birthday"),
				rs.getString("register_date"),
				rs.getString("email_address"),
				rs.getString("phone_number")
		);
	}

	@Override
	public Map<String, String> getSubAttributes() {
		Map<String, String > ret = new HashMap<>();
		ret.put("name", name);
		ret.put("password", password);
		ret.put("birthday", birthday);
		ret.put("email_address", emailAddress);
		ret.put("phone_number", phoneNumber);
		return ret;
	}
}
