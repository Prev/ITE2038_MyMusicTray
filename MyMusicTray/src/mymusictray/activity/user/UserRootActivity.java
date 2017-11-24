package mymusictray.activity.user;

import mymusictray.activity.MenuActivity;
import mymusictray.activity.list.AlbumListActivity;
import mymusictray.activity.list.ArtistListActivity;
import mymusictray.activity.list.MusicListActivity;
import mymusictray.activity.list.PlayListListActivity;
import mymusictray.model.User;

public class UserRootActivity extends MenuActivity {

	private User model;

	public UserRootActivity(User model) {
		super("User Menu");
		this.model = model;
	}

	@Override
	public String getFirstMenuTitle() { return "Logout and Go Home"; }

	@Override
	public String[] getMenu() {
		return new String[] {
				"View music list",
				"View artist list",
				"View album list",
				"View and manage playlists",
				"Add new playlist",
				"Change password",
				"Remove this account",
		};
	}

	@Override
	public void operate(int choice) {
		switch (choice) {
			case 1 :
				// View Music List
				(new MusicListActivity()).start();
				break;

			case 2:
				// View Artist List
				(new ArtistListActivity()).start();
				break;

			case 3:
				// View Album List
				(new AlbumListActivity()).start();
				break;

			case 4:
				// View and manage playlists
				(new PlayListListActivity(this.model)).start();
				break;

			case 5:
				(new PlayListInsertingActivity(this.model)).start();
				break;

			case 6:
				// Change Password
				(new ChangeUserPasswordActivity(this.model)).start();
				break;

			case 7:
				// Remove this account
				(new RemoveUserActivity(this.model)).start();
				return; // Go home
		}

		start();
	}
}
