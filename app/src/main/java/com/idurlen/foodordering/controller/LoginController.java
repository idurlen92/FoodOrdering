package com.idurlen.foodordering.controller;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.idurlen.foodordering.database.model.User;
import com.idurlen.foodordering.net.UsersRequest;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.LoginActivity;
import com.idurlen.foodordering.view.MainActivity;
import com.idurlen.foodordering.view.RegisterActivity;




/**
 * MVC Controller component for LoginActivity.
 */
public class LoginController implements Controller{

	private final String MSG_EMPTY = "Unesite vrijednost";
	private final String MSG_NOT_FOUND = "Krivo kor. ime ili lozinka";

	private EditText etUsername;
	private EditText etPassword;

	private TextInputLayout layoutUsername;
	private TextInputLayout layoutPassword;

	private BackgroundTask loginTask;
	private SessionManager sessionManager;

	private LoginActivity activity;



	public LoginController(AppCompatActivity activity){
		this.activity = (LoginActivity) activity;
		sessionManager = SessionManager.getInstance(activity);
	}


	@Override
	public void activate() {
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
	public void onClick(View v){
		Log.d("View class: ", v.getClass().toString());

		if(v instanceof AppCompatTextView){
			Intent intent = new Intent(activity, RegisterActivity.class);
			activity.startActivity(intent);
		}
		else{
			handleLogin();
		}
	}


	private void handleLogin(){
		//TODO: errors!!!
		final String sUsername = etUsername.getText().toString();
		final String sPassword = etPassword.getText().toString();

		layoutUsername.setError(sUsername.isEmpty() ? MSG_EMPTY : null);
		layoutPassword.setError(sPassword.isEmpty() ? MSG_EMPTY : null);

		if(!sUsername.isEmpty() && !sPassword.isEmpty()){
			//if
			loginTask = new BackgroundTask(activity, "Prijava", new BackgroundOperation() {
				@Override
				public Object execInBackground() {
					User user = null;
					try{
						user = UsersRequest.getUser(sUsername, sPassword);
					}
					catch(Exception e){
						Log.e("REST", "error");
						e.printStackTrace();
					}
					return user;
				}

				@Override
				public void execAfter(Object object) {
					if(object == null){
						Snackbar.make(layoutPassword, MSG_NOT_FOUND, Snackbar.LENGTH_SHORT).show();
					}
					else {
						sessionManager.createSession((User) object);
						redirectToMain();
					}
				}
			});

			loginTask.execute();
		}//if
	}


	private void redirectToMain(){
		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}


}