package com.idurlen.foodordering.factory;

import android.util.Log;




/**
 * Contains methods for factories.
 * @author Ivan Durlen
 */
public abstract class FactoryMethod {


	/**
	 * Returns Class instance by specified parameters.
	 * @param componentName Activity/Fragment/Id component name
	 * @param filter Superclass of first param component or "option" for menu options
	 * @param packageSubPath Package of new component without base package name and start/end dots
	 * @param superClass (Fragment/Controller etc.) superclass of new component
	 * @return {@link Class} of new Component
	 * @throws Exception
	 */
	protected static Class getComponentClass(String componentName, String filter, String packageSubPath,
	                                         Class superClass)throws Exception{
		componentName = componentName.toLowerCase();
		String componentSubName = componentName.substring(0, 1).toUpperCase() +
				componentName.substring(1, componentName.indexOf(filter.toLowerCase()));

		String strClassPath = "com.idurlen.foodordering." + packageSubPath + "." +
				componentSubName + superClass.getSimpleName();
		Log.d("COMPONENT", strClassPath);

		return Class.forName(strClassPath);
	}


}
