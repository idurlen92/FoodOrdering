package com.idurlen.foodordering.utils;

public class StringUtils {


	/**
	 * TODO: description
	 * Formats Uppercase String as Name format i.e.
	 * @param upperCaseString
	 * @return
	 */
	public static String upperToName(String upperCaseString){
		String newString = upperCaseString.substring(0, 1);
		newString += upperCaseString.substring(1, upperCaseString.length()).toLowerCase();
		return newString;
	}


	/**
	 * TODO: Description
	 * @param year
	 * @param month
	 * @param day
	 * @param isFromZero
	 * @param isMonthAsString
	 * @return
	 */
	public static String getDateString(int year, int month, int day, boolean isFromZero, boolean isMonthAsString){
		String[] strMonths = new String[]{
				"Siječanj", "Veljača", "Ožujak", "Travanj", "Svibanj", "Lipanj",
				"Srpanj", "Kolovoz", "Rujan", "Listopad", "Studeni", "Prosinac" };

		StringBuilder builder = new StringBuilder();
		if(isMonthAsString){
			builder.append(day + ". ");
			builder.append(strMonths[isFromZero ? month :  month - 1] + " ") ;
			builder.append(year + ".");
		}
		else{
			builder.append((day < 10 ? "0" : "") + day + "/");
			builder.append((month < 10 ? "0" : "") + month + "/" + year);
		}

		return builder.toString();
	}


}