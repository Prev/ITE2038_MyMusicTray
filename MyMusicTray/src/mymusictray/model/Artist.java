package mymusictray.model;

import mymusictray.core.Context;
import mymusictray.exception.NotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Artist Entity
 *
 * @author Prev (0soo.2@prev.kr)
 */
public class Artist extends StrongTypeModel implements ListableModel {

	/**
	 * Init table `album` and related table `artist_music` by SQL
	 * @throws SQLException
	 */
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
	 * Get artists in artist table
	 * @param condition: Additional condition of query.
	 * @return List of Arist instance
	 */
	public static List<Artist> getArtists(String condition) {
		List<Artist> ret = new ArrayList<>();

		try {
			ResultSet rs = Context.getDatabaseDriver().getStatement().executeQuery(
					"SELECT * FROM `artist`" +
							condition + ";"
			);

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

	/**
	 * Get all artists in database
	 * @return List of Arist instance
	 */
	public static List<Artist> getAllArtists() {
		return getArtists("");
	}


	/**
	 * Select one artist by id
	 *
	 * @param id: PK of artist
	 * @return Artist instance
	 * @throws NotFoundException
	 */
	static public Artist selectById(int id) {
		List<Artist> list = getArtists("WHERE id = '" + id + "'");
		if (list.size() == 0)
			throw new NotFoundException("Cannot find artist by id '"+id+"'");

		return list.get(0);
	}


	/**
	 * Name of this artist
	 */
	public String name;

	/**
	 * Activity start date of this artist
	 */
	public String activityStartDate;


	/**
	 * Constructor of Artist Model
	 *   Generally used in result of selection
	 * @param id
	 * @param name
	 * @param activityStartDate
	 */
	public Artist(int id,
				  String name,
				  String activityStartDate) {

		super("artist");
		this.id = id;
		this.name = name;
		this.activityStartDate = activityStartDate;
	}

	/**
	 * Constructor of Artist Model with no id (= not saved to database yet)
	 *   Generally used to make new album
	 * @param name
	 * @param activityStartDate
	 */
	public Artist(String name,
				  String activityStartDate) {

		this(-1, name, activityStartDate);
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

	/**
	 * Get attribute name and value set that is not a key.
	 * @return (name-value) set of attributes
	 */
	@Override
	public Map<String, String> getSubAttributes() {
		Map<String, String > ret = new HashMap<>();
		ret.put("name", name);
		ret.put("activity_start_date", activityStartDate);
		return ret;
	}

	/**
	 * Return ID of this model to show identifying number
	 * @return id
	 */
	@Override
	public int getID() {
		return this.id;
	}

	/**
	 * Return name of this model to show readable string
	 * @return name
	 */
	@Override
	public String getName() {
		return this.name;
	}
}
