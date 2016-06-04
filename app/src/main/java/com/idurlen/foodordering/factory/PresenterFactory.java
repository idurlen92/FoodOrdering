package com.idurlen.foodordering.factory;

import com.idurlen.foodordering.presenter.Presenter;

import java.lang.reflect.Constructor;




/**
 * @author Ivan Durlen
 */
public class PresenterFactory extends  FactoryMethod{



	/**
	 * Creates Presenter component for Activity/Fragment passed as first arg.
	 * @param component Activity or Fragment
	 * @return {@link Presenter}
	 */
	 public static Presenter newInstance(Object component){
		Presenter presenter = null;

		try{
			Class presenterClass = getComponentClass(component, "presenter", "Presenter");
			Constructor constructor = presenterClass.getConstructor(new Class[]{ component.getClass().getSuperclass() });
			presenter = (Presenter) constructor.newInstance(component);
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

		return presenter;
	}


}
