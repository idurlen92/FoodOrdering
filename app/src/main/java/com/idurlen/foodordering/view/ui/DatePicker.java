package com.idurlen.foodordering.view.ui;


import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;




/**
 * Caution: month is in range [0, 11]
 * @author Ivan Durlen
 */
public class DatePicker{

	private DatePickerDialog dialog;

	private int currentYear = -1;
	private int currentMonth = -1;
	private int currentDay = -1;


	public DatePicker(Context context, DatePickerDialog.OnDateSetListener onDateSetListener){
		Calendar calendar = Calendar.getInstance();
		currentYear = calendar.get(Calendar.YEAR);
		currentMonth = calendar.get(Calendar.MONTH);
		currentDay = calendar.get(Calendar.DAY_OF_MONTH);

		dialog = new DatePickerDialog(context, onDateSetListener,
				currentYear, currentMonth, currentDay);
	}


	public void showPicker(){
		dialog.show();
	}


}