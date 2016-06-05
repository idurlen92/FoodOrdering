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
	private final String KEY_IS_MAIN_DATA_SYNCED = "is_main_data_synced";
	private final String KEY_IS_USER_CHANGED = "is_user_changed";
	private final String KEY_LAST_USER_ID = "last_user_id";

	private static AppSettings instance = null;

	private SharedPreferences preferences;



	private AppSettings(Context context){
		preferences = context.getApplicationContext().getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
		if(preferences.getAll().isEmpty()) {
			populateValues();
		}
	}


	/**
	 * Returns instance of Settings object, with opened preferences file.
	 * @param context
	 * @return
	 */
	public static synchronized AppSettings getInstance(Context context){
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
		editor.putBoolean(KEY_IS_MAIN_DATA_SYNCED, false);
		editor.putBoolean(KEY_IS_USER_CHANGED, true);
		editor.putInt(KEY_LAST_USER_ID, -1);

		editor.commit();
	}


	public boolean isAutoSync(){ return preferences.getBoolean(KEY_IS_AUTO_SYNC, false); }

	public boolean isMainDataSynced(){ return preferences.getBoolean(KEY_IS_MAIN_DATA_SYNCED, false); }

	public boolean isUserChanged(){ return preferences.getBoolean(KEY_IS_USER_CHANGED, true); }

	public int getLastUserId(){ return preferences.getInt(KEY_LAST_USER_ID, - 1); }


	public boolean isSyncRequired(){ return !isMainDataSynced() || isUserChanged(); }


	public void setAutoSync(boolean isAutoSync){
		Editor editor = preferences.edit();
		editor.putBoolean(KEY_IS_AUTO_SYNC, isAutoSync);
		editor.commit();
	}


	public void setIsMainDataSynced(boolean isMainDataSynced){
		Editor editor = preferences.edit();
		editor.putBoolean(KEY_IS_MAIN_DATA_SYNCED, isMainDataSynced);
		editor.commit();
	}


	public void setIsUserChanged(boolean isUserChanged){
		Editor editor = preferences.edit();
		editor.putBoolean(KEY_IS_USER_CHANGED, isUserChanged);
		editor.commit();
	}


	public void setLastUserId(int lastUserId){
		if(lastUserId != getLastUserId()){
			setIsUserChanged(true);
		}
		Editor editor = preferences.edit();
		editor.putInt(KEY_LAST_USER_ID, lastUserId);
		editor.commit();
	}


}
