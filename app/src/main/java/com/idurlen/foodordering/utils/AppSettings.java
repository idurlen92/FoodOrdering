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

	private final String KEY_IS_AUTO_SYNC = "is_auto_sync";
	private final String KEY_IS_SCHEMA_CREATED = "is_synced";
	private final String KEY_LAST_SYNC = "last_sync";

	private static AppSettings instance = null;

	private SharedPreferences preferences;



	private AppSettings(Context context){
		preferences = context.getApplicationContext().getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
		if(!preferences.contains(KEY_IS_AUTO_SYNC)) {
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

		editor.putBoolean(KEY_IS_AUTO_SYNC, true);
		editor.putBoolean(KEY_IS_SCHEMA_CREATED, false);
		editor.putString(KEY_LAST_SYNC, "");

		editor.commit();
	}


	public boolean isAutoSync(){ return preferences.getBoolean(KEY_IS_AUTO_SYNC, false); }

	public boolean isSchemaCreated(){ return preferences.getBoolean(KEY_IS_SCHEMA_CREATED, false); }

	public String getLastSyncTime(){ return preferences.getString(KEY_LAST_SYNC, "none"); }


	public void setAutoSync(boolean isAutoSync){
		Editor editor = preferences.edit();
		editor.putBoolean(KEY_IS_AUTO_SYNC, isAutoSync);
		editor.commit();
	}


	public void setIsSchemaCreated(boolean isSchemaCreated){
		Editor editor = preferences.edit();
		editor.putBoolean(KEY_IS_SCHEMA_CREATED, isSchemaCreated);
		editor.commit();
	}


	public void setLastSyncTime(String syncTime){
		Editor editor = preferences.edit();
		editor.putString(KEY_LAST_SYNC, syncTime);
		editor.commit();
	}


}
