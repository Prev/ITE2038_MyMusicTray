package mymusictray.activity.admin;

import mymusictray.activity.Activity;
import mymusictray.exception.NotFoundException;
import mymusictray.model.Album;
import mymusictray.model.Artist;
import mymusictray.util.IOUtil;

public class AlbumAndMusicInsertingActivity implements Activity {

	@Override
	public void start() {
		IOUtil.printSection("[Add New Album and Music]");

		int artistId = IOUtil.inputNatural("Input artist id");
		Artist artist;

		try {
			artist = Artist.selectById(artistId);

		}catch (NotFoundException e) {
			System.err.println("Cannot find artist by id '" + artistId + "'. Return to menu.");
			return;
		}

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

		artist.addRelationWithAlbum(model);


		String genreList = IOUtil.inputLine("Input genre (separator: ',')");
		if (genreList != "") {
			for (String genre: genreList.split(","))
				model.addGenreAndSave(genre);
		}

		IOUtil.printPopup("New Album is created successfully", "Start managing this album");

		(new AlbumManageActivity(model)).start();

	}
}
