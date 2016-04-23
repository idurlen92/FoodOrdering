package com.idurlen.foodordering.utils;

import java.util.Calendar;




/**
 * Contains methods for Date/Time.
 * @author Ivan Durlen
 */
public class DateTimeUtils {


	/**
	 * Returns current timestamp as a string.
	 * @return
	 */
	public static String getCurrentTimeStampString(){
		Calendar calendar = Calendar.getInstance();
		StringBuilder builder = new StringBuilder();

		int[] dateValues = new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)};
		int[] timeValues = new int[]{calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)};

		builder.append(dateValues[2] + "-");
		builder.append((dateValues[1] < 10 ? "0" : "") + dateValues[1] + "-");
		builder.append((dateValues[0] < 10 ? "0" : "") + dateValues[0] + " ");
		builder.append(StringUtils.getTimeString(timeValues[0], timeValues[1], timeValues[2]));

		return builder.toString();
	}




	/**
	 * Returns current date as String.
	 * @return
	 */
	public static String getCurrentDateString(){
		Calendar calendar = Calendar.getInstance();
		return StringUtils.getDateString(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				false, true);
	}


}
