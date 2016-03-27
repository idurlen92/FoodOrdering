package com.idurlen.foodordering.controller;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.view.LoginActivity;
import com.idurlen.foodordering.view.MainActivity;
import com.idurlen.foodordering.view.SettingsActivity;




/**
 * Controls click events for Navigation Drawer.
 */
public class MenuController implements NavigationView.OnNavigationItemSelectedListener {

	public static final String[] OPTIONS_ADD_TO_STACK = new String[]{"restaurant"};

	public static final String OPTION_HOME = "homeOption";
	public static final String OPTION_RESTAURANT = "restaurantOption";

	SessionManager sessionManager;

	DrawerLayout drawer;
	NavigationView navigation;

	MainActivity activity;


	public MenuController(AppCompatActivity activity){
		this.activity = (MainActivity) activity;
		sessionManager = SessionManager.getInstance(activity);
		setWidgets();
	}


	private void setWidgets(){
		drawer = activity.getDrawer();
		navigation = activity.getNavigationView();
		navigation.setNavigationItemSelectedListener(this);
	}


	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		String itemName = activity.getResources().getResourceName(item.getItemId());
		Log.d("OPTION", itemName);

		if(item.getItemId() == R.id.logoutOption) {
			sessionManager.destroySession();
			redirectToActivity(LoginActivity.class, true);
		}
		else if(item.getItemId() == R.id.settingsOption){
			redirectToActivity(SettingsActivity.class, false);
		}
		else{
			activity.pushFragment(itemName);
		}

		drawer.closeDrawer(GravityCompat.START);
		return true;
	}



	private void redirectToActivity(Class redirectActivityClass, boolean isFinish){
		Intent intent = new Intent(activity, redirectActivityClass);
		activity.startActivity(intent);
		if(isFinish) {
			activity.finish();
		}
	}


}
