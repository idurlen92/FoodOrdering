package com.idurlen.foodordering.utils;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;




/**
 * Simple class for holding data between Fragments, etc.
 * @author Ivan Durlen
 */
public class Messenger {

	// ---------- Maps ----------
	private static Bundle bundle  = new Bundle();
	private static Map<String, Object> mobjects = new HashMap<String, Object>();

	// ---------- Keys ----------
	public static final String DEFAULT_STRING = "none";
	public static final int DEFAULT_INT = -1;

	public static final String KEY_RESTAURANT_OBJECT = "restaurant_obj";
	public static final String KEY_SELECTED_DISHES_MAP = "selected_dishes";


	// ---------- Getters ----------
	public static int getInt(String key){
		return bundle.getInt(key, DEFAULT_INT);
	}

	public static String getString(String key){
		return bundle.getString(key, DEFAULT_STRING);
	}

	public static Object getObject(String key){
		return mobjects.get(key);
	}

	// ---------- Setters ----------
	public static void putInt(String key, int value){
		bundle.putInt(key, value);
	}

	public static void putString(String key, String value){
		bundle.putString(key, value);
	}

	public static void putObject(String key, Object value){
		mobjects.put(key, value);
	}


	public static void clearAll(){
		bundle.clear();
		mobjects.clear();
	}




}
