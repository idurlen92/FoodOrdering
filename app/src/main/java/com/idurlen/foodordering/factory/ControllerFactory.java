package com.idurlen.foodordering.factory;

import android.util.Log;

import com.idurlen.foodordering.controller.Controller;

import java.lang.reflect.Constructor;




/**
 * @author Ivan Durlen
 */
public class ControllerFactory extends  FactoryMethod{

	/**
	 * Creates Controller component for Activity/Fragment passed as first arg.
	 * @param component Activity or Fragment
	 * @return {@link Controller}
	 */
	 public static Controller getInstance(Object component){
		Controller controller = null;

		try{
			String sFilter = component.getClass().getSuperclass().getSimpleName().contains("Fragment") ?
					"fragment" : "activity";
			Class controllerClass = getComponentClass(component.getClass().getSimpleName(), sFilter,
					"controller", Controller.class);
			Constructor constructor = controllerClass.getConstructor(new Class[]{ component.getClass().getSuperclass() });
			controller = (Controller) constructor.newInstance(component);
			Log.d("REFLECTION", "Created: " + controller.getClass().getSimpleName());
		}
		catch(Exception e){
			Log.e("REFLECTION", "Controller class not found for " + component.getClass().getSimpleName());
			e.printStackTrace();
		}

		return  controller;
	}

}
