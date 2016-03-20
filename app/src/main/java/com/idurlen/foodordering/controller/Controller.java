package com.idurlen.foodordering.controller;


import android.view.View;




/**
 * Controller interface of MVC Controller component.
 * TODO: refactor!!
 */
public interface Controller extends View.OnClickListener{


	/**
	 * Sets listeners and the data for the Activity.
	 */
	void activate();

	/**
	 * Set the listeners of activity widgets.
	 */
	void setListeners();

}
