package mymusictray.activity;

import mymusictray.util.IOUtil;

abstract public class MenuActivity extends Activity {

	private String title;

	/**
	 * Activity Constructor
	 *
	 * @param previousActivity : Previous mymusictray.activity that calls current mymusictray.activity
	 */
	public MenuActivity(Activity previousActivity) {
		super(previousActivity);
	}

	/**
	 * Activity Constructor with title
	 *
	 * @param previousActivity : Previous mymusictray.activity that calls current mymusictray.activity
	 */
	public MenuActivity(Activity previousActivity, String title) {
		super(previousActivity);
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
		if (input == 0) {
			if (this.previousActivity == null) {
				// If previousActivity is null, exit program (=root)
				System.exit(-1);
			}else {
				// Generally go to previous mymusictray.activity
				this.previousActivity.start();
			}

		}else {
			// Operate with choice
			this.operate(input);
		}
	}

	/**
	 * Get title of first menu.
	 *   First menu is generally going back to previous mymusictray.activity
	 *
	 * @return title
	 */
	public String getFirstMenuTitle() {
		if (this.previousActivity != null)
			return "Return to Previous menu";
		else
			return "Exit";
	}

	/**
	 * Get menu list of mymusictray.activity.
	 * @return list of string
	 */
	abstract public String[] getMenu();

	/**
	 * Operation of acting.
	 * @param choice: index of chosen menu
	 */
	abstract public void operate(int choice);
}
