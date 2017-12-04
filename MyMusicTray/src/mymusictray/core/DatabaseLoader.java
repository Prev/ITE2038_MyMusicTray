package mymusictray.core;

import mymusictray.model.*;
import mymusictray.util.ConditionChecker;

import java.sql.*;

public class DatabaseLoader {

	static String[] TABLES = {"admin", "album", "album_artists", "album_genre",
			"artist", "music_artists", "music", "music_genre",
			"playlist", "playlist_item", "user"};


	/**
	 * Initialize and check database
	 * @param driver: DatabaseDriver instance
	 * @return true if initialization is succeed
	 * 		   false if failed
	 */
	static public boolean checkDatabase(DatabaseDriver driver) {
		try {
			DatabaseMetaData meta = driver.getConnection().getMetaData();
			ResultSet res = meta.getTables(null, null, "%", null);

			ConditionChecker cc = new ConditionChecker(TABLES);
			while (res.next())
				cc.check(res.getString(3)); // third column prints name of table

			if (cc.allChecked())
				// All tables are exists
				return true;

		} catch (ConditionChecker.UnexpectedConditionException e) {
			// Some other table exists on database

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}


	/**
	 * Setup tables and constraints
	 * @param driver: DatabaseDriver instance
	 */
	static public void setupTables(DatabaseDriver driver) {
		Statement stmt = driver.getStatement();

		try {
			System.out.println("Init admin table");
			Admin.initTable();

			System.out.println("Init album table");
			Album.initTable();

			System.out.println("Init artist table");
			Artist.initTable();

			System.out.println("Init music table");
			Music.initTable();

			System.out.println("Init playlist table");
			PlayList.initTable();

			System.out.println("Init user table");
			User.initTable();

			// Constraints
			System.out.println("Init constraints");
			stmt.execute("ALTER TABLE `album_artists`\n" +
					"  ADD CONSTRAINT `album_artists_ibfk_1` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
					"  ADD CONSTRAINT `album_artists_ibfk_2` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;\n"
			);
			stmt.execute("ALTER TABLE `album_genre`\n" +
					"  ADD CONSTRAINT `album_genre_ibfk_1` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;\n"
			);
			stmt.execute("ALTER TABLE `music`\n" +
					"  ADD CONSTRAINT `music_ibfk_1` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;\n"
			);
			stmt.execute("ALTER TABLE `music_artists`\n" +
					"  ADD CONSTRAINT `music_artists_ibfk_1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
					"  ADD CONSTRAINT `music_artists_ibfk_2` FOREIGN KEY (`music_id`) REFERENCES `music` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;\n"
			);
			stmt.execute("ALTER TABLE `music_genre`\n" +
					"  ADD CONSTRAINT `music_genre_ibfk_1` FOREIGN KEY (`music_id`) REFERENCES `music` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;\n"
			);
			stmt.execute("ALTER TABLE `playlist`\n" +
					"  ADD CONSTRAINT `playlist_ibfk_1` FOREIGN KEY (`owner`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;\n"
			);
			stmt.execute("ALTER TABLE `playlist_item`\n" +
					"  ADD CONSTRAINT `playlist_item_ibfk_1` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
					"  ADD CONSTRAINT `playlist_item_ibfk_2` FOREIGN KEY (`music_id`) REFERENCES `music` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;\n"
			);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
