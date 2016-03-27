package com.idurlen.foodordering.controller;
import android.os.Bundle;




/**
 * Simple class for holding data between Fragments, etc.
 * @author Ivan Durlen
 */
public class Messenger {

	private static Bundle bundle  = new Bundle();

	public static final String KEY_RESTAURANT_ID = "restaurant_id";


	public static Bundle getBundle() {
		return bundle;
	}

}
