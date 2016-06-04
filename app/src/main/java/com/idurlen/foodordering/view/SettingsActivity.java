package com.idurlen.foodordering.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ToggleButton;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.presenter.Presenter;
import com.idurlen.foodordering.factory.PresenterFactory;




public class SettingsActivity extends AppCompatActivity {

	Presenter presenter;

	ToggleButton tbAutoSync;
	Toolbar toolbar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_settings);
		findViews();

		presenter = PresenterFactory.newInstance(this);
		presenter.onCreate(savedInstanceState);
	}


	@Override
	protected void onStart() {
		super.onStart();
		presenter.onStart();
	}


	@Override
	protected void onResume() {
		super.onResume();
		getSupportActionBar().setTitle(R.string.title_activity_settings);
	}


	@Override
	protected void onStop() {
		super.onStop();
		presenter.onStop();
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.onDestroy();
		presenter = null;
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
