package activity;

import util.IOUtil;

public class LoginFailedActivity extends MenuActivity {

	String message;

	public LoginFailedActivity(Activity previousActivity, String message) {
		super(previousActivity);
		this.message = message;
	}

	@Override
	public void start() {
		IOUtil.printPopup("Login Failed", message);
		super.start();
	}

	@Override
	public String getFirstMenuTitle() {
		return "Try again";
	}

	@Override
	String[] getMenu() {
		return new String[] {
				"Go Home"
		};
	}

	@Override
	void operate(int choice) {
		(new RootActivity()).start();
	}

}
