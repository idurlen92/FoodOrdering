package com.idurlen.foodordering.controller;

import android.app.Activity;
import android.util.Log;

import com.idurlen.foodordering.utils.StringUtils;

import java.lang.reflect.Constructor;




public class ControllerFactory {


	/**
	 * Returns Controller instance for Activity or Fragment in parameter.
	 * @param activity
	 * @return
	 */
	public static Controller getInstance(Activity activity){
		String className = activity.getClass().getSimpleName();
		String contextName = className.substring(0, className.indexOf("Activity"));

		String strClassPath = "com.idurlen.foodordering.controller." +
				StringUtils.upperToName(contextName) + "Controller";

		Controller controller = null;
		try{
			Class controllerClass = Class.forName(strClassPath);
			Constructor constructor = controllerClass.getConstructor(new Class[]{Activity.class});
			controller = (Controller) constructor.newInstance(activity);
			Log.d("REFLECTION", "Created: " + controller.getClass().getSimpleName());
		}
		catch(Exception e){
			Log.e("REFLECTION", "Class " + strClassPath + " not found");
			e.printStackTrace();
		}

		return  controller;
	}

}
