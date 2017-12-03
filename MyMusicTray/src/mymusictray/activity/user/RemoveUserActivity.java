package mymusictray.activity.user;

import mymusictray.activity.Activity;
import mymusictray.model.User;
import mymusictray.util.IOUtil;

public class RemoveUserActivity implements Activity {

	private User model;

	public RemoveUserActivity(User model) {
		this.model = model;
	}

	@Override
	public void start() {
		IOUtil.printSection("Remove this account", '-');
		System.out.println("WARNING!! This action can not be undone once performed.");

		String password = IOUtil.inputLine("Input password");

		if (!this.model.password.equals(password)) {
			System.err.println("Password is wrong.");
			this.start();
			return;
		}

		this.model.remove();
		IOUtil.printPopup("Account is removed");
	}
}
