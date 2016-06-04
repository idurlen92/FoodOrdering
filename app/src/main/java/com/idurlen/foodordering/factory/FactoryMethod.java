package com.idurlen.foodordering.factory;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;




/**
 * Contains methods for factories.
 * @author Ivan Durlen
 */
public class FactoryMethod {

	protected static String strClassName = "";


	private static List<String> getFiltersList(){
		List<String> lFilters = new ArrayList<>();
		lFilters.add("option");
		lFilters.add("fragment");
		lFilters.add("activity");
		return lFilters;
	}


	protected static void logError(Exception e, String message){
		Log.e("FACTORY", message  + " (" + strClassName + ")");
		e.printStackTrace();
	}


	/**
	 *
	 * @param arg Object witch calls Factory Method or String option for Fragments
	 * @param subPackage Subpackage name without start and end dots
	 * @param suffix Instance Class suffix e.g. Presenter, Fragment
	 * @return {@link Class} of new Component
	 * @throws Exception
	 */
	protected static Class getComponentClass(Object arg, String subPackage, String suffix) throws Exception{
		String strArg = (arg instanceof String) ? (String) arg : arg.getClass().getSimpleName();

		strClassName = "com.idurlen.foodordering." + subPackage + "." + strArg.substring(0, 1).toUpperCase();

		for(String strFilter : getFiltersList()){
			if(strArg.toLowerCase().contains(strFilter)) {
				strClassName += strArg.substring(1, strArg.toLowerCase().indexOf(strFilter.toLowerCase())) + suffix;
				break;
			}
		}

		return Class.forName(strClassName);
	}


}
