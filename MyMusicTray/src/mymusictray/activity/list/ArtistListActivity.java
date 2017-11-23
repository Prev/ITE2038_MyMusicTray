package mymusictray.activity.list;

import mymusictray.activity.Activity;
import mymusictray.model.Artist;
import mymusictray.util.IOUtil;

public class ArtistListActivity extends Activity {
	public ArtistListActivity(Activity previousActivity) {
		super(previousActivity);
	}

	@Override
	public void start() {
		IOUtil.printSection("View Artist List", '-');

		System.out.printf("| %-2s | %-25s | %-15s |\n", "ID", "Name", "Act Start Date");
		IOUtil.printSection("", '-');

		for (Artist a: Artist.getAllArtists()) {
			System.out.printf("| %-2d | %-25s | %-15s |\n",
					a.id,
					a.name,
					a.activityStartDate
			);
		}
		System.out.println("");
	}
}
