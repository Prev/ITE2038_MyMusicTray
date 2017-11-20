package model;

import java.sql.*;

public class Admin extends Model {

	static public void initTable(Statement stmt) throws SQLException {
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS `admin` (\n" +
						"  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
						"  `password` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
						"  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
						"  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
						"  PRIMARY KEY (`id`)\n" +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=1 ;\n"
		);
	}

	@Override
	public void insert(Statement stmt) throws SQLException {
		// TODO
	}
}