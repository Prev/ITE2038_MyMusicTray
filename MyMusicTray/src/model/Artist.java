package model;

import core.Context;
import java.sql.*;

public class Artist implements Model {

	static public void initTable() throws SQLException {
		Statement stmt = Context.getDatabaseDriver().getStatement();
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS `artist` (\n" +
						"  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
						"  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
						"  `profile_image` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
						"  `activity_start_date` date NOT NULL,\n" +
						"  PRIMARY KEY (`id`)\n" +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=1 ;\n"
		);
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS `artist_musics` (\n" +
						"  `artist_id` int(11) NOT NULL,\n" +
						"  `music_id` int(11) NOT NULL,\n" +
						"  PRIMARY KEY (`artist_id`,`music_id`),\n" +
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
