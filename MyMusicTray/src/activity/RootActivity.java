package activity;

public class RootActivity extends MenuActivity {

	/**
	 * RootActivity Constructor
	 */
	public RootActivity() {
		super(null, "Home");
	}

	@Override
	String[] getMenu() {
		return new String[]{
				"Admin Login",
				"User Login",
				"User Sign Up",
		};
	}

	@Override
	void operate(int choice) {
		switch (choice) {
			case 1 :
				(new AdminLoginActivity(this)).start();
				break;

			case 2 :
				// TODO
				System.err.println("Currently unsupported");
				start();
				break;

			case 3 :
				// TODO
				System.err.println("Currently unsupported");
				start();
				break;
		}
	}
}
