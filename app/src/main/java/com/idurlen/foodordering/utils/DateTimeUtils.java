package com.idurlen.foodordering.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;




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

		int[] dateValues = new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)};
		int[] timeValues = new int[]{calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)};

		builder.append(dateValues[0] + "-");
		builder.append((dateValues[1] < 10 ? "0" : "") + dateValues[1] + "-");
		builder.append((dateValues[2] < 10 ? "0" : "") + dateValues[2] + " ");
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



	public static boolean isTodayDate(String sTimestamp){
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String sCurrentDate = dateFormat.format(currentDate);

		return sTimestamp.startsWith(sCurrentDate);
	}




	/**
	 * Returns human - readable date from timestamp from the Database.
	 * @param sTimestamp
	 * @return
	 */
	public static String getDateFromTimestamp(String sTimestamp){
		Log.d("TIMESTAMP", sTimestamp);
		int year = Integer.parseInt(sTimestamp.substring(0, 4));
		int month = Integer.parseInt(sTimestamp.substring(5, 7));
		int day = Integer.parseInt(sTimestamp.substring(8, 10));
		return StringUtils.getDateString(year, month, day, false, true);
	}


	public static String getFormatedTimestamp(String sTimestamp){
		//2016-05-22
		return getDateFromTimestamp(sTimestamp)  + sTimestamp.substring(10, sTimestamp.length());
	}

}
