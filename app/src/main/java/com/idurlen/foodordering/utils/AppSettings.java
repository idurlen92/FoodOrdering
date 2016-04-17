package com.idurlen.foodordering.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;




/**
 * Simple class for storing and retrieving Application Settings.
 * @author Ivan Durlen
 */
public class AppSettings {

	private static final String SETTINGS_FILE_NAME = "settings";

	private static AppSettings instance = null;

	private final String KEY_SYNC = "is_auto_sync";
	private final String KEY_LAST_SYNC = "last_sync";

	private SharedPreferences preferences;



	private AppSettings(Context context){
		preferences = context.getApplicationContext().getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
		if(!preferences.contains(KEY_SYNC)) {
			populateValues();
		}
	}


	/**
	 * Returns instance of Settings object, with opened preferences file.
	 * @param context
	 * @return
	 */
	public static AppSettings getInstance(Context context){
		if(instance == null) {
			instance = new AppSettings(context);
		}
		return instance;
	}




	/**
	 * TODO - more
	 * Creates and stores initial Keys/Values.
	 */
	private void populateValues(){
		Editor editor = preferences.edit();
		editor.putBoolean(KEY_SYNC, true);
		editor.putString(KEY_LAST_SYNC, DateTimeUtils.getCurrentTimeStampString());
		editor.commit();
	}


	public boolean isAutoSync(){ return preferences.getBoolean(KEY_SYNC, false); }

	public String getLastSyncTime(){ return preferences.getString(KEY_LAST_SYNC, "none"); }


	public void setAutoSync(boolean isAutoSync){
		Editor editor = preferences.edit();
		editor.putBoolean(KEY_SYNC, isAutoSync);
		editor.commit();
	}


}
