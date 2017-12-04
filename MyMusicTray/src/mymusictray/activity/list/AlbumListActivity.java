package mymusictray.activity.list;

import mymusictray.activity.Activity;
import mymusictray.activity.admin.AlbumManageActivity;
import mymusictray.model.Admin;
import mymusictray.model.Album;
import mymusictray.util.IOUtil;


public class AlbumListActivity implements Activity {

	private boolean manageEntity;

	public AlbumListActivity(boolean manageEntity) {
		this.manageEntity = manageEntity;
	}
	public AlbumListActivity() {
		this(false);
	}

	@Override
	public void start() {
		IOUtil.printSection("View Album List", '-');

		System.out.printf("| %-3s | %-20s | %-10s | %-20s | %-10s | %-11s |\n", "ID", "Title", "Type", "Artists", "Genre", "Release Date");
		IOUtil.printSection("", '-');

		for (Album a: Album.getAllAlbums()) {
			System.out.printf("| #%-2d | %-20s | %-10s | %-20s | %-10s | %-11s |\n",
					a.id,
					a.title,
					a.getReadableType(),
					a.getArtistsString(),
					a.getGenreString(),
					a.releaseDate
			);
		}
		System.out.println("");

		if (manageEntity) {
			AlbumListActivity outerActivity = this;

			(new ListSelectingActivity<Album>(Album.getAllAlbums()) {
				@Override
				public void operate(Album model) {
					(new AlbumManageActivity(model)).start();
					outerActivity.start();
				}
			}).start();
		}
	}

}
