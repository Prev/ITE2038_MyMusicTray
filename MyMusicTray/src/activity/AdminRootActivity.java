package activity;

import model.Admin;
import util.IOUtil;

public class AdminRootActivity extends MenuActivity {

	private Admin model;

	public AdminRootActivity(Activity previousActivity, Admin model) {
		super(previousActivity, "Admin Menu");
		this.model = model;
	}

	@Override
	public String getFirstMenuTitle() { return "Logout and Go Home"; }

	@Override
	String[] getMenu() {
		return new String[] {
				"View music list",
				"View artist list",
				"View album list",
				"Add new artist",
				"Add new album and music",
				"Register new admin",
				"Change password",
				"Remove this account",
		};
	}

	@Override
	void operate(int choice) {
		String accountId, password, passwordRe, name;

		switch (choice) {
			case 6 :
				// Register New Admin
				IOUtil.printSection("Register New Admin", '-');

				accountId = IOUtil.inputLine("Input account ID");
				password = IOUtil.inputLine("Input password");
				passwordRe = IOUtil.inputLine("Input password again");
				name = IOUtil.inputLine("Input name");

				if (!password.equals(passwordRe)) {
					System.err.println("Please input same password");
					operate(6);
					return;
				}

				Admin newAdminModel = new Admin(accountId, password, name);
				newAdminModel.insert();

				IOUtil.printPopup("New Admin is created");
				break;


			case 7:
				// Change Password
				IOUtil.printSection("Change password", '-');

				password = IOUtil.inputLine("Input new password");
				passwordRe = IOUtil.inputLine("Input new password again");

				if (!password.equals(passwordRe)) {
					System.err.println("Please input same password");
					operate(7);
					return;
				}

				this.model.password = password;
				this.model.update();

				IOUtil.printPopup("Password is changed");
				break;

			case 8:
				// Remove this account
				IOUtil.printSection("Remove this account", '-');
				System.out.println("WARNING!! This action can not be undone once performed.");

				password = IOUtil.inputLine("Input password");

				if (!this.model.password.equals(password)) {
					System.err.println("Password is wrong.");
					operate(8);
					return;
				}

				this.model.remove();

				IOUtil.printPopup("Account is removed");
				this.previousActivity.start();
				return;

			default :
				// TODO
				System.err.println("Currently unsupported");

		}

		start();
	}
}
