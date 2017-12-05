package mymusictray.activity.auth;

import mymusictray.activity.Activity;
import mymusictray.model.User;
import mymusictray.util.IOUtil;

public class UserSignupActivity implements Activity {

	@Override
	public void start() {
		IOUtil.printSection("User Signup", '-');

		String accountId = IOUtil.inputLine("Input account ID");
		String password = IOUtil.inputLine("Input password");
		String passwordRe = IOUtil.inputLine("Input password again");

		if (!password.equals(passwordRe)) {
			System.err.println("Password is not equal. Stop SignUp.");
			return;
		}

		String name = IOUtil.inputLine("Input name");
		String birthday = IOUtil.inputDateString("Input birthday (yyyy-MM-dd)");
		String emailAddress = IOUtil.inputLine("Input email address");
		String phoneNumber = IOUtil.inputLine("Input phone number");


		User newUserModel = new User(accountId, password, name, birthday, emailAddress, phoneNumber);
		newUserModel.insert();

		IOUtil.printPopup("SignUp completed!");
	}
}
