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

	private static String lastActivityName = "";

	private final String KEY_LOGGED_IN = "is_logged_in";
	private final String KEY_USERNAME = "username";
	private final String KEY_USER_ID = "user_id";

	private SharedPreferences preferences;


	private SessionManager(){ /* Private Constructor */}


	/**
	 * Returns instance of SessionManager object, with opened prefrences file.
	 * @param activity
	 * @return
	 */
	public static SessionManager getInstance(Activity activity){
		if(instance == null) {
			instance = new SessionManager();
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
			preferences = activity.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
		}
	}

	/**
	 * Returns true if user is logged in.
	 * @return boolean
	 */
	public boolean isLoggedIn(){
		return preferences.getBoolean(KEY_LOGGED_IN, false);
	}

	/**
	 * If the user is logged in, returns his username.
	 * @return
	 */
	public String getUsername(){
		return preferences.getString(KEY_USERNAME, "none");
	}

	/**
	 * If the user is logged in, returns his id in the database.
	 * @return
	 */
	public int getUserId(){
		return preferences.getInt(KEY_USER_ID, - 1);
	}


	/**
	 * Creates session and stores users username and id.
	 * @param username
	 * @param id
	 */
	public void createSession(String username, int id){
		Editor editor = preferences.edit();
		editor.putString(KEY_USERNAME, username);
		editor.putInt(KEY_USER_ID, id);
		editor.putBoolean(KEY_LOGGED_IN, true);
		editor.commit();
	}




	/**
	 * Destroys session and clears stored user values.
	 */
	public void destroySession(){
		Editor editor = preferences.edit();
		editor.putBoolean(KEY_LOGGED_IN, false);
		editor.remove(KEY_USER_ID).remove(KEY_USERNAME);
		editor.commit();
	}

}