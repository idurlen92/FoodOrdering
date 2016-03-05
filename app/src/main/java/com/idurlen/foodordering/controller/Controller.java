package com.idurlen.foodordering.controller;


import android.app.Activity;
import android.view.View;




/**
 *
 */
public interface Controller extends View.OnClickListener, View.OnTouchListener{


	/**
	 * Sets the activity and listeners.
	 * @param activity
	 */
	public void activate(Activity activity);

	/**
	 * Set the listeners of activity widgets.
	 */
	void setListeners();

}
