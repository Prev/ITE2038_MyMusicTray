import core.Config;
import core.DatabaseDriver;
import core.Initializer;
import util.IOUtil;

import java.sql.SQLException;
import java.util.Scanner;


public class MyMusicTray {

	static private final String PROGRAM_TITLE = " __  __    _  _           __  __                     _                      _____                    _  _  \n" +
			"|  \\/  |  | || |    o O O|  \\/  |  _  _     ___     (_)     __       o O O |_   _|    _ _   __ _    | || | \n" +
			"| |\\/| |   \\_, |   o     | |\\/| | | +| |   (_-<     | |    / _|     o        | |     | '_| / _` |    \\_, | \n" +
			"|_|__|_|  _|__/   TS__[O]|_|__|_|  \\_,_|   /__/_   _|_|_   \\__|_   TS__[O]  _|_|_   _|_|_  \\__,_|   _|__/  \n" +
			"_|\"\"\"\"\"|_| \"\"\"\"| {======|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| {======|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_| \"\"\"\"| \n" +
			"\"`-0-0-'\"`-0-0-'./o--000'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'./o--000'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-' ";


	public static void main(String[] args) {
		System.out.println(PROGRAM_TITLE + "\n\n");

		Scanner scanner = new Scanner(System.in);

		// Get config
		Config config = Config.getConfig();
		if (config != null)
			IOUtil.printSection("Config is loaded from saved file");
		else {
			config = inputDatabaseConfig(scanner);
			Config.saveConfig(config);
			IOUtil.printSection("Saved");
		}


		// Init driver
		DatabaseDriver driver = null;
		try {
			driver = new DatabaseDriver(config);

		} catch (SQLException e) {
			System.err.println(e.getMessage());

			IOUtil.printSection('-');
			int choice = IOUtil.openChoices(scanner, new String[]{
					"Exit",
					"Remove saved file and restart setup"
			});

			switch (choice) {
				case 1:
					System.exit(-1);
					break;

				case 2:
					Config.remove();
					main(args);
					break;
			}
		}

		// Init database
		if (!Initializer.initDatabase(driver)) {
			IOUtil.printSection("Some tables are missing. Starting setup of tables.");

			Initializer.setupTables(driver);

		}else {
			System.out.println("All passed");
		}
	}

	private static Config inputDatabaseConfig(Scanner scanner) {
		IOUtil.printSection("Start Setup Database");

		String type = IOUtil.inputLine(scanner, "Input type of database", "mysql");
		String port = IOUtil.inputLine(scanner, "Input port", "3306");
		String databaseName = IOUtil.inputLine(scanner, "Input database name");
		String userName = IOUtil.inputLine(scanner, "Input user name");
		String password = IOUtil.inputLine(scanner, "Input password");

		return new Config(
				"jdbc:"+type+"://localhost:" + port + "/",
				databaseName,
				userName,
				password
		);
	}


}
