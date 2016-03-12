package com.idurlen.foodordering.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.controller.MenuController;
import com.idurlen.foodordering.utils.SessionManager;




public class MainActivity extends AppCompatActivity {

	SessionManager sessionManager;

	ActionBarDrawerToggle toggle;
	DrawerLayout drawer;
	NavigationView navigationView;
	Toolbar toolbar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sessionManager = SessionManager.getInstance(this);
		if(!sessionManager.isLoggedIn()){
			Log.d("USER", "Not logged in - redirecting to Login");
			redirectToLogin();//TODO: onRestart(), onStart()?
		}
		else{
			Log.d("USER", "Logged in");
			setContentView(R.layout.activity_main);

			findWidgets();
			setSupportActionBar(toolbar);

			MenuController menuController = new MenuController(this);

			toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
			drawer.setDrawerListener(toggle);
			toggle.syncState();
		}
	}


	@Override
	public void onBackPressed() {
		if(drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		}
		else {
			super.onBackPressed();
		}
	}


	public DrawerLayout getDrawer(){ return drawer; }

	public NavigationView getNavigationView(){ return navigationView; }

	public ActionBarDrawerToggle getToggle(){ return toggle; }

	public Toolbar getToolbar(){ return toolbar; }


	private void findWidgets(){
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		navigationView = (NavigationView) findViewById(R.id.nav_view);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
	}



	private void redirectToLogin(){
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}


}