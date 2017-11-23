package mymusictray.activity.admin;

import mymusictray.activity.Activity;
import mymusictray.activity.MenuActivity;
import mymusictray.exception.NotFoundException;
import mymusictray.model.Album;
import mymusictray.model.Artist;
import mymusictray.model.Music;
import mymusictray.util.IOUtil;

public class AlbumManageActivity extends MenuActivity {

	private Album model;

	public AlbumManageActivity(Activity previousActivity, Album model) {
		super(previousActivity);
		this.model = model;
	}

	@Override
	public void start() {
		IOUtil.printSection("Album Management <" + model.title + ">");

		System.out.println("ID:\t\t\t\t" + this.model.id);
		System.out.println("Title:\t\t\t" + this.model.title);
		System.out.println("Release Date:\t" + this.model.releaseDate);
		System.out.println("Type:\t\t\t" + this.model.getReadableType());

		System.out.print("Artists:");
		for (int i = 0; i < this.model.artists.size(); i++) {
			if (i > 0) System.out.print("\t\t");
			Artist a = this.model.artists.get(i);
			System.out.printf("\t\t%s (id=%d)\n", a.name, a.id);
		}

		System.out.println("Musics:");
		for (Music m: this.model.musics)
			System.out.printf("\t\t%d. %s (id=%d)\n", m.trackNo, m.title, m.id);


		System.out.println("\n");
		IOUtil.printSection('-');
		super.start();
	}

	@Override
	public String[] getMenu() {
		return new String[] {
				"Add artist to this album",
				"Add music in this album",
				"Remove music in this album",
		};
	}

	@Override
	public void operate(int choice) {
		switch (choice) {
			case 1:
				// Add artist to this album
				IOUtil.printSection("Add new music to this album", '-');

				int artistId = IOUtil.inputNatural("Input artist id");

				try {
					Artist artist = Artist.selectById(artistId);
					artist.addRelationWithAlbum(this.model);

				}catch (NotFoundException e) {
					System.err.println("Cannot find artist by id '" + artistId + "'");
					break;
				}

				break;

			case 2:
				// Add music in this album
				IOUtil.printSection("Add new music to this album", '-');

				int trackNo = IOUtil.inputNatural("Input track no");
				String title = IOUtil.inputLine("Input title");

				Music newMusic = new Music(title, this.model, trackNo);
				newMusic.insert();

				IOUtil.printPopup("New music is inserted");
				break;

			case 3:
				// Remove music in this album
				IOUtil.printSection("Remove music in this album", '-');

				int musicId = IOUtil.inputNatural("Input music id");
				boolean removed = false;
				for (Music m: this.model.musics){
					if (m.id == musicId) {
						m.remove();
						this.model.musics.remove(m);
						removed = true;

						IOUtil.printPopup("Music '" + m.title + "' is removed");
						break;
					}
				}

				if (!removed) {
					System.err.println("Cannot find music by id '" + musicId + "'");
				}

				break;
		}

		this.start();
	}
}
