package com.idurlen.foodordering.controller;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.Restaurants;
import com.idurlen.foodordering.database.model.Restaurant;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.MainActivity;
import com.idurlen.foodordering.view.fragment.HomeFragment;
import com.idurlen.foodordering.view.ui.adapter.RestaurantsAdapter;

import java.util.List;




public class HomeController implements Controller, AdapterView.OnItemClickListener {

	ListView lvRestaurants;

	BackgroundTask loadRestaurantsTask;
	RestaurantsAdapter adapter;

	DatabaseManager databaseManager;
	SQLiteDatabase db;

	MainActivity activity;
	HomeFragment fragment;


	public HomeController(Fragment fragment){
		this.fragment = (HomeFragment) fragment;
		this.activity = (MainActivity) fragment.getActivity();
	}


	@Override
	public void activate() {
		lvRestaurants = fragment.getLvRestaurants();

		databaseManager = DatabaseManager.getInstance(activity);
		db = databaseManager.getReadableDatabase();

		loadRestaurantsTask = new BackgroundTask(fragment.getPbHome(), fragment.getLayoutHome(), new BackgroundOperation() {
			@Override
			public Object execInBackground() {
				//TODO: Hardcoded!!!!
				return Restaurants.getRetaurantsByCity(db, "Zagreb");
			}

			@Override
			public void execAfter(Object object) {
				adapter = new RestaurantsAdapter(fragment);
				adapter.setList((List<Restaurant>) object);
				fragment.getLvRestaurants().setAdapter(adapter);
				setListeners();
			}
		});

		loadRestaurantsTask.execute();
	}




	@Override
	public void setListeners() {
		lvRestaurants.setOnItemClickListener(this);
	}


	@Override
	public void onClick(View v) { /* Empty */ }


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Restaurant restaurant = (Restaurant) adapter.getItem(position);
		Messenger.getBundle().putInt(Messenger.KEY_RESTAURANT_ID, restaurant.getId());
		activity.pushFragment(MenuController.OPTION_RESTAURANT);
	}

}
