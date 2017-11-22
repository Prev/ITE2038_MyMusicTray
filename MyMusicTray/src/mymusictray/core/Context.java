package mymusictray.core;

import java.sql.Connection;
import java.util.Scanner;

public class Context {

	static private Scanner scannerSingleton;

	/**
	 * Get public scanner of this program
	 * @return Scanner instance
	 */
	static public Scanner getScanner() {
		if (scannerSingleton == null)
			scannerSingleton = new Scanner(System.in);

		return scannerSingleton;
	}


	static private DatabaseDriver driverSingleton;
	static public void setDatabaseDriver(DatabaseDriver driver) {
		driverSingleton = driver;
	}

	static public DatabaseDriver getDatabaseDriver() {
		return driverSingleton;
	}


	static public Connection getConnection() {
		return driverSingleton.getConnection();
	}
}
