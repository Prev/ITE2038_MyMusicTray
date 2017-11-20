package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IOUtil {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	private static final int SECTION_LENGTH = 90;

	public static void printSection(String title, char separator) {
		StringBuilder stringBuilder = new StringBuilder();
		int separatorCounts = (SECTION_LENGTH - title.length()) / 2;

		if (title != null && title.length() > 0)
			--separatorCounts;

		for (int i = 0; i < separatorCounts; i++) stringBuilder.append(separator);

		if (title != null && title.length() > 0) {
			stringBuilder.append(' ');
			stringBuilder.append(title);
			stringBuilder.append(' ');
		}

		for (int i = 0; i < separatorCounts; i++) stringBuilder.append(separator);

		System.out.println(stringBuilder.toString());
	}
	public static void printSection(String title) {
		printSection(title, '=');
	}
	public static void printSection(char separator) {
		printSection("", separator);
	}


	public static int openChoices(Scanner scanner, String[] choices) {
		for (int i = 0; i < choices.length; i++)
			System.out.printf("%d: %s\n", i+1, choices[i]);

		System.out.print("Input: ");
		int value = 0;

		try {
			value = scanner.nextInt();

		} catch (InputMismatchException e) {
			scanner.next();
			// Pass to finally clause

		} finally {
			if (value <= 0 || value > choices.length) {
				System.out.println(ANSI_YELLOW + "Invalid input. Please try again." + ANSI_RESET);
				printSection('-');

				return openChoices(scanner, choices);
			}
		}

		return value;
	}


	public static String inputLine(Scanner scanner, String message, String defaultValue) {
		System.out.print(message + " (default: " + defaultValue + "): ");
		String rst = scanner.nextLine();
		if (rst.equals("")) rst = defaultValue;

		return rst;
	}

	public static String inputLine(Scanner scanner, String message) {
		System.out.print(message + ": ");
		String rst = scanner.nextLine();

		if (rst.equals("")) {
			System.out.println(ANSI_YELLOW + "Invalid input. Please try again." + ANSI_RESET);
			return inputLine(scanner, message);
		}
		return rst;
	}
}
