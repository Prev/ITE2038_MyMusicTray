package core;

import java.io.*;

public class Config {

	private static final String FILE_NAME = "config.dat";

	public static Config getConfig() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
			return new Config(
					br.readLine(),
					br.readLine(),
					br.readLine(),
					br.readLine()
			);

		} catch (FileNotFoundException e) {
			return null;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveConfig(Config config) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

			bw.write(config.databaseUrl);
			bw.newLine();
			bw.write(config.databaseName);
			bw.newLine();
			bw.write(config.databaseUser);
			bw.newLine();
			bw.write(config.databasePassword);
			bw.newLine();

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void remove() {
		File file = new File(FILE_NAME);
		file.delete();
	}



	public String databaseUrl;
	public String databaseName;
	public String databaseUser;
	public String databasePassword;

	public Config(String databaseUrl,
				  String databaseName,
				  String databaseUser,
				  String databasePassword) {

		this.databaseUrl = databaseUrl;
		this.databaseName = databaseName;
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
	}

}
