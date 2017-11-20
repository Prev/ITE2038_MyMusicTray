package activity;

import exception.NotFoundException;
import model.Admin;
import util.IOUtil;

public class AdminLoginActivity extends Activity {

	public AdminLoginActivity(Activity previousActivity) {
		super(previousActivity);
	}

	@Override
	public void start() {
		IOUtil.printSection("[Admin Login]");
		System.out.println("Input ID and password to login.");

		String id = IOUtil.inputLine("ID");
		String password = IOUtil.inputLine("Password");

		try {
			Admin model = Admin.selectByAccountId(id);

			if (model.password.equals(password)) {
				IOUtil.printSection('-');
				IOUtil.printSection("<Login Succeed>", ' ');
				IOUtil.printSection('-');

				// Root Activity is previous one
				(new AdminRootActivity(this.previousActivity)).start();

			}else {
				(new LoginFailedActivity(
						this,
						"Id and password are not matched"
				)).start();
			}

		}catch (NotFoundException e) {
			(new LoginFailedActivity(
					this,
					"Account not exists"
			)).start();
		}


	}
}