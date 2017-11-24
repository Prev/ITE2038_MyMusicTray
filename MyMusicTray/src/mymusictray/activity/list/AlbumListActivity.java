package mymusictray.activity.list;

import mymusictray.activity.Activity;
import mymusictray.activity.admin.AlbumManageActivity;
import mymusictray.model.Admin;
import mymusictray.model.Album;
import mymusictray.model.ListableModel;
import mymusictray.util.IOUtil;


public class AlbumListActivity extends Activity {

	private Admin adminInstance = null;

	public AlbumListActivity(Activity previousActivity) {
		super(previousActivity);
	}

	public AlbumListActivity(Activity previousActivity, Admin admin) {
		super(previousActivity);
		this.adminInstance = admin;
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

		if (adminInstance != null) {
			(new ListSelectingActivity<Album>(this.previousActivity, Album.getAllAlbums()) {
				@Override
				public void operate(Album model) {
					(new AlbumManageActivity(this, model)).start();
				}
			}).start();
		}
	}

}
