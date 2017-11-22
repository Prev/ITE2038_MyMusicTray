package activity;

import model.Album;
import util.IOUtil;

public class AlbumListActivity extends Activity {


	public AlbumListActivity(Activity previousActivity) {
		super(previousActivity);
	}

	@Override
	public void start() {
		IOUtil.printSection("View Album List", '-');

		System.out.printf("| %-2s | %-25s | %-10s | %-25s | %-11s |\n", "ID", "Title", "Type", "Artists", "Release Date");
		IOUtil.printSection("", '-');

		for (Album a: Album.getAllAlbums()) {
			System.out.printf("| %-2d | %-25s | %-10s | %-25s | %-11s |\n",
					a.id,
					a.title,
					a.getReadableType(),
					a.getArtistsString(),
					a.releaseDate
			);
		}
		System.out.println("");

		this.previousActivity.start();
	}
}
