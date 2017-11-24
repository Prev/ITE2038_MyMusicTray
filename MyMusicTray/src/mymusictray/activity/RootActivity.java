package mymusictray.activity;

import mymusictray.activity.auth.AdminLoginActivity;
import mymusictray.activity.auth.UserLoginActivity;
import mymusictray.activity.auth.UserSignupActivity;

public class RootActivity extends MenuActivity {

	/**
	 * RootActivity Constructor
	 */
	public RootActivity() {
		super("Home");
	}

	@Override
	public String getFirstMenuTitle() {
		return "Exit Program";
	}

	@Override
	public String[] getMenu() {
		return new String[]{
				"Admin Login",
				"User Login",
				"User Sign Up",
		};
	}

	@Override
	public void operate(int choice) {
		switch (choice) {
			case 1 :
				(new AdminLoginActivity()).start();
				break;

			case 2 :
				(new UserLoginActivity()).start();
				break;

			case 3 :
				(new UserSignupActivity()).start();
				break;
		}

		start();
	}
}
