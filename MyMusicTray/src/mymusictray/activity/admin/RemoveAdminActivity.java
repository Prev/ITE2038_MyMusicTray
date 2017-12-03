package mymusictray.activity.admin;

import mymusictray.activity.Activity;
import mymusictray.model.Admin;
import mymusictray.util.IOUtil;

public class RemoveAdminActivity implements Activity {

	private Admin model;

	public RemoveAdminActivity(Admin model) {
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
