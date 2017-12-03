package mymusictray;

import mymusictray.activity.RootActivity;
import mymusictray.core.Loader;


/**
 * MyMusicTray
 *
 * Imaginary Music Player with MySQL
 * ITE2038
 * ------
 *
 * @author Prev (0soo.2@prev.kr)
 */
public class MyMusicTray {

	static private final String PROGRAM_TITLE = " __  __    _  _           __  __                     _                      _____                    _  _  \n" +
			"|  \\/  |  | || |    o O O|  \\/  |  _  _     ___     (_)     __       o O O |_   _|    _ _   __ _    | || | \n" +
			"| |\\/| |   \\_, |   o     | |\\/| | | +| |   (_-<     | |    / _|     o        | |     | '_| / _` |    \\_, | \n" +
			"|_|__|_|  _|__/   TS__[O]|_|__|_|  \\_,_|   /__/_   _|_|_   \\__|_   TS__[O]  _|_|_   _|_|_  \\__,_|   _|__/  \n" +
			"_|\"\"\"\"\"|_| \"\"\"\"| {======|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| {======|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_| \"\"\"\"| \n" +
			"\"`-0-0-'\"`-0-0-'./o--000'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'./o--000'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-' ";


	public static void main(String[] args) {
		Loader.load();

		System.out.println(PROGRAM_TITLE + "\n");

		(new RootActivity()).start();
	}

}
