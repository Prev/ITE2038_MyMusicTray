package core;

import java.util.Scanner;

public class Context {

	static private Scanner scannerSingleton;
	static public Scanner getScanner() {
		if (scannerSingleton == null)
			scannerSingleton = new Scanner(System.in);

		return scannerSingleton;
	}

}
