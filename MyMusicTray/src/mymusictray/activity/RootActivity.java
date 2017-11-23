package mymusictray.activity;

import mymusictray.activity.auth.AdminLoginActivity;
import mymusictray.activity.auth.UserLoginActivity;
import mymusictray.activity.auth.UserSignupActivity;

public class RootActivity extends MenuActivity {

	/**
	 * RootActivity Constructor
	 */
	public RootActivity() {
		super(null, "Home");
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
				(new AdminLoginActivity(this)).start();
				break;

			case 2 :
				(new UserLoginActivity(this)).start();
				break;

			case 3 :
				(new UserSignupActivity(this)).start();
				break;
		}

		start();
	}
}
