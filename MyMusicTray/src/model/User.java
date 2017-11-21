package model;

import core.Context;
import exception.NotFoundException;

import java.sql.*;

public class User implements Model {

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

	int id;
	String name;
	String password;
	String birthday;
	String register_date;
	String email_address;
	String phone_number;

	/**
	 * Constructor of User Model
	 * @param id
	 * @param name
	 * @param password
	 * @param birthday
	 * @param email_address
	 * @param phone_number
	 */
	public User(int id,
				String name,
				String password,
				String birthday,
				String register_date,
				String email_address,
				String phone_number) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
		this.register_date = register_date;
		this.email_address = email_address;
		this.phone_number = phone_number;
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
	public void insert() {
		try {
			Context.getDatabaseDriver().getStatement().executeUpdate(String.format("INSERT INTO user" +
						" (id, name, password, birthday, email_address, phone_number) " +
							"VALUES ('%d', '%s', '%s', '%s', '%s', '%s');",
						id,
						name,
						password,
						birthday,
						email_address,
						phone_number
			));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		// TODO
	}

	@Override
	public void remove() {
		// TODO
	}
}
