package mymusictray.activity.admin;

import mymusictray.activity.MenuActivity;
import mymusictray.model.Artist;
import mymusictray.util.IOUtil;

public class ArtistManageActivity extends MenuActivity {

	private Artist model;

	public ArtistManageActivity(Artist model) {
		this.model = model;
	}

	@Override
	public void start() {
		IOUtil.printSection("Artist Management <" + model.name + ">");

		System.out.println("ID:\t\t\t\t\t\t#" + this.model.id);
		System.out.println("Activity Start Time:\t" + this.model.activityStartDate);
		IOUtil.printSection('-');

		super.start();
	}

	@Override
	public String[] getMenu() {
		return new String[] {
				"Edit information",
				"[Warning] Remove this artist"
		};
	}

	@Override
	public void operate(int choice) {
		switch (choice) {
			case 1:
				// Edit information
				String name = IOUtil.inputLine("Input name of artist", this.model.name);
				String activityStartDate = IOUtil.inputDateString("Input activity start date (yyyy-MM-dd)");

				this.model.name = name;
				this.model.activityStartDate = activityStartDate;
				this.model.update();

				IOUtil.printPopup("Information is changed successfully");
				break;

			case 2:
				// Remove this artist
				this.model.remove();
				IOUtil.printPopup("Artist '" + this.model.name + "' is removed");
				break;
		}
	}
}
