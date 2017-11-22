package mymusictray.model;

import mymusictray.core.Context;
import java.sql.*;

public class PlayList implements Model {

	static public void initTable() throws SQLException {
		Statement stmt = Context.getDatabaseDriver().getStatement();
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS `playlist` (\n" +
						"  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
						"  `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
						"  `owner` int(11) NOT NULL,\n" +
						"  PRIMARY KEY (`id`),\n" +
						"  KEY `owner` (`owner`)\n" +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=1 ;\n"
		);
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS `playlist_item` (\n" +
						"  `playlist_id` int(11) NOT NULL,\n" +
						"  `music_id` int(11) NOT NULL,\n" +
						"  PRIMARY KEY (`playlist_id`,`music_id`),\n" +
						"  KEY `music_id` (`music_id`)\n" +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;\n"
		);
	}

	@Override
	public void insert() {
		// TODO
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