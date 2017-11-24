package mymusictray.activity.auth;

import mymusictray.activity.Activity;
import mymusictray.activity.user.UserRootActivity;
import mymusictray.exception.NotFoundException;
import mymusictray.model.User;
import mymusictray.util.IOUtil;

public class UserLoginActivity extends Activity {

	@Override
	public void start() {
		IOUtil.printSection("[User Login]");
		System.out.println("Input ID and password to login.");

		String id = IOUtil.inputLine("ID");
		String password = IOUtil.inputLine("Password");

		try {
			User model = User.selectByAccountId(id);

			if (model.password.equals(password)) {
				IOUtil.printPopup("Login Succeed", "Hello " + model.name);

				(new UserRootActivity(model)).start();

			}else {
				(new LoginFailedActivity("Id and password are not matched")).start();
			}

		}catch (NotFoundException e) {
			(new LoginFailedActivity("Account not exists")).start();
		}
	}
}
