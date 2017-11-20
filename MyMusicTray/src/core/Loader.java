package core;

import util.IOUtil;
import java.sql.SQLException;

public class Loader {

	/**
	 * Load config, database, tables to run program
	 */
	static public void load() {

		Config config = loadConfig();
		DatabaseDriver driver = loadDatabaseDriver(config);

		// Init database
		if (!DatabaseLoader.checkDatabase(driver)) {
			System.out.println("Some tables are missing. Starting setup of tables.");

			DatabaseLoader.setupTables(driver);

		}else {
			System.out.println("All tables are exists. Start Program.");
		}
	}

	/**
	 * Load config if saved file exists and create file by user's input if not exists.
	 *
	 * @return Config instance
	 */
	private static Config loadConfig() {
		Config config = Config.getConfig();

		if (config != null)
			System.out.println("Config is loaded from saved file.");

		else {
			// input database config
			IOUtil.printSection("Start Setup Database");

			String type = IOUtil.inputLine("Input type of database", "mysql");
			String port = IOUtil.inputLine("Input port", "3306");
			String databaseName = IOUtil.inputLine("Input database name");
			String userName = IOUtil.inputLine("Input user name");
			String password = IOUtil.inputLine("Input password");

			config = new Config(
					"jdbc:"+type+"://localhost:" + port + "/",
					databaseName,
					userName,
					password
			);

			Config.saveConfig(config);
			System.out.println("Config is saved to file.");
		}

		return config;
	}

	/**
	 * Load database driver.
	 * If there is error opening database, give two options
	 *     1. Exit program
	 *     2. Remove config and restart setup
	 *
	 * @param config: Config instance
	 * @return: DatabaseDriver instance
	 */
	private static DatabaseDriver loadDatabaseDriver(Config config) {
		DatabaseDriver driver = null;
		try {
			driver = new DatabaseDriver(config);

		} catch (SQLException e) {
			System.err.println(e.getMessage());

			int choice = IOUtil.openChoices(new String[]{
					"Exit",
					"Remove saved file and restart setup"
			}, true);

			switch (choice) {
				case 0:
					System.exit(-1);
					break;

				case 1:
					Config.remove();
					load();
					break;
			}
		}
		return driver;
	}


}
