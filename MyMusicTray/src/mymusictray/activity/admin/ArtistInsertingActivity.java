package mymusictray.activity.admin;

import mymusictray.activity.Activity;
import mymusictray.model.Artist;
import mymusictray.util.IOUtil;

public class ArtistInsertingActivity extends Activity {

	public ArtistInsertingActivity(Activity previousActivity) {
		super(previousActivity);
	}

	@Override
	public void start() {
		IOUtil.printSection("[Add New Artist]");

		String name = IOUtil.inputLine("Input name of artist");
		String activityStartDate = IOUtil.inputDateString("Input activity start date (yyyy-MM-dd)");

		Artist model = new Artist(name, activityStartDate);
		model.insert();

		IOUtil.printPopup("New Artist is created");
	}
}
