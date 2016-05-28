package com.idurlen.foodordering.controller;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.Restaurants;
import com.idurlen.foodordering.database.model.Restaurant;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.fragment.ShowMapFragment;

import java.io.IOException;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class ShowMapController implements Controller, OnMapReadyCallback{

	List<Restaurant> lRestaurants;

	DatabaseManager databaseManager;
	SessionManager session;

	BackgroundTask loadRestaurantsTask;

	ShowMapFragment fragment;


	public ShowMapController(MapFragment fragment) {
		this.fragment = (ShowMapFragment) fragment;
		this.databaseManager = DatabaseManager.getInstance(fragment.getActivity());
		session = SessionManager.getInstance(fragment.getActivity());
	}


	@Override
	public void activate() {
		loadRestaurantsTask = new BackgroundTask(new BackgroundOperation() {

			@Override
			public Object execInBackground() {
				SQLiteDatabase db = databaseManager.getReadableDatabase();
				lRestaurants = Restaurants.getRestaurantsByCity(db, session.getCity());
				db.close();

				return lRestaurants;
			}

			@Override
			public void execAfter(Object object) {
				fragment.getMapAsync(ShowMapController.this);
			}
		});

		loadRestaurantsTask.execute();
	}




	@Override
	public void setListeners() {

	}




	@Override
	public void onClick(View v) {

	}




	@Override
	public void onMapReady(GoogleMap googleMap) {
		googleMap.setMyLocationEnabled(true);
		setMarkersAsync(googleMap);
	}



	private void setMarkersAsync(final GoogleMap map){
		Thread thr = new Thread(new Runnable() {
			@Override
			public void run() {
				Geocoder geocoder = new Geocoder(fragment.getActivity());
				for(final Restaurant restaurant : lRestaurants){
					try {
						for(final Address address : geocoder.getFromLocationName(restaurant.getAddress() + ", "
								+ session.getCity(), 1)){
							fragment.getActivity().runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     map.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),
		                                     address.getLongitude())).title(restaurant.getName()));
                                 }
                             });
						}
					}
					catch(IOException e){
						e.printStackTrace();
					}
					catch(Exception e){
						e.printStackTrace();
					}

				}//for
			}
		});

		thr.start();
	}


}
