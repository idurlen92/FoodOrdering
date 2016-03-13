package com.idurlen.foodordering.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;




/**
 * Simple class for storing and retrieving Application Settings.
 * @author Ivan Durlen
 */
public class Settings {

	private static Settings instance = null;

	private static final String SETTINGS_FILE_NAME = "settings";
	private final String KEY_SYNC = "auto_sync";

	private String lastActivityName = "";



	private SharedPreferences preferences;


	private Settings(){/* Private Constructor */}



	/**
	 * Returns instance of Settings object, with opened prefrences file.
	 * @param activity
	 * @return
	 */
	public static Settings getInstance(Activity activity){
		if(instance == null) {
			instance = new Settings();
		}
		instance.getPreferencesFile(activity);

		return instance;
	}


	/**
	 * Gets preferences file for the activity, if context is changed.
	 * @param activity
	 */
	private void getPreferencesFile(Activity activity){
		if(lastActivityName.compareTo(activity.getClass().getSimpleName()) != 0){
			lastActivityName = activity.getClass().getSimpleName();
			preferences = activity.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
		}
	}


}
