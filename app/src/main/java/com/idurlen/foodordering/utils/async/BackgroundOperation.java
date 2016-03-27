package com.idurlen.foodordering.utils.async;

/**
 * Interface witch is used to define background operation in {@link BackgroundTask}
 * @author Ivan Durlen
 */
public interface BackgroundOperation {


	/**
	 * Method witch is called in {@link BackgroundTask} method doInBackground.
	 * @return
	 */
	Object execInBackground();

	/**
	 * Method witch is called in {@link BackgroundTask} method onPostExecute.
	 * @param object
	 */
	void execAfter(Object object);

}
