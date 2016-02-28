package com.idurlen.foodordering.controller;
import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.idurlen.foodordering.view.RegisterActivity;




/**
 * MVC Controller component for RegisterActivity
 */
public class RegisterController implements Controller{


	RegisterActivity activity;


	@Override
	public void activate(Activity activity) {
		this.activity = (RegisterActivity) activity;
		Log.d("ATTACHED", "RegisterController");
	}


	@Override
	public void setListeners() {
	}


	@Override
	public void onClick(View v) {
	}


}