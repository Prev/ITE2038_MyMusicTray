package mymusictray.activity.list;

import mymusictray.activity.Activity;
import mymusictray.activity.admin.ArtistManageActivity;
import mymusictray.model.Admin;
import mymusictray.model.Artist;
import mymusictray.util.IOUtil;

public class ArtistListActivity implements Activity {

	private Admin adminInstance = null;

	public ArtistListActivity() {}

	public ArtistListActivity(Admin admin) {
		this.adminInstance = admin;
	}


	@Override
	public void start() {
		IOUtil.printSection("View Artist List", '-');

		System.out.printf("| %-3s | %-25s | %-15s |\n", "ID", "Name", "Act Start Date");
		IOUtil.printSection("", '-');

		for (Artist a: Artist.getAllArtists()) {
			System.out.printf("| #%-2d | %-25s | %-15s |\n",
					a.id,
					a.name,
					a.activityStartDate
			);
		}
		System.out.println("");


		if (adminInstance != null) {
			ArtistListActivity outerActivity = this;

			(new ListSelectingActivity<Artist>(Artist.getAllArtists()) {
				@Override
				public void operate(Artist model) {
					(new ArtistManageActivity(model)).start();
					outerActivity.start();
				}
			}).start();
		}
	}
}
