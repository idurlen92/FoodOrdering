package com.idurlen.foodordering.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.idurlen.foodordering.database.model.User;




/**
 * Simple class for managing user session - Login/Logout
 * @author Ivan Durlen
 */
public class SessionManager {

	private static SessionManager instance = null;

	private static final String PREFERENCES_FILE_NAME = "session";

	private final String KEY_USER_ID = "user_id";
	private final String KEY_FIRST_NAME = "first_name";
	private final String KEY_LAST_NAME = "last_name";
	private final String KEY_EMAIL = "email";
	private final String KEY_CITY = "city";
	private final String KEY_ADDRESS = "address";
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
	 * If the user is logged in, returns his id in the database.
	 * @return
	 */
	public int getUserId(){
		return preferences.getInt(KEY_USER_ID, - 1);
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
	public String getEmail(){
		return preferences.getString(KEY_EMAIL, "none");
	}

	/**
	 * If the user is logged in, returns his city.
	 * @return
	 */
	public String getCity(){
		return preferences.getString(KEY_CITY, "none");
	}

	/**
	 * If the user is logged in, returns his city.
	 * @return
	 */
	public String getAddress(){
		return preferences.getString(KEY_ADDRESS, "none");
	}



	/**
	 * Creates session and stores user data.
	 * @param user
	 */
	public void createSession(User user){
		Editor editor = preferences.edit();

		editor.putInt(KEY_USER_ID, user.getId());
		editor.putString(KEY_FIRST_NAME, user.getFirstName());
		editor.putString(KEY_LAST_NAME, user.getLastName());
		editor.putString(KEY_EMAIL, user.getEmail());
		editor.putString(KEY_CITY, user.getCity());
		editor.putString(KEY_ADDRESS, user.getAddress());
		editor.putBoolean(KEY_LOGGED_IN, true);

		editor.commit();
	}




	/**
	 * Destroys session and clears stored user values.
	 */
	public void destroySession(){
		Editor editor = preferences.edit();

		editor.remove(KEY_USER_ID);
		editor.remove(KEY_FIRST_NAME);
		editor.remove(KEY_LAST_NAME);
		editor.remove(KEY_EMAIL);
		editor.remove(KEY_CITY);
		editor.remove(KEY_ADDRESS);
		editor.remove(KEY_LOGGED_IN);

		editor.commit();
	}

}