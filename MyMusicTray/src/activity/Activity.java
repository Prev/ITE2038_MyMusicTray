package activity;

abstract public class Activity {

	protected Activity previousActivity;

	/**
	 * Activity Constructor
	 * @param previousActivity : Previous activity that calls current activity
	 */
	public Activity(Activity previousActivity) {
		this.previousActivity = previousActivity;
	}

	/**
	 * Start activity.
	 */
	abstract public void start();
}
