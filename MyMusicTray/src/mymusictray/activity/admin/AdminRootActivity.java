package mymusictray.activity.admin;

import mymusictray.activity.*;
import mymusictray.activity.list.*;
import mymusictray.model.Admin;

public class AdminRootActivity extends MenuActivity {

	private Admin model;

	public AdminRootActivity(Activity previousActivity, Admin model) {
		super(previousActivity, "Admin Menu");
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
				"Add new artist",
				"Add new album and music",
				"Register new admin",
				"Change password",
				"Remove this account",
		};
	}

	@Override
	public void operate(int choice) {
		// TODO: remove artist

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
				(new AlbumListActivity(this, this.model)).start();

				break;

			case 4:
				// Add New Artist
				(new ArtistInsertingActivity(this)).start();
				break;

			case 5:
				// Add New Album and Music
				(new AlbumAndMusicInsertingActivity(this)).start();
				break;

			case 6 :
				// Register New Admin
				(new RegisterNewAdminActivity(this)).start();
				break;

			case 7:
				// Change Password
				(new ChangeAdminPasswordActivity(this, this.model)).start();
				break;

			case 8:
				// Remove this account
				(new RemoveAdminActivity(this, this.model)).start();

				// Go home
				this.previousActivity.start();
				return;

		}

		this.start();
	}
}
