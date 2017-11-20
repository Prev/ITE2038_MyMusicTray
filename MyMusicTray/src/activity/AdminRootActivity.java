package activity;

import util.IOUtil;

public class AdminRootActivity extends MenuActivity {
	
	public AdminRootActivity(Activity previousActivity) {
		super(previousActivity, "Admin Menu");
	}

	@Override
	public String getFirstMenuTitle() { return "Logout and Go Home"; }

	@Override
	String[] getMenu() {
		return new String[] {
				"View Music List",
				"View Artist List",
				"View Album List",
				"Add New Artist",
				"Add New Album and Music",
		};
	}

	@Override
	void operate(int choice) {
		System.err.println("Currently unsupported");
		start();
	}
}
