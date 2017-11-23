package mymusictray.activity.user;

import mymusictray.activity.Activity;
import mymusictray.activity.MenuActivity;
import mymusictray.activity.list.AlbumListActivity;
import mymusictray.activity.list.ArtistListActivity;
import mymusictray.activity.list.MusicListActivity;
import mymusictray.model.User;

public class UserRootActivity extends MenuActivity {

	private User model;

	public UserRootActivity(Activity previousActivity, User model) {
		super(previousActivity, "User Menu");
		this.model = model;
	}

	@Override
	public String[] getMenu() {
		return new String[] {
				"View music list",
				"View artist list",
				"View album list",
				"Change password",
				"Remove this account",
		};
	}

	@Override
	public void operate(int choice) {
		switch (choice) {
			case 1 :
				// View Music List
				(new MusicListActivity(this)).start();
				break;

			case 2:
				// View Artist List
				(new ArtistListActivity(this)).start();
				break;

			case 3:
				// View Album List
				(new AlbumListActivity(this)).start();

				break;

			default :
				System.err.println("Currently Unsupported feature");
		}

		start();
	}
}
