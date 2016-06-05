package com.idurlen.foodordering.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.factory.PresenterFactory;
import com.idurlen.foodordering.presenter.Presenter;




/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

	private AppCompatTextView tvRegister;
	private Button bLogin;
	private EditText etUsername;
	private EditText etPassword;
	private TextInputLayout layoutUsername;
	private TextInputLayout layoutPassword;

	Presenter presenter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
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
	protected void onStop() {
		super.onStop();
		presenter.onStop();
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter = null;
	}




	private void findViews(){
		bLogin = (Button) findViewById(R.id.bLogin);
		etUsername = (EditText) findViewById(R.id.etLoginUsername);
		etPassword = (EditText) findViewById(R.id.etLoginPassword);
		layoutPassword = (TextInputLayout) findViewById(R.id.layoutLoginPassword);
		layoutUsername = (TextInputLayout) findViewById(R.id.layoutLoginUsername);
		tvRegister = (AppCompatTextView) findViewById(R.id.tvRegister);

		etPassword.setTransformationMethod(new PasswordTransformationMethod());
	}


	public AppCompatTextView getTVRegister(){ return tvRegister; }

	public Button getBLogin(){ return bLogin; }

	public EditText getETUsername(){ return etUsername; }
	public EditText getETPassword(){ return etPassword; }

	public TextInputLayout getLayoutUsername(){ return layoutUsername; }
	public TextInputLayout getLayoutPassword(){ return layoutPassword; }



}// class