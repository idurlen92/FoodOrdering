package com.idurlen.foodordering.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.factory.PresenterFactory;
import com.idurlen.foodordering.presenter.Presenter;




/**
 * Activity for registering user.
 */
public class RegisterActivity extends AppCompatActivity {

	Button bRegister;

	EditText etFirstName;
	EditText etLastName;
	EditText etEmail;
	EditText etPassword;
	EditText etPasswordRepeat;
	EditText etAddress;
	EditText etBirthDate;

	Spinner sCity;
	Toolbar toolbar;

	Presenter presenter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		findViews();
		setSupportActionBar(toolbar);

		presenter = PresenterFactory.newInstance(this);
		presenter.onCreate(savedInstanceState);
	}


	@Override
	protected void onStart() {
		super.onStart();
		presenter.onStart();
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
		toolbar = null;
	}




	private void findViews(){
		bRegister = (Button) findViewById(R.id.bRegister);
		etFirstName = (EditText) findViewById(R.id.etRegisterFirstName);
		etLastName = (EditText) findViewById(R.id.etRegisterLastName);
		etEmail = (EditText) findViewById(R.id.etRegisterEmail);
		etPassword = (EditText) findViewById(R.id.etRegisterPassword);
		etPasswordRepeat = (EditText) findViewById(R.id.etRegisterPasswordRepeat);
		etAddress = (EditText) findViewById(R.id.etRegisterAddress);
		etBirthDate = (EditText) findViewById(R.id.etRegisterBirthDate);
		sCity = (Spinner) findViewById(R.id.sRegisterCity);
		toolbar = (Toolbar) findViewById(R.id.toolbar);

		etPassword.setTransformationMethod(new PasswordTransformationMethod());
		etPasswordRepeat.setTransformationMethod(new PasswordTransformationMethod());
	}


	public Button getBRegister(){ return bRegister; }

	public EditText getEtFirstName() { return etFirstName; }

	public EditText getEtLastName() {
		return etLastName;
	}

	public EditText getEtEmail() {
		return etEmail;
	}

	public EditText getEtPassword() {
		return etPassword;
	}

	public EditText getEtPasswordRepeat() {
		return etPasswordRepeat;
	}

	public EditText getEtAddress() {
		return etAddress;
	}

	public EditText getEtBirthDate() { return etBirthDate; }

	public Spinner getSCity() {
		return sCity;
	}

}