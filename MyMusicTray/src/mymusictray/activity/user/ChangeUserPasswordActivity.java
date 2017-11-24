package mymusictray.activity.user;

import mymusictray.activity.Activity;
import mymusictray.model.User;
import mymusictray.util.IOUtil;

public class ChangeUserPasswordActivity extends Activity {

	private User model;

	public ChangeUserPasswordActivity(User model) {
		this.model = model;
	}

	@Override
	public void start() {
		IOUtil.printSection("Change password", '-');

		String password = IOUtil.inputLine("Input new password");
		String passwordRe = IOUtil.inputLine("Input new password again");

		if (!password.equals(passwordRe)) {
			System.err.println("Please input same password");
			this.start();
			return;
		}

		this.model.password = password;
		this.model.update();

		IOUtil.printPopup("Password is changed");
	}
}
