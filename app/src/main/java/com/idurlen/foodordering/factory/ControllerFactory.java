package com.idurlen.foodordering.factory;

import android.app.Activity;
import android.util.Log;

import com.idurlen.foodordering.controller.Controller;

import java.lang.reflect.Constructor;




public class ControllerFactory extends  FactoryMethod{


	/**
	 * Returns Controller instance for Activity or Fragment in parameter.
	 * @param activity
	 * @return
	 */
	public static Controller getInstance(Activity activity){
		Controller controller = null;

		try{
			Class controllerClass = getComponentClass(activity.getClass().getSimpleName(), "activity", "controller", "controller");
			Constructor constructor = controllerClass.getConstructor(new Class[]{Activity.class});
			controller = (Controller) constructor.newInstance(activity);
			Log.d("REFLECTION", "Created: " + controller.getClass().getSimpleName());
		}
		catch(Exception e){
			Log.e("REFLECTION", "Class not found");
			e.printStackTrace();
		}

		return  controller;
	}

}
