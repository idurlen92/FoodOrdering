package com.idurlen.foodordering.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;




/**
 * Simple class for managing user session:
 *      - Login/Logout
 * @author Ivan Durlen
 */
public class SessionManager {

	private static SessionManager instance = null;

	private static final String PREFERENCES_FILE_NAME = "session";

	private final String KEY_LOGGED_IN = "is_logged_in";
	private final String KEY_USERNAME = "username";
	private final String KEY_USER_ID = "user_id";

	private Activity activity;
	private SharedPreferences preferences;


	private SessionManager(Activity activity){
		this.activity = activity;
	}


	public static SessionManager getInstance(Activity activity){
		if(instance == null) {
			instance = new SessionManager(activity);
		}
		instance.preferences = activity.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);

		return instance;
	}


	public boolean isLoggedIn(){
		return preferences.getBoolean(KEY_LOGGED_IN, false);
	}


	public String getUsername(){
		return preferences.getString(KEY_USERNAME, "none");
	}


	public int getUserId(){
		return preferences.getInt(KEY_USER_ID, - 1);
	}


	/**
	 * TODO
	 * @param username
	 * @param id
	 */
	public void logUserIn(String username, int id){
		Editor editor = preferences.edit();
		editor.putString(KEY_USERNAME, username);
		editor.putInt(KEY_USER_ID, id);
		editor.putBoolean(KEY_LOGGED_IN, true);
		editor.commit();
	}


	public void logUserOut(){
		Editor editor = preferences.edit();
		editor.putBoolean(KEY_LOGGED_IN, false);
		editor.remove(KEY_USER_ID).remove(KEY_USERNAME);
		editor.commit();
	}

}