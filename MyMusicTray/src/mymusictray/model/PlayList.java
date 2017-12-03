package mymusictray.model;

import mymusictray.core.Context;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayList extends StrongTypeModel implements ListableModel {

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

	/**
	 * Get all artists in database
	 * @return List of Arist instance
	 */
	public static List<PlayList> getAllPlaylists(User owner) {
		List<PlayList> ret = new ArrayList<>();

		try {
			ResultSet rs = Context.getDatabaseDriver().getStatement().executeQuery(
					"SELECT *, (SELECT COUNT(*) FROM playlist_item WHERE id = playlist.id) AS cnt\n" +
					"FROM playlist\n" +
					"WHERE owner = '"+owner.id+"'"
			);

			while (rs.next()) {
				ret.add(new ShallowPlayList(
						rs.getInt("id"),
						rs.getString("name"),
						owner,
						rs.getInt("cnt")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public static PlayList getPlayListById(int id) {
		PlayList model = null;
		try {
			ResultSet rs = Context.getDatabaseDriver().getStatement().executeQuery(
					"SELECT playlist.* , music.id AS music_id, music.title AS music_title, music.track_no AS music_track_no\n" +
							"FROM playlist\n" +
						"LEFT JOIN playlist_item ON playlist_item.playlist_id = playlist.id\n" +
						"LEFT JOIN music ON music.id = playlist_item.music_id\n" +
						"WHERE playlist.id = '"+id+"'"
			);

			while (rs.next()) {
				if (model == null) {
					model = new PlayList(
							rs.getInt("id"),
							rs.getString("name"),
							null,
							null
					);
				}
				if (rs.getString("music_id") != null) {
					model.musics.add(new Music(
							rs.getInt("music_id"),
							rs.getString("music_title"),
							null,
							rs.getInt("music_track_no"),
							null
					));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}

	public String name;
	public User owner;
	public List<Music> musics;

	public PlayList(int id,
				 String name,
				 User owner,
				 List<Music> musics) {

		super("playlist");
		this.id = id;
		this.name = name;
		this.owner = owner;

		if (musics != null)
			this.musics = musics;
		else
			this.musics = new ArrayList<>();
	}

	public PlayList(String name, User owner) {
		this(-1, name, owner, null);
	}

	public int getMusicCount() {
		return this.musics.size();
	}

	@Override
	public Map<String, String> getSubAttributes() {
		Map<String, String > ret = new HashMap<>();
		ret.put("name", name);
		ret.put("owner", Integer.toString(owner.id));
		return ret;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}


	/**
	 * Add relation with music (Add entity in `playlist_item` table)
	 * @param music: music to add
	 */
	public void addRelationWithMusic(Music music) {
		try {
			PreparedStatement stmt = Context.getConnection().prepareStatement("INSERT INTO `playlist_item`(`playlist_id`, `music_id`) VALUES (?, ?)");
			stmt.setInt(1, this.id);
			stmt.setInt(2, music.id);
			stmt.executeUpdate();

			if (!this.musics.contains(music))
				this.musics.add(music);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove relation with music (Remove entity in `playlist_item` table)
	 * @param music: music to remove
	 */
	public void removeRelationWithMusic(Music music) {
		try {
			PreparedStatement stmt = Context.getConnection().prepareStatement("DELETE FROM `playlist_item` WHERE playlist_id = ? AND music_id = ?");
			stmt.setInt(1, this.id);
			stmt.setInt(2, music.id);
			stmt.executeUpdate();

			if (this.musics.contains(music))
				this.musics.remove(music);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

/**
 * Playlist that not contains music (only count)
 */
class ShallowPlayList extends PlayList {

	private int musicCount;

	public ShallowPlayList(int id, String name, User owner, int musicCount) {
		super(id, name, owner, null);
		this.musicCount = musicCount;
	}

	public int getMusicCount() {
		return musicCount;
	}
}