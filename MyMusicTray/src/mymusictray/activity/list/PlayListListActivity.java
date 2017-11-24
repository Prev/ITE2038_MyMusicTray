package mymusictray.activity.list;

import mymusictray.activity.Activity;
import mymusictray.model.PlayList;
import mymusictray.model.User;
import mymusictray.util.IOUtil;

public class PlayListListActivity extends Activity {

	private User user;

	public PlayListListActivity(Activity previousActivity, User user) {
		super(previousActivity);
		this.user = user;
	}

	@Override
	public void start() {
		IOUtil.printSection("View PlayLists", '-');

		System.out.printf("| %-2s | %-45s | %-12s |\n", "ID", "Name", "Music Counts");
		IOUtil.printSection("", '-');

		for (PlayList p: PlayList.getAllPlaylists(user)) {
			System.out.printf("| %-2d | %-45s | %-12d |\n",
					p.id,
					p.name,
					p.getMusicCount()
			);
		}
		System.out.println("");

		(new ListSelectingActivity<PlayList>(this.previousActivity, PlayList.getAllPlaylists(user)) {
			@Override
			public void operate(PlayList model) {
				// TODO
				System.err.println("Currently unsupported feature");
			}
		}).start();
	}
}
