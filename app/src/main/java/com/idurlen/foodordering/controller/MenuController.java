package com.idurlen.foodordering.controller;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.factory.FragmentFactory;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.view.LoginActivity;
import com.idurlen.foodordering.view.MainActivity;




/**
 * Controls click events for Navigation Drawer.
 */
public class MenuController implements NavigationView.OnNavigationItemSelectedListener {

	public static final String OPTION_HOME = "homeOption";

	SessionManager sessionManager;

	DrawerLayout drawer;
	NavigationView navigation;

	MainActivity activity;


	public MenuController(Activity activity){
		this.activity = (MainActivity) activity;
		sessionManager = SessionManager.getInstance(activity);
		drawer = this.activity.getDrawer();
		navigation = this.activity.getNavigationView();

		navigation.setNavigationItemSelectedListener(this);
		setFragment(OPTION_HOME);
		//TODO: set username in drawer menu
	}


	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		int id = item.getItemId();

		Log.w("PERO", activity.getResources().getResourceName(id));
		if(id == R.id.logoutOption) {
			sessionManager.destroySession();
			redirectToActivity(LoginActivity.class);
		}
		else if(id == R.id.settingsOption){
			//TODO:
		}
		else{
			String itemName = activity.getResources().getResourceName(id);
			itemName = itemName.substring(itemName.lastIndexOf("/") + 1);
			setFragment(itemName);
		}

		drawer.closeDrawer(GravityCompat.START);
		return true;
	}



	private void redirectToActivity(Class redirectActivityClass){
		Intent intent = new Intent(activity, redirectActivityClass);
		activity.startActivity(intent);
		activity.finish();
	}


	private void setFragment(String itemName){
		Log.d("ITEM", itemName);

		Fragment fragment = FragmentFactory.getInstance(itemName);
		if(fragment == null){
			Log.e("NO FRAGMENT", "No Fragment for: " + itemName);
			return;
		}

		FragmentManager manager = activity.getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.layout_main, fragment);
		transaction.commit();
	}



}
