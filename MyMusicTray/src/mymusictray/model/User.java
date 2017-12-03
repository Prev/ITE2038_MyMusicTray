package mymusictray.model;

import mymusictray.core.Context;
import mymusictray.exception.NotFoundException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User Entity
 *
 * @author Prev (0soo.2@prev.kr)
 */
public class User extends StrongTypeModel {

	/**
	 * Init table `user` by SQL
	 * @throws SQLException
	 */
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

	/**
	 * Select User instance by `accountId`
	 * @param accountId: String id value of account
	 * @return User instance
	 * @throws NotFoundException if there is no admin which has accountId of param.
	 */
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


	/**
	 * Account id used to login
	 */
	public String accountId;

	/**
	 * Password used to login
	 */
	public String password;

	/**
	 * Name of this user
	 */
	public String name;

	/**
	 * Birthday of this user
	 */
	public String birthday;

	/**
	 * Register date of this user
	 */
	public String registerDate;

	/**
	 * Email address of this user
	 */
	public String emailAddress;

	/**
	 * Phone number of this user
	 */
	public String phoneNumber;


	/**
	 * Constructor of User Model
	 *   Generally used in result of selection
	 * @param id
	 * @param accountId
	 * @param password
	 * @param name
	 * @param birthday
	 * @param registerDate
	 * @param emailAddress
	 * @param phoneNumber
	 */
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


	/**
	 * Constructor of User Model with no id (= not saved to database yet)
	 *   Generally used to make new admin
	 * @param accountId
	 * @param password
	 * @param name
	 * @param birthday
	 * @param emailAddress
	 * @param phoneNumber
	 */
	public User(String accountId,
				String password,
				String name,
				String birthday,
				String emailAddress,
				String phoneNumber) {
		this(-1, accountId, password, name, birthday, null, emailAddress, phoneNumber);
	}

	/**
	 * Get attribute name and value set that is not a key.
	 * @return (name-value) set of attributes
	 */
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
