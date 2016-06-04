package com.idurlen.foodordering.presenter;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

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
public class ShowMapPresenter extends Presenter implements OnMapReadyCallback{

	private static final String TITLE = "Mapa";

	List<Restaurant> lRestaurants;

	DatabaseManager databaseManager;
	SessionManager session;


	public ShowMapPresenter(MapFragment fragment) {
		super(fragment, TITLE);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) { }


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		databaseManager = DatabaseManager.getInstance(getApplicationContext());
		session = SessionManager.getInstance(getApplicationContext());
		getRestaurants();
	}


	@Override
	public void onPause() { }


	@Override
	public void onStop() {
		databaseManager = null;
		session = null;
	}


	@Override
	public void onMapReady(GoogleMap googleMap) {
		googleMap.setMyLocationEnabled(true);
		setMarkersAsync(googleMap);
	}


	private void getRestaurants(){
		BackgroundTask task = new BackgroundTask(new BackgroundOperation() {
			@Override
			public Object execInBackground() {
				SQLiteDatabase db = databaseManager.getReadableDatabase();
				lRestaurants = Restaurants.getRestaurantsByCity(db, session.getCity());
				db.close();

				return lRestaurants;
			}

			@Override
			public void execAfter(Object object) {
				((ShowMapFragment) ShowMapPresenter.this.getFragment()).getMapAsync(ShowMapPresenter.this);
				setIsActivated(true);
			}
		});

		task.execute();
	}


	private void setMarkersAsync(final GoogleMap map){
		Thread thr = new Thread(new Runnable() {
			@Override
			public void run() {
				Geocoder geocoder = new Geocoder(getActivity());
				for(final Restaurant restaurant : lRestaurants){
					try {
						for(final Address address : geocoder.getFromLocationName(restaurant.getAddress() +
								", " + session.getCity(), 1)){
							getActivity().runOnUiThread(new Runnable() {
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
