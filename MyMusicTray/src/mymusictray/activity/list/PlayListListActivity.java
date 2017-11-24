package mymusictray.activity.list;

import mymusictray.activity.Activity;
import mymusictray.activity.user.PlayListManageActivity;
import mymusictray.model.PlayList;
import mymusictray.model.User;
import mymusictray.util.IOUtil;

public class PlayListListActivity extends Activity {

	private User user;

	public PlayListListActivity(User user) {
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

		PlayListListActivity outerActivity = this;

		(new ListSelectingActivity<PlayList>(PlayList.getAllPlaylists(user)) {
			@Override
			public void operate(PlayList model) {
				(new PlayListManageActivity(PlayList.getPlayListById(model.id))).start();
				outerActivity.start();
			}
		}).start();
	}
}
