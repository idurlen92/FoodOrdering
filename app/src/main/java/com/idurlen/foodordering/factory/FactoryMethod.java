package com.idurlen.foodordering.factory;

/**
 * Contains methods for factories.
 * @author Ivan Durlen
 */
public abstract class FactoryMethod {


	/**
	 * Returns Class instance by specified parameters.
	 * @param componentName - Activity/Fragment/Id component name
	 * @param baseClassName - Activity/Fragment superclass of previous component object
	 * @param packageSubPath - Package of new component without base package name and start/end dots
	 * @param newBaseClassName - Fragment/Controller superclass of new component
	 * @return
	 * @throws Exception
	 */
	protected static Class getComponentClass(String componentName, String baseClassName, String packageSubPath,
	                                         String newBaseClassName)throws Exception{
		// com.idurlen.foodordering:id/nav_camera

		//MainActivity, Controller - MainController
		componentName = componentName.toLowerCase();
		String componentSubName = componentName.substring(0, 1).toUpperCase() +
				componentName.substring(1, componentName.indexOf(baseClassName.toLowerCase()));

		newBaseClassName = newBaseClassName.toLowerCase();
		newBaseClassName = newBaseClassName.substring(0, 1).toUpperCase() +
				newBaseClassName.substring(1, newBaseClassName.length());

		String strClassPath = "com.idurlen.foodordering." + packageSubPath + "." +
				componentSubName + newBaseClassName;

		return Class.forName(strClassPath);
	}


}
