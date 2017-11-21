package model;

import core.Context;
import java.sql.*;
import java.util.*;

public class Music implements Model {

	static public void initTable() throws SQLException {
		Statement stmt = Context.getDatabaseDriver().getStatement();
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS `music` (" +
						"  `id` int(11) NOT NULL AUTO_INCREMENT," +
						"  `title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
						"  `lyrics` text COLLATE utf8mb4_unicode_ci NOT NULL," +
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
	static public Music that(int id, String title, String lyrics, Album album, int trackNo) {
		if (repository.containsKey(id))
			return repository.get(id);
		else {
			Music newInstance = new Music(id, title, lyrics, album, trackNo);
			repository.put(id, newInstance);
			return newInstance;
		}
	}

	/**
	 * Get all musics in database with album and artist
	 * @return List of Music instance
	 */
	public static List<Music> getAllMusics() {
		Map<Integer, Music> musicSet = new HashMap<>();

		try {
			ResultSet rs = Context.getDatabaseDriver().getStatement().executeQuery(
					"SELECT music.*," +
								"artist.id AS artist_id, artist.name AS artist_name, artist.activity_start_date AS artist_act_start," +
								"album.title AS album_title, album.release_date AS album_release_date, album.type AS album_type " +
						"FROM music, artist, album, album_artists\n" +
						"WHERE music.album_id = album.id\n" +
						"AND album_artists.album_id = album.id\n" +
						"AND album_artists.artist_id = artist.id"
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
						rs.getString("lyrics"),
						albumModel,
						rs.getInt("track_no")
				);
				musicSet.put(musicModel.id, musicModel);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return new ArrayList<>(musicSet.values());
	}


	public int id;
	public String title;
	public String lyrics;
	public Album album;
	public int trackNo;

	public Music(int id,
				 String title,
				 String lyrics,
				 Album album,
				 int trackNo) {

		this.id = id;
		this.title = title;
		this.lyrics = lyrics;
		this.album = album;
		this.trackNo = trackNo;
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
