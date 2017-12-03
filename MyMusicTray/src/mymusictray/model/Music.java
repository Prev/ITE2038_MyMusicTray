package mymusictray.model;

import mymusictray.core.Context;
import mymusictray.exception.NotFoundException;

import java.sql.*;
import java.util.*;

public class Music extends StrongTypeModel implements ListableModel {

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
	static public Music that(int id, String title, Album album, int trackNo, List<String> genre) {
		if (id == 0) return null;

		if (repository.containsKey(id))
			return repository.get(id);
		else {
			Music newInstance = new Music(id, title, album, trackNo, genre);
			repository.put(id, newInstance);
			return newInstance;
		}
	}

	/**
	 * Get music, album of music, and artists of album with JOIN statement.
	 *
	 * @param condition: Additional condition of query.
	 * @return List of Music instance
	 */
	public static List<Music> getMusics(String condition) {
		Map<Integer, Music> musicDict = new HashMap<>();

		try {
			ResultSet rs = Context.getDatabaseDriver().getStatement().executeQuery(
					"SELECT music.*, music_genre.genre\n," +
							"artist.id AS artist_id, artist.name AS artist_name, artist.activity_start_date AS artist_act_start,\n" +
							"album.id AS album_id, album.title AS album_title, album.release_date AS album_release_date, album.type AS album_type \n" +
						"FROM music\n" +
						"LEFT JOIN album ON music.album_id = album.id\n" +
						"LEFT JOIN album_artists ON album_artists.album_id = music.album_id\n" +
						"LEFT JOIN artist ON artist.id = album_artists.artist_id\n" +
						"LEFT JOIN music_genre ON music_genre.music_id = music.id\n" +
						condition + ";"
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
						rs.getInt("track_no"),
						null
				);
				musicDict.put(musicModel.id, musicModel);

				// Add genre if not null
				String genre = rs.getString("genre");
				if (genre != null)
					musicModel.genre.add(genre);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return new ArrayList<>(musicDict.values());
	}

	/**
	 * Get all musics in database with album and artist
	 * @return List of Music instance
	 */
	public static List<Music> getAllMusics() {
		return getMusics("");
	}

	/**
	 * Get one music by id
	 *
	 * @param id: PK of artist
	 * @return Music instance
	 * @throws NotFoundException
	 */
	public static Music selectById(int id) {
		List<Music> list = getMusics("WHERE music.id = '" + id + "'");
		if (list.size() == 0)
			throw new NotFoundException("Cannot find artist by id '"+id+"'");

		return list.get(0);
	}


	public String title;
	public Album album;
	public int trackNo;
	public List<String> genre;

	public Music(int id,
				 String title,
				 Album album,
				 int trackNo,
				 List<String> genre) {

		super("music");
		this.id = id;
		this.title = title;
		this.album = album;
		this.trackNo = trackNo;

		if (this.album != null && !this.album.musics.contains(this))
			this.album.musics.add(this);

		if (genre == null)
			genre = new ArrayList<>();

		this.genre = genre;
	}

	public Music(String title,
				 Album album,
				 int trackNo) {
		this(-1, title, album, trackNo, null);
	}

	public String getGenreString() {
		return String.join(",", this.genre);
	}

	public void addGenreAndSave(String genre) {
		try {
			PreparedStatement stmt = Context.getConnection().prepareStatement("INSERT INTO `music_genre`(`music_id`, `genre`) VALUES (?, ?)");
			stmt.setInt(1, this.id);
			stmt.setString(2, genre);
			stmt.executeUpdate();

			this.genre.add(genre);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public Map<String, String> getSubAttributes() {
		Map<String, String > ret = new HashMap<>();
		ret.put("title", title);
		ret.put("album_id", Integer.toString(album.id));
		ret.put("track_no", Integer.toString(trackNo));
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
