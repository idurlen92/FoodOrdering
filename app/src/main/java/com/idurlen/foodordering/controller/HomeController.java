package com.idurlen.foodordering.controller;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.Restaurants;
import com.idurlen.foodordering.database.model.Restaurant;
import com.idurlen.foodordering.view.MainActivity;
import com.idurlen.foodordering.view.fragment.HomeFragment;
import com.idurlen.foodordering.view.ui.adapter.RestaurantsAdapter;

import java.util.ArrayList;
import java.util.List;




public class HomeController implements Controller, AdapterView.OnItemClickListener {

	List<Restaurant> lRestaurants = new ArrayList<Restaurant>();

	LoadRestaurants loadRestaurants;
	RestaurantsAdapter adapter;

	DatabaseManager databaseManager;
	MainActivity activity;
	HomeFragment fragment;


	public HomeController(Fragment fragment){
		this.fragment = (HomeFragment) fragment;
		this.activity = (MainActivity) fragment.getActivity();
		adapter = new RestaurantsAdapter(fragment);
	}


	@Override
	public void activate() {
		databaseManager = DatabaseManager.getInstance(activity);
		SQLiteDatabase database = databaseManager.getReadableDatabase();
		loadRestaurants = new LoadRestaurants(database, fragment.getLayoutHome(), fragment.getPbHome(), "Zagreb");
		loadRestaurants.execute(null, null, null);
		adapter.setList(loadRestaurants.lRestaurants);
		fragment.getLvRestaurants().setAdapter(adapter);
		setListeners();
	}




	@Override
	public void setListeners() {
		fragment.getLvRestaurants().setOnItemClickListener(this);
	}


	@Override
	public void onClick(View v) { /* Empty */ }


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		OrderController.restaurant = (Restaurant) adapter.getItem(position);
		activity.pushFragment(MainActivity.OPTION_ORDER);
	}


	/**
	 * AsyncTask class for async loading of restaurants.
	 * @author Ivan Durlen
	 */
	private class LoadRestaurants extends AsyncTask<Void, Void, Void> {

		SQLiteDatabase db;
		ProgressBar pb;
		View view;
		String cityName;

		public List<Restaurant> lRestaurants = new ArrayList<Restaurant>();

		public LoadRestaurants(SQLiteDatabase db, View view, ProgressBar pb, String cityName){
			this.db = db;
			this.view = view;
			this.pb = pb;
			this.cityName = cityName;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pb.setVisibility(View.VISIBLE);
			view.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			lRestaurants.addAll(Restaurants.getRetaurantsByCity(db,  cityName));
			return null;
		}




		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			pb.setVisibility(View.GONE);
			view.setVisibility(View.VISIBLE);
		}
	}


}
