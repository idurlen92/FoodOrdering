package com.idurlen.foodordering.controller;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

import com.idurlen.foodordering.utils.AppSettings;
import com.idurlen.foodordering.view.SettingsActivity;




/**
 * MVC Controller component for SettingsActivity.
 */
public class SettingsController implements Controller{

	ToggleButton tbAutoSync;

	AppSettings settings;
	SettingsActivity activity;


	public SettingsController(AppCompatActivity activity){
		this.activity = (SettingsActivity) activity;
		settings = AppSettings.getInstance(activity);
	}


	@Override
	public void activate() {
		tbAutoSync = activity.getTbAutoSync();
		tbAutoSync.setChecked(settings.isAutoSync());
		tbAutoSync.setOnClickListener(this);
	}




	@Override
	public void setListeners() {

	}




	@Override
	public void onClick(View v) {
		if(v instanceof ToggleButton){
			settings.setAutoSync(tbAutoSync.isChecked());
		}
	}


}
