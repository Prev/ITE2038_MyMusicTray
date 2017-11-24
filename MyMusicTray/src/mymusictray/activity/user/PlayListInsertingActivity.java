package mymusictray.activity.user;

import mymusictray.activity.Activity;
import mymusictray.model.PlayList;
import mymusictray.model.User;
import mymusictray.util.IOUtil;

public class PlayListInsertingActivity extends Activity {

	private User userModel;

	public PlayListInsertingActivity(User model) {
		this.userModel = model;
	}

	@Override
	public void start() {
		IOUtil.printSection("[Add New Playlist]");

		String name = IOUtil.inputLine("Input name of playlist");

		PlayList model = new PlayList(name, userModel);
		model.insert();
		IOUtil.printPopup("New Playlist is created successfully", "Start managing this playlist");

		(new PlayListManageActivity(model)).start();
	}
}
