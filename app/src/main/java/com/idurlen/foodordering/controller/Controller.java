package com.idurlen.foodordering.controller;


import android.view.View;




/**
 *
 */
public interface Controller extends View.OnClickListener, View.OnTouchListener{


	/**
	 * Sets listeners and the data for the Activity.
	 */
	void activate();

	/**
	 * Set the listeners of activity widgets.
	 */
	void setListeners();

}
