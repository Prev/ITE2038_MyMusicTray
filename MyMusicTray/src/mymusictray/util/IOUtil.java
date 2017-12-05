package mymusictray.util;

import mymusictray.core.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IOUtil {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";

	private static final int SECTION_LENGTH = 90;

	private static void greenColor() {
		if (System.getProperty("os.name").indexOf("Windows") != -1) return;
		System.out.print(ANSI_GREEN);
	}

	private static void yellowColor() {
		if (System.getProperty("os.name").indexOf("Windows") != -1) return;
		System.out.print(ANSI_YELLOW);
	}

	private static void resetColor() {
		if (System.getProperty("os.name").indexOf("Windows") != -1) return;
		System.out.print(ANSI_RESET);
	}

	private static void yellow(String message) {
		yellowColor();
		System.out.println(message);
		resetColor();
	}

	private static void green(String message) {
		greenColor();
		System.out.println(message);
		resetColor();
	}


	/**
	 * Print section like `=========== [Admin Menu] ============`
	 * @param title: Title string of section (ex. Admin Menu)
	 * @param separator: Separator of section (default: `=`)
	 */
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

	/**
	 * Print section with default separator `=`
	 * @param title: Title string of section (ex. Admin Menu)
	 */
	public static void printSection(String title) {
		printSection(title, '=');
	}

	/**
	 * Print section with default title `null` (only prints separators)
	 * @param separator: Separator of section
	 */
	public static void printSection(char separator) {
		printSection("", separator);
	}


	/**
	 * Print popup like
	 *  -----------------------------------------------------
	 *		               <Login Succeed>
	 *		                  Hello James!
	 *	-----------------------------------------------------
	 * @param title: Title message of section enclosed with `<` and `></`>`
	 * @param message: Sub message of popup under title
	 */
	public static void printPopup(String title, String message) {
		greenColor();

		IOUtil.printSection('-');
		IOUtil.printSection("<" + title + ">", ' ');
		if (message != null)
			IOUtil.printSection(message, ' ');
		IOUtil.printSection('-');

		resetColor();
	}

	/**
	 * Print popup without sub message
	 * @param title: Title message of section enclosed with `<` and `></`>`
	 */
	public static void printPopup(String title) {
		printPopup(title, null);

	}


	/**
	 * Open choice selection popup and return choice by user inputs.
	 * If user input invalid value, this method opens choice popup until user inputs valid value.
	 *   (Guarantee for right value)
	 *
	 *   (ex.)
	 *    0: Exit
	 *    1: Admin Login
	 *    2: User Login
	 *    3: User Sign Up
	 *    > Input:
	 *
	 * @param choices: List of Choice (String). Number will be inserted before the message of the choice
	 * @param startFromZero: If true, choice will be started from zero
	 *                       Otherwise, choice will be started from one
	 *
	 * @return index of choice list that user selected
	 */
	public static int openChoices(String[] choices, boolean startFromZero) {
		Scanner scanner = Context.getScanner();
		int startIndex = startFromZero ? 0 : 1;

		for (int i = 0; i < choices.length; i++)
			System.out.printf("%d: %s\n", i+startIndex, choices[i]);

		System.out.print("> Input: ");
		int value = -1;

		try {
			value = scanner.nextInt();

		} catch (InputMismatchException e) {
			scanner.next();
			// Pass to finally clause

		} finally {
			if (value < startIndex || value >= choices.length + startIndex) {
				yellow("Invalid input. Please try again.");

				printSection('-');
				return openChoices(choices, startFromZero);
			}
		}

		scanner.nextLine();
		return value;
	}


	/**
	 * Input line value with default value
	 * @param message: Message to show before input
	 * @param defaultValue: Default value (if user do not input anything, default value will be returned)
	 * @return User typed string
	 */
	public static String inputLine(String message, String defaultValue) {
		System.out.print(message + " (default: " + defaultValue + "): ");
		String rst = Context.getScanner().nextLine();
		if (rst.equals("")) rst = defaultValue;

		return rst;
	}

	/**
	 * Input line value with guaranteed feature
	 *   If user inputs empty string (""), input popup will be re-opened.
	 * @param message: Message to show before input
	 * @return User typed string
	 */
	public static String inputLine(String message) {
		System.out.print(message + ": ");
		String rst = Context.getScanner().nextLine();

		if (rst.equals("")) {
			yellow("Invalid input. Please try again.");
			return inputLine(message);
		}
		return rst;
	}

	/**
	 * Input natural number with guaranteed feature
	 * @param message: Message to show before input
	 * @return User typed natural number
	 */
	public static int inputNatural(String message) {
		System.out.print(message + ": ");
		int value = -1;

		try {
			value = Context.getScanner().nextInt();
			//Context.getScanner().next();

		} catch (InputMismatchException e) {
			// Pass to finally clause

		} finally {
			if (value < -1) {
				yellow("Invalid input. Please try again.");

				printSection('-');
				return inputNatural(message);
			}
		}

		Context.getScanner().nextLine();
		return value;
	}


	/**
	 * Input line that's format is date (guarantee string is date)
	 *  (WARNING): if defaultValue is not date format, error will be generated when user inputs empty("") string
	 *
	 * @param message: Message to show before input
	 * @param defaultValue: Default value when user input nothing
	 * @return User typed string
	 */
	public static String inputDateString(String message, String defaultValue) {
		System.out.print(message + (defaultValue != null ? " (default: " + defaultValue + "): " : "") + ": ");
		String value = Context.getScanner().nextLine();

		if (defaultValue != null && value.equals(""))
			value = defaultValue;

		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(value);

			if (!value.equals(sdf.format(date)))
				date = null;

		} catch (ParseException ex) {
		}

		if (date == null) {
			yellow("Value is not date format. Please try again.");
			return inputDateString(message);

		} else {
			return value;
		}
	}

	/**
	 * Input line that's format is date (guarantee string is date) with no default value
	 * @param message: Message to show before input
	 * @return User typed string
	 */
	public static String inputDateString(String message) {
		return inputDateString(message, null);
	}
}
