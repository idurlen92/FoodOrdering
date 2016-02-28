package com.idurlen.foodordering.utils;

public class StringUtils {


	public static String upperToName(String upperCaseString){
		String newString = upperCaseString.substring(0, 1);
		newString += upperCaseString.substring(1, upperCaseString.length()).toLowerCase();
		return newString;
	}


}
