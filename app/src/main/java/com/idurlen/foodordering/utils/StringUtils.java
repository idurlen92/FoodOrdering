package com.idurlen.foodordering.utils;

/**
 * Contains methods for oprerating Strings.
 * @author Ivan Durlen
 */
public class StringUtils{


	/**
	 * Formats Uppercase String as Name format i.e.
	 * @param upperCaseString
	 * @return String
	 */
	public static String upperToName(String upperCaseString){
		String newString = upperCaseString.substring(0, 1);
		newString += upperCaseString.substring(1, upperCaseString.length()).toLowerCase();
		return newString;
	}


	/**
	 * Formats year, month and day as date string.
	 * @param year
	 * @param month
	 * @param day
	 * @param isFromZero if month in range [0, 11] instead of [1, 12]
	 * @param isMonthAsString if month should be formatted as a string, instead of number in range [1, 12]
	 * @return Date String
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
			if(isFromZero) {
				builder.append((month < 10 ? "0" : "") + month + "/" + year);
			}
			else{
				builder.append((month + 1 < 10 ? "0" : "") + (month + 1) + "/" + year);
			}
		}

		return builder.toString();
	}




	/**
	 * Formats date String from given parameters.
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static String getTimeString(int hour, int minute, int second){
		StringBuilder builder = new StringBuilder();
		builder.append((hour < 9 ? "0" : "") + hour);
		builder.append((minute < 9 ? "0" : "") + minute);
		builder.append((second < 9 ? "0" : "") + second);
		return builder.toString();
	}


	/**
	 * Capitalizes the String.
	 * @param arg
	 * @return
	 */
	public static String capitalize(String arg){
		return arg.substring(0, 1).toUpperCase() + arg.substring(1, arg.length());
	}


	/**
	 * Concatenates String array, where each chunk is first capitalized.
	 * @param args
	 * @return
	 */
	public static String concatCamelCase(String[] args){
		StringBuilder builder = new StringBuilder();
		for(String arg : args){
			builder.append(capitalize(arg));
		}
		return builder.toString();
	}

}