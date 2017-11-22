package mymusictray.activity;

abstract public class Activity {

	protected Activity previousActivity;

	/**
	 * Activity Constructor
	 * @param previousActivity : Previous mymusictray.activity that calls current mymusictray.activity
	 */
	public Activity(Activity previousActivity) {
		this.previousActivity = previousActivity;
	}

	/**
	 * Start mymusictray.activity.
	 */
	abstract public void start();
}
