package mymusictray.activity.auth;

import mymusictray.activity.Activity;
import mymusictray.activity.MenuActivity;
import mymusictray.util.IOUtil;

public class LoginFailedActivity extends MenuActivity {

	String message;
	Activity previousActivity;

	public LoginFailedActivity(String message, Activity previousActivity) {
		this.message = message;
		this.previousActivity = previousActivity;
	}

	@Override
	public void start() {
		IOUtil.printPopup("Login Failed", message);
		super.start();
	}

	@Override
	public String getFirstMenuTitle() {
		return "Go Home";
	}

	@Override
	public String[] getMenu() {
		return new String[] {
				"Try Again"
		};
	}

	@Override
	public void operate(int choice) {
		// On try-again
		previousActivity.start();
	}

}
