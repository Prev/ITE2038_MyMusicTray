package mymusictray.exception;

public class ModelMisuseException extends RuntimeException {

	static public final String INSERT_MISUSE = "Cannot insert model that already inserted";
	static public final String UPDATE_MISUSE = "Cannot update model before inserted";
	static public final String REMOVE_MISUSE = "Cannot remove model before inserted";

	public ModelMisuseException(String message) {
		super(message);
	}

}
