package com.idurlen.foodordering.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.factory.FragmentFactory;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.view.LoginActivity;
import com.idurlen.foodordering.view.MainActivity;
import com.idurlen.foodordering.view.SettingsActivity;




/**
 * Controls click events for Navigation Drawer.
 */
public class MenuController implements NavigationView.OnNavigationItemSelectedListener {

	public static final String OPTION_HOME = "homeOption";

	SessionManager sessionManager;

	DrawerLayout drawer;
	NavigationView navigation;

	MainActivity activity;


	public MenuController(AppCompatActivity activity){
		this.activity = (MainActivity) activity;
		sessionManager = SessionManager.getInstance(activity);
		setWidgets();
		setFragment(OPTION_HOME);
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
			setFragment(itemName);
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


	private void setFragment(String itemName){
		Fragment fragment = FragmentFactory.newInstance(itemName);
		if(fragment == null){
			Log.e("NO FRAGMENT", "No Fragment for: " + itemName);
			Toast.makeText(activity, "Nije implementirano", Toast.LENGTH_SHORT).show();
			return;
		}

		FragmentManager manager = activity.getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.layout_main, fragment);
		transaction.commit();
	}



}
