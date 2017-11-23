package mymusictray.model;

import mymusictray.core.Context;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PlayList extends StrongTypeModel {

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

	public String name;
	public User owner;

	public PlayList(int id,
				 String name,
				 User owner) {

		super("playlist");
		this.id = id;
		this.name = name;
		this.owner = owner;
	}

	public PlayList(String name, User owner) {
		this(-1, name, owner);
	}

	@Override
	public Map<String, String> getSubAttributes() {
		Map<String, String > ret = new HashMap<>();
		ret.put("name", name);
		ret.put("owner", Integer.toString(owner.id));
		return ret;
	}
}