package mymusictray.model;

import mymusictray.core.Context;
import mymusictray.exception.ModelMisuseException;
import mymusictray.exception.NotFoundException;

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


	static public Artist selectById(int id) {
		try {
			ResultSet rs = Context.getDatabaseDriver().getStatement().executeQuery("SELECT * FROM artist WHERE id = '"+id+"';");

			if (!rs.next())
				throw new NotFoundException("Cannot find artist by id '"+id+"'");

			return new Artist(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("activity_start_date")
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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

	public Artist(String name,
				  String activityStartDate) {

		this(-1, name, activityStartDate);
	}

	@Override
	public void insert() {
		if (this.id != -1) {
			throw new ModelMisuseException(ModelMisuseException.INSERT_MISUSE);
		}
		try {
			PreparedStatement stmt = Context.getConnection().prepareStatement(
					"INSERT INTO artist (name, activity_start_date) values(?, ?);",
					Statement.RETURN_GENERATED_KEYS
			);
			stmt.setString(1, this.name);
			stmt.setString(2, this.activityStartDate);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			this.id = rs.getInt(1); // Auto-incremented value


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addRelationWithAlbum(Album album) {
		try {
			PreparedStatement stmt = Context.getConnection().prepareStatement("INSERT INTO `album_artists`(`album_id`, `artist_id`) VALUES (?, ?)");
			stmt.setInt(1, album.id);
			stmt.setInt(2, this.id);
			stmt.executeUpdate();

			album.artists.add(this);

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
