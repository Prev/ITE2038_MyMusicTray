package mymusictray.activity;

import mymusictray.util.IOUtil;

abstract public class MenuActivity extends Activity {

	private String title;

	/**
	 * Activity Constructor
	 */
	public MenuActivity() {}

	/**
	 * Activity Constructor with title
	 *
	 * @param title : Title of menu activity
	 */
	public MenuActivity(String title) {
		this.title = title;
	}

	/**
	 * Start mymusictray.activity.
	 * Show menu and do operation by user's input
	 */
	@Override
	public void start() {
		if (this.title != null)
			IOUtil.printSection("[" + this.title + "]");

		String[] menu = getMenu();
		String[] combinedMenu = new String[menu.length+1];

		for (int i = 0; i < menu.length; i++)
			combinedMenu[i+1] = menu[i];
		combinedMenu[0] = this.getFirstMenuTitle();

		int input = IOUtil.openChoices(combinedMenu,true);

		if (input != 0) {
			// Operate with choice
			this.operate(input);
		}else
			this.finish();
	}

	/**
	 * Get title of first menu.
	 *   First menu is generally going back to previous mymusictray.activity
	 *
	 * @return title
	 */
	public String getFirstMenuTitle() {
		return "Return to Previous menu";
	}

	/**
	 * Get menu list of mymusictray.activity.
	 * @return list of string
	 */
	abstract public String[] getMenu();

	/**
	 * Operation on selecting right choice
	 * @param choice: index of chosen menu
	 */
	abstract public void operate(int choice);

	/**
	 * Operation on close (= input zero)
	 */
	public void finish() {

	}
}
