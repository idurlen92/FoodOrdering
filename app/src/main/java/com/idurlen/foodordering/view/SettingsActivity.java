package com.idurlen.foodordering.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ToggleButton;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.controller.Controller;
import com.idurlen.foodordering.factory.ControllerFactory;




public class SettingsActivity extends AppCompatActivity {

	Controller controller;

	ToggleButton tbAutoSync;
	Toolbar toolbar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		findViews();
		controller = ControllerFactory.newInstance(this);
	}




	@Override
	protected void onStart() {
		super.onStart();
		controller.activate();
	}




	private void findViews(){
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		tbAutoSync = (ToggleButton) findViewById(R.id.tbAutoSync);
		setSupportActionBar(toolbar);
	}


	public ToggleButton getTbAutoSync() {
		return tbAutoSync;
	}

	public Toolbar getToolbar() {
		return toolbar;
	}


}
