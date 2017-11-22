package mymusictray.activity.list;

import mymusictray.activity.Activity;
import mymusictray.model.Music;
import mymusictray.util.IOUtil;

public class MusicListActivity extends Activity {

	public MusicListActivity(Activity previousActivity) {
		super(previousActivity);
	}

	@Override
	public void start() {
		IOUtil.printSection("View Music List", '-');

		System.out.printf("| %-2s | %-29s | %-17s | %-17s | %-7s |\n", "ID", "Title", "Artists", "Album", "Track No.");
		IOUtil.printSection("", '-');

		for (Music m: Music.getAllMusics()) {
			System.out.printf("| %-2d | %-29s | %-17s | %-17s | %-7d |\n",
					m.id,
					m.title,
					m.album.getArtistsString(),
					m.album.title,
					m.trackNo
			);
		}
		System.out.println("");

		this.previousActivity.start();
	}
}
