package mymusictray.activity.admin;

import mymusictray.activity.Activity;
import mymusictray.model.Album;
import mymusictray.util.IOUtil;

public class AlbumAndMusicInsertingActivity extends Activity {

	public AlbumAndMusicInsertingActivity(Activity previousActivity) {
		super(previousActivity);
	}

	@Override
	public void start() {
		IOUtil.printSection("[Add New Album and Music");

		String name = IOUtil.inputLine("Input title of album");
		String releaseDate = IOUtil.inputDateString("Input release date of album (yyyy-MM-dd)");

		System.out.println("Select type of this album: ");
		int type = IOUtil.openChoices(new String[]{
			Album.getReadableType(Album.TYPE_REGULAR),
			Album.getReadableType(Album.TYPE_MINI),
			Album.getReadableType(Album.TYPE_SINGLE),
		}, false);

		Album model = new Album(name, releaseDate, type);
		model.insert();

		IOUtil.printPopup("New Album is created successfully", "Start managing this album");

		(new AlbumManageActivity(this, model)).start();

	}
}
