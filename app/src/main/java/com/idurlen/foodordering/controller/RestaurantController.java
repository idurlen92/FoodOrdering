package com.idurlen.foodordering.controller;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.Dishes;
import com.idurlen.foodordering.database.model.Dish;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.fragment.RestaurantFragment;


/**
 * MVC Controller component for RestaurantFragment.
 */
public class RestaurantController implements Controller{

	int restaurantId;

	DatabaseManager databaseManager;
	SQLiteDatabase db;
	RestaurantFragment fragment;

	public RestaurantController(Fragment fragment){
		this.fragment = (RestaurantFragment) fragment;
		restaurantId = Messenger.getBundle().getInt(Messenger.KEY_RESTAURANT_ID);
		Log.d("RESTAURANT ID", Integer.toString(restaurantId));
	}


	@Override
	public void activate() {
		databaseManager = DatabaseManager.getInstance(fragment.getActivity());
		db = databaseManager.getReadableDatabase();

		BackgroundTask task = new BackgroundTask(fragment.getPbRestaurant(), fragment.getLayoutRestaurant(), new BackgroundOperation() {
			@Override
			public Object execInBackground() {
				Log.d("BACKGROUND", "In backgroun thread");
				for(Dish dish : Dishes.getDishesOfRestaurant(db, restaurantId)){
					Log.d("Dish", dish.getTitle());
				}
				return null;
			}

			@Override
			public void execAfter(Object object) {
				Log.d("AFTER", "In UI thread");
			}
		});

		task.execute();
	}




	@Override
	public void setListeners() {

	}




	@Override
	public void onClick(View v) {
	}


}
