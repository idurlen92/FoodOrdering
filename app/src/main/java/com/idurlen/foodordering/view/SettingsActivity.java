package com.idurlen.foodordering.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.factory.PresenterFactory;
import com.idurlen.foodordering.presenter.Presenter;




public class SettingsActivity extends AppCompatActivity {

	EditText etFirstName;
	EditText etLastName;
	EditText etAddress;

	Spinner spCity;
	Switch swAutoSync;
	Toolbar toolbar;

	TextInputLayout layoutFirstName;
	TextInputLayout layoutLastName;
	TextInputLayout layoutAddress;

	Presenter presenter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		findViews();
		getSupportActionBar().setTitle(R.string.title_activity_settings);

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
		etFirstName = (EditText) findViewById(R.id.etSettingsFirstName);
		etLastName = (EditText) findViewById(R.id.etSettingsLastName);
		etAddress = (EditText) findViewById(R.id.etSettingsAddress);
		spCity = (Spinner) findViewById(R.id.spSettingsCity);
		swAutoSync = (Switch) findViewById(R.id.swSettingsAutoSync);

		layoutFirstName = (TextInputLayout) findViewById(R.id.layoutSettingsFirstName);
		layoutLastName = (TextInputLayout) findViewById(R.id.layoutSettingsLastName);
		layoutAddress = (TextInputLayout) findViewById(R.id.layoutSettingsAddress);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}


	public EditText getEtFirstName() {
		return etFirstName;
	}

	public EditText getEtLastName() {
		return etLastName;
	}

	public EditText getEtAddress() {
		return etAddress;
	}

	public Spinner getSpCity() {
		return spCity;
	}

	public Switch getSwAutoSync() {
		return swAutoSync;
	}

	public TextInputLayout getLayoutFirstName() {
		return layoutFirstName;
	}

	public TextInputLayout getLayoutLastName() {
		return layoutLastName;
	}

	public TextInputLayout getLayoutAddress() {
		return layoutAddress;
	}




	public Toolbar getToolbar() {
		return toolbar;
	}


}
