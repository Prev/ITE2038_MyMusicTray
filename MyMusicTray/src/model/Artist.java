package model;

import core.Context;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// Artist instance repository
	static private Map<Integer, Artist> repository = new HashMap<>();

	/**
	 * Instead of creating a new instance each time,
	 *   returns an existing, already created instance with the same ID.
	 * @return Artist instance
	 */
	static public Artist that(int id, String name, String activityStartDate) {
		if (repository.containsKey(id))
			return repository.get(id);
		else {
			Artist newInstance = new Artist(id, name, activityStartDate);
			repository.put(id, newInstance);
			return newInstance;
		}
	}


	/**
	 * Get all artists in database
	 * @return List of Arist instance
	 */
	public static List<Artist> getAllArtists() {
		List<Artist> ret = new ArrayList<>();

		try {
			ResultSet rs = Context.getDatabaseDriver().getStatement().executeQuery("SELECT * FROM `artist`");

			while (rs.next()) {
				ret.add(new Artist(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("activity_start_date")
				));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}


	public int id;
	public String name;
	public String activityStartDate;

	public Artist(int id,
				  String name,
				  String activityStartDate) {

		this.id = id;
		this.name = name;
		this.activityStartDate = activityStartDate;
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
