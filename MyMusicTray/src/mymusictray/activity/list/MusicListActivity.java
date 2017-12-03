package mymusictray.activity.list;

import mymusictray.activity.Activity;
import mymusictray.model.Music;
import mymusictray.util.IOUtil;

public class MusicListActivity extends Activity {

	@Override
	public void start() {
		IOUtil.printSection("View Music List", '-');

		System.out.printf("| %-2s | %-19s | %-17s | %-10s | %-17s | %-7s |\n", "ID", "Title", "Artists", "Genre", "Album", "Track No.");
		IOUtil.printSection("", '-');

		for (Music m: Music.getAllMusics()) {
			System.out.printf("| %-2d | %-19s | %-17s | %-10s | %-17s | %-7d |\n",
					m.id,
					m.title,
					m.album.getArtistsString(),
					m.getGenreString(),
					m.album.title,
					m.trackNo
			);
		}
		System.out.println("");
	}
}
