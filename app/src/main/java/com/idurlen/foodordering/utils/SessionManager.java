package com.idurlen.foodordering.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;




/**
 * Simple class for managing user session - Login/Logout
 * @author Ivan Durlen
 */
public class SessionManager {

	private static SessionManager instance = null;

	private static final String PREFERENCES_FILE_NAME = "session";

	private final String KEY_FIRST_NAME = "first_name";
	private final String KEY_LAST_NAME = "last_name";
	private final String KEY_EMAIL = "email";
	private final String KEY_USERNAME = "username";
	private final String KEY_USER_ID = "user_id";
	private final String KEY_LOGGED_IN = "is_logged_in";

	private SharedPreferences preferences;


	private SessionManager(Context context){
		preferences = context.getApplicationContext().getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
	}


	/**
	 * Returns instance of SessionManager object, with opened prefrences file.
	 * @param context
	 * @return
	 */
	public static SessionManager getInstance(Context context){
		if(instance == null) {
			instance = new SessionManager(context);
		}
		return instance;
	}


	/**
	 * Returns true if user is logged in.
	 * @return boolean
	 */
	public boolean isLoggedIn(){
		return preferences.getBoolean(KEY_LOGGED_IN, false);
	}


	/**
	 * Returns First Name of user, if logged in.
	 * @return String
	 */
	public String getFirstName(){ return preferences.getString(KEY_FIRST_NAME, "none"); }

	/**
	 * Returns Last Name of user, if logged in.
	 * @return
	 */
	public String getLastName(){ return preferences.getString(KEY_LAST_NAME, "none"); }

	/**
	 * Returns First and Last Name of user, if logged in.
	 * @return String
	 */
	public String getName(){ return getFirstName() + " " + getLastName(); }

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
		editor.remove(KEY_FIRST_NAME);
		editor.remove(KEY_USER_ID);
		editor.remove(KEY_USERNAME);
		editor.remove(KEY_LAST_NAME);
		editor.commit();
	}

}