package com.idurlen.foodordering.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.controller.ControllerFactory;




public class RegisterActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		findWidgets();
		ControllerFactory.getInstance().activate(this);
	}


	private void findWidgets(){
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		//TODO:
	}




}
