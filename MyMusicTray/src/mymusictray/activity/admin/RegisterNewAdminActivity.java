package mymusictray.activity.admin;

import mymusictray.activity.Activity;
import mymusictray.model.Admin;
import mymusictray.util.IOUtil;

public class RegisterNewAdminActivity extends Activity {

	@Override
	public void start() {
		IOUtil.printSection("Register New Admin", '-');

		String accountId = IOUtil.inputLine("Input account ID");
		String password = IOUtil.inputLine("Input password");
		String passwordRe = IOUtil.inputLine("Input password again");
		String name = IOUtil.inputLine("Input name");

		if (!password.equals(passwordRe)) {
			System.err.println("Please input same password");
			this.start();
			return;
		}

		Admin newAdminModel = new Admin(accountId, password, name);
		newAdminModel.insert();

		IOUtil.printPopup("New Admin is created");
	}
}
