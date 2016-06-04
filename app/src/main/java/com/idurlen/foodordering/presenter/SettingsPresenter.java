package com.idurlen.foodordering.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

import com.idurlen.foodordering.utils.AppSettings;
import com.idurlen.foodordering.view.SettingsActivity;




/**
 * MVP Presenter component for SettingsActivity.
 */
public class SettingsPresenter extends Presenter implements View.OnClickListener{

	ToggleButton tbAutoSync;

	AppSettings settings;


	public SettingsPresenter(AppCompatActivity activity){
		super(activity);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		tbAutoSync = ((SettingsActivity) getActivity()).getTbAutoSync();
		tbAutoSync.setOnClickListener(this);
	}


	@Override
	public void onPause() { }


	@Override
	public void onActivityCreated(Bundle savedInstanceState) { }


	@Override
	public void onStart() {
		super.onStart();
		settings = AppSettings.getInstance(getApplicationContext());
		tbAutoSync.setChecked(settings.isAutoSync());
	}


	@Override
	public void onStop() {
		settings = null;
	}


	@Override
	public void onClick(View v) {
		if(v instanceof ToggleButton){
			settings.setAutoSync(tbAutoSync.isChecked());
		}
	}


}
