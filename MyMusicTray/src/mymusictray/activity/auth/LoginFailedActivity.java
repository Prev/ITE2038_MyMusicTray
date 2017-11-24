package mymusictray.activity.auth;

import mymusictray.activity.MenuActivity;
import mymusictray.util.IOUtil;

public class LoginFailedActivity extends MenuActivity {

	String message;

	public LoginFailedActivity(String message) {
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
	public String[] getMenu() {
		return new String[] {
				"Go Home"
		};
	}

	@Override
	public void operate(int choice) {
		// Do nothing -> then go back to RootActivity
	}

}
