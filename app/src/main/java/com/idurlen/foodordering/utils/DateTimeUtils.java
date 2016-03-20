package com.idurlen.foodordering.utils;

import java.util.Calendar;




/**
 * Contains methods for Date/Time.
 * @author Ivan Durlen
 */
public class DateTimeUtils {


	public static String getCurrentTimeStamp(){
		Calendar calendar = Calendar.getInstance();
		StringBuilder builder = new StringBuilder();

		int[] dateValues = new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)};
		int[] timeValues = new int[]{calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)};

		builder.append(StringUtils.getDateString(
				dateValues[0], dateValues[1], dateValues[2], true, false).replace("/", "-"));
		builder.append(" ");
		builder.append(StringUtils.getTimeString(timeValues[0], timeValues[1], timeValues[2]));

		return builder.toString();
	}


}
