package mymusictray.activity.list;

import mymusictray.activity.Activity;
import mymusictray.activity.MenuActivity;
import mymusictray.activity.admin.AlbumManageActivity;
import mymusictray.model.Album;
import mymusictray.util.IOUtil;

import java.util.List;

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
	}

	public void startWithManaging() {
		this.start();

		(new ListSelectingActivity(this, Album.getAllAlbums())).start();
	}

	class ListSelectingActivity extends MenuActivity{

		private List<Album> albums;

		public ListSelectingActivity(Activity previousActivity, List<Album> albums) {
			super(previousActivity);
			this.albums = albums;
		}

		@Override
		public String getFirstMenuTitle() { return "Close List"; }

		@Override
		public String[] getMenu() {
			String[] ret = new String[albums.size()];
			for (int i = 0; i < albums.size(); i++)
				ret[i] = "Manage '" + (albums.get(i).title) + "'";
			return ret;
		}

		@Override
		public void operate(int choice) {
			(new AlbumManageActivity(
					this.previousActivity,
					this.albums.get(choice-1)
			)).start();
		}
	}
}
