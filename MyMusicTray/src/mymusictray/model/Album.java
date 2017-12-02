package mymusictray.model;

import mymusictray.core.Context;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Album extends StrongTypeModel implements ListableModel {

	static public void initTable() throws SQLException {
		Statement stmt = Context.getDatabaseDriver().getStatement();
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS `album` (\n" +
						"  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
						"  `title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
						"  `release_date` date NOT NULL,\n" +
						"  `type` tinyint(4) NOT NULL,\n" +
						"  PRIMARY KEY (`id`)\n" +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=1 ;\n"
		);
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS `album_genre` (\n" +
						"  `album_id` int(11) NOT NULL,\n" +
						"  `genre` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
						"  PRIMARY KEY (`album_id`,`genre`)\n" +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;\n"
		);
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS `album_artists` (\n" +
						"  `album_id` int(11) NOT NULL,\n" +
						"  `artist_id` int(11) NOT NULL,\n" +
						"  PRIMARY KEY (`album_id`,`artist_id`),\n" +
						"  KEY `artist_id` (`artist_id`)\n" +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;\n"
		);
	}

	// Album instance repository
	static private Map<Integer, Album> repository = new HashMap<>();

	/**
	 * Instead of creating a new instance each time,
	 *   returns an existing, already created instance with the same ID.
	 * @return Album instance
	 */
	static public Album that(int id, String title, String releaseDate, int type) {
		if (repository.containsKey(id))
			return repository.get(id);
		else {
			Album newInstance = new Album(id, title, releaseDate, type);
			repository.put(id, newInstance);
			return newInstance;
		}
	}

	/**
	 * Get all albums in database with artists
	 * @return List of Arist instance
	 */
	public static List<Album> getAllAlbums() {
		Map<Integer, Album> albumDict = new HashMap<>();

		try {
			ResultSet rs = Context.getDatabaseDriver().getStatement().executeQuery(
					/*"SELECT album.*, \n" +
							"artist.id AS artist_id, artist.name AS artist_name, artist.activity_start_date AS artist_act_start\n" +
						"FROM album, artist, album_artists\n" +
						"WHERE album_artists.album_id = album.id\n" +
						"AND album_artists.artist_id = artist.id"*/
					/*"SELECT album.*," +
							"artist.id AS artist_id, artist.name AS artist_name, artist.activity_start_date AS artist_act_start\n" +

							"FROM album\n" +
							"LEFT JOIN album_artists ON album.id = album_artists.album_id\n" +
							"LEFT JOIN artist ON album_artists.artist_id = artist.id;"*/
					"SELECT " +
							"artist.id AS artist_id, artist.name AS artist_name, artist.activity_start_date AS artist_act_start,\n" +
							"album.id AS album_id, album.title AS album_title, album.release_date AS album_release_date, album.type AS album_type \n," +
							"music.*\n" +
						"FROM album\n" +
						"LEFT JOIN album_artists ON album_artists.album_id = album.id\n" +
						"LEFT JOIN artist ON artist.id = album_artists.artist_id\n" +
						"LEFT JOIN music ON music.album_id = album.id;\n"
			);

			while (rs.next()) {
				Album albumModel = Album.that(
						rs.getInt("album_id"),
						rs.getString("album_title"),
						rs.getString("album_release_date"),
						rs.getInt("album_type")
				);
				Artist artistModel = Artist.that(
						rs.getInt("artist_id"),
						rs.getString("artist_name"),
						rs.getString("artist_act_start")
				);

				if (!albumModel.artists.contains(artistModel))
					albumModel.artists.add(artistModel);

				Music.that(
						rs.getInt("id"),
						rs.getString("title"),
						albumModel,
						rs.getInt("track_no")
				);
				// Add music to album

				albumDict.put(albumModel.id, albumModel);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return new ArrayList<>(albumDict.values());
	}



	static public final int TYPE_REGULAR = 1;
	static public final int TYPE_MINI = 2;
	static public final int TYPE_SINGLE = 3;

	/**
	 * Convert integer type to readable string
	 * @param type: Type of album
	 * @return Readable string
	 */
	static public String getReadableType(int type) {
		switch (type) {
			case TYPE_REGULAR:
				return "REGULAR";
			case TYPE_MINI:
				return "MINI";
			case TYPE_SINGLE:
				return "SINGLE";
			default:
				return null;
		}
	}

	public String title;
	public String releaseDate;
	public int type;
	public List<Artist> artists;
	public List<Music> musics;

	public Album(int id,
				 String title,
				 String releaseDate,
				 int type) {

		super("album");
		this.id = id;
		this.title = title;
		this.releaseDate = releaseDate;
		this.type = type;

		this.artists = new ArrayList<>();
		this.musics = new ArrayList<>();

		// TODO: genre
	}

	public Album(String title,
				 String releaseDate,
				 int type) {

		this(-1, title, releaseDate, type);
	}

	public String getArtistsString() {
		StringBuilder sb = new StringBuilder();
		for (Artist artist: this.artists) {
			sb.append(artist.name);
			sb.append(',');
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	public String getReadableType() {
		return getReadableType(this.type);
	}

	@Override
	public Map<String, String> getSubAttributes() {
		Map<String, String > ret = new HashMap<>();
		ret.put("title", title);
		ret.put("release_date", releaseDate);
		ret.put("type", Integer.toString(type));
		return ret;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.title;
	}
}
