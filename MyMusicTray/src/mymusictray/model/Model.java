package mymusictray.model;

import mymusictray.exception.ModelMisuseException;

public interface Model {

	/**
	 * Insert current mymusictray.model to database
	 *
	 * @throws ModelMisuseException when you try already inserted mymusictray.model
	 */
	void insert();


	/**
	 * Update current mymusictray.model to database
	 *
	 * @throws ModelMisuseException when you try to update mymusictray.model that is not in database yet
	 */
	void update();


	/**
	 * Remove current mymusictray.model from database
	 *
	 * @throws ModelMisuseException when you try to remove mymusictray.model that is not in database yet
	 */
	void remove();
}
