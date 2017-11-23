package mymusictray.model;

import mymusictray.core.Context;
import mymusictray.exception.ModelMisuseException;

import java.sql.*;
import java.util.*;

public class Music implements Model {

	static public void initTable() throws SQLException {
		Statement stmt = Context.getDatabaseDriver().getStatement();
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS `music` (" +
						"  `id` int(11) NOT NULL AUTO_INCREMENT," +
						"  `title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
						"  `album_id` int(11) NOT NULL," +
						"  `track_no` int(11) NOT NULL," +
						"  PRIMARY KEY (`id`)," +
						"  KEY `album_id` (`album_id`)" +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=1 ;"
		);
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS `music_genre` (\n" +
						"  `music_id` int(11) NOT NULL,\n" +
						"  `genre` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,\n" +
						"  PRIMARY KEY (`music_id`,`genre`)\n" +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;"
		);
	}

	// Music instance repository
	static private Map<Integer, Music> repository = new HashMap<>();

	/**
	 * Instead of creating a new instance each time,
	 *   returns an existing, already created instance with the same ID.
	 * @return Music instance
	 */
	static public Music that(int id, String title, Album album, int trackNo) {
		if (repository.containsKey(id))
			return repository.get(id);
		else {
			Music newInstance = new Music(id, title, album, trackNo);
			repository.put(id, newInstance);
			return newInstance;
		}
	}

	/**
	 * Get all musics in database with album and artist
	 * @return List of Music instance
	 */
	public static List<Music> getAllMusics() {
		Map<Integer, Music> musicDict = new HashMap<>();

		try {
			ResultSet rs = Context.getDatabaseDriver().getStatement().executeQuery(
					/*"SELECT music.*," +
								"artist.id AS artist_id, artist.name AS artist_name, artist.activity_start_date AS artist_act_start," +
								"album.title AS album_title, album.release_date AS album_release_date, album.type AS album_type " +
						"FROM music, artist, album, album_artists\n" +
						"WHERE music.album_id = album.id\n" +
						"AND album_artists.album_id = album.id\n" +
						"AND album_artists.artist_id = artist.id"*/
					"SELECT music.*,\n" +
							"artist.id AS artist_id, artist.name AS artist_name, artist.activity_start_date AS artist_act_start,\n" +
							"album.title AS album_title, album.release_date AS album_release_date, album.type AS album_type \n" +
						"FROM music\n" +
						"LEFT JOIN album ON album.id = music.album_id\n" +
						"LEFT JOIN album_artists ON music.album_id = album_artists.album_id\n" +
						"LEFT JOIN artist ON album_artists.artist_id = artist.id;\n"
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

				Music musicModel = Music.that(
						rs.getInt("id"),
						rs.getString("title"),
						albumModel,
						rs.getInt("track_no")
				);
				musicDict.put(musicModel.id, musicModel);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return new ArrayList<>(musicDict.values());
	}


	public int id;
	public String title;
	public Album album;
	public int trackNo;

	public Music(int id,
				 String title,
				 Album album,
				 int trackNo) {

		this.id = id;
		this.title = title;
		this.album = album;
		this.trackNo = trackNo;

		if (this.album != null && !this.album.musics.contains(this))
			this.album.musics.add(this);

	}

	public Music(String title,
				 Album album,
				 int trackNo) {
		this(-1, title, album, trackNo);
	}


	@Override
	public void insert() {
		if (this.id != -1) {
			throw new ModelMisuseException(ModelMisuseException.INSERT_MISUSE);
		}
		try {
			PreparedStatement stmt = Context.getConnection().prepareStatement(
					"INSERT INTO music (title, album_id, track_no) values(?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS
			);
			stmt.setString(1, this.title);
			stmt.setInt(2, this.album.id);
			stmt.setInt(3, this.trackNo);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			this.id = rs.getInt(1); // Auto-incremented value

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
