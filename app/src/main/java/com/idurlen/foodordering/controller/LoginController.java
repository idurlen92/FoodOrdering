package com.idurlen.foodordering.controller;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.idurlen.foodordering.AppController;
import com.idurlen.foodordering.view.LoginActivity;




/**
 * MVC Controller component for LoginActivity.
 */
public class LoginController implements Controller{

	private final String TEST_USERNAME = "user";
	private final String TEST_PASSWORD = "123456";
	private final String MSG_EMPTY = "Unesite vrijednost";
	private final String MSG_WRONG_USERNAME = "Korisnik nije pronađen";
	private final String MSG_WRONG_PASSWORD = "Kriva lozinka";

	private EditText etUsername;
	private EditText etPassword;

	private TextInputLayout layoutUsername;
	private TextInputLayout layoutPassword;

	private LoginActivity activity;


	@Override
	public void activate(Activity activity) {
		this.activity = (LoginActivity) activity;
		Log.d("ATTACHED", "LoginController");

		etUsername = this.activity.getETUsername();
		etPassword = this.activity.getETPassword();
		layoutUsername = this.activity.getLayoutUsername();
		layoutPassword = this.activity.getLayoutPassword();
		setListeners();
	}


	@Override
	public void setListeners() {
		activity.getBLogin().setOnClickListener(this);
		activity.getTVRegister().setOnClickListener(this);
	}




	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}




	@Override
	public void onClick(View v){
		Log.d("View class: ", v.getClass().toString());

		if(v instanceof AppCompatTextView){
			AppController.changeActivity(activity, AppController.AppContext.REGISTER);
		}
		else{
			handleLogin();
		}
	}


	private void handleLogin(){
		String strUsername = etUsername.getText().toString();
		String strPassword = etPassword.getText().toString();
		boolean isValidUsername = (etUsername.getText().toString().compareTo(TEST_USERNAME) == 0);
		boolean isValidPassword = (etPassword.getText().toString().compareTo(TEST_PASSWORD) == 0);

		layoutUsername.setError(strUsername.isEmpty() ? MSG_EMPTY :
				(isValidUsername ? null : MSG_WRONG_USERNAME));
		layoutPassword.setError(strPassword.isEmpty() ? MSG_EMPTY :
				(isValidUsername && !isValidPassword ? MSG_WRONG_PASSWORD : null));

		if(isValidPassword && isValidUsername){
			AppController.changeActivity(activity, AppController.AppContext.MAIN, null, true);
		}
	}


}