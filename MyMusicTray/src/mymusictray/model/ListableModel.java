package mymusictray.model;

/**
 * Guarantee that Model is listable, it means model has `ID` value and `Name` value to show
 *   Used in `activity.list.ListSelectingActivity`
 *
 * @author Prev (0soo.2@prev.kr)
 */
public interface ListableModel {

	/**
	 * Return ID of this model to show identifying number
	 * @return id
	 */
	int getID();

	/**
	 * Return name of this model to show readable string
	 * @return name or title
	 */
	String getName();
}
