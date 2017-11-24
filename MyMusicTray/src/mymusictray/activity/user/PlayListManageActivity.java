package mymusictray.activity.user;

import mymusictray.activity.MenuActivity;
import mymusictray.exception.NotFoundException;
import mymusictray.model.Music;
import mymusictray.model.PlayList;
import mymusictray.util.IOUtil;

public class PlayListManageActivity extends MenuActivity {

	private PlayList model;

	public PlayListManageActivity(PlayList model) {
		this.model = model;
	}

	@Override
	public void start() {
		IOUtil.printSection("Playlist Management <" + model.name + ">");

		System.out.println("Musics:");
		for (Music m: this.model.musics)
			System.out.printf("\t\t%s (id=%d)\n", m.title, m.id);

		IOUtil.printSection('-');
		super.start();
	}

	@Override
	public String[] getMenu() {
		return new String[] {
				"Add music in this playlist",
				"Remove music in this playlist",
				"[Warning] Remove this playlist",
		};
	}

	@Override
	public void operate(int choice) {
		int musicId;

		switch (choice) {
			case 1:
				// Add music in this playlist
				IOUtil.printSection("Add music in this playlist", '-');

				musicId = IOUtil.inputNatural("Input music id");
				boolean keepGo = true;

				for (Music m: this.model.musics) {
					if (m.id == musicId) {
						System.err.println("Error: Music '" + m.title + "' is already in the playlist.");
						keepGo = false;
						break;
					}
				}

				if (!keepGo) break;

				try {
					Music music = Music.selectById(musicId);
					this.model.addRelationWithMusic(music);

				} catch (NotFoundException e) {
					System.err.println("Cannot find music by id '" + musicId + "'");
					break;
				}

				IOUtil.printPopup("New music is inserted");
				break;

			case 2:
				// Remove music in this album
				IOUtil.printSection("Remove music in this album", '-');

				musicId = IOUtil.inputNatural("Input music id");

				boolean removed = false;
				for (Music m : this.model.musics) {
					if (m.id == musicId) {
						this.model.removeRelationWithMusic(m);
						removed = true;

						IOUtil.printPopup("Music '" + m.title + "' is removed in this playlist");
						break;
					}
				}
				if (!removed)
					System.err.println("Cannot find music by id '" + musicId + "'");

				break;

			case 3:
				this.model.remove();
				IOUtil.printPopup("Playlist '" + this.model.name + "' is removed");
				return;
		}
		this.start();
	}
}
