package com.idurlen.foodordering.factory;

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
	 public static Controller newInstance(Object component){
		Controller controller = null;

		try{
			Class controllerClass = getComponentClass(component, "controller", "Controller");
			Constructor constructor = controllerClass.getConstructor(new Class[]{ component.getClass().getSuperclass() });
			controller = (Controller) constructor.newInstance(component);
		}
		catch (ClassNotFoundException e){
			logError(e, "Class not found");
		}
		catch(IllegalAccessException e) {
			logError(e, "No Constructor");
		}
		catch (IllegalArgumentException e){
			logError(e, "Wrong number of Constructor params");
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return  controller;
	}


}
