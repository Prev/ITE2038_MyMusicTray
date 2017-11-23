package mymusictray.activity.admin;

import mymusictray.activity.Activity;
import mymusictray.model.Admin;
import mymusictray.util.IOUtil;

public class ChangeAdminPasswordActivity extends Activity {

	private Admin model;

	public ChangeAdminPasswordActivity(Activity previousActivity, Admin model) {
		super(previousActivity);
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
