package com.idurlen.foodordering.utils.async;

/**
 * Interface witch is used to define background operation in {@link BackgroundTask}
 * @author Ivan Durlen
 */
public abstract class BackgroundOperation {


	/**
	 * Optional method witch is called in {@link BackgroundTask} method onPreExecute.
	 */
	public void execBefore(){/* Not implemented */}

	/**
	 * Method witch is called in {@link BackgroundTask} method doInBackground.
	 * @return
	 */
	 public abstract Object execInBackground();

	/**
	 * Method witch is called in {@link BackgroundTask} method onPostExecute.
	 * @param object
	 */
	public void execAfter(Object object){/* NOT implemented */}

}
