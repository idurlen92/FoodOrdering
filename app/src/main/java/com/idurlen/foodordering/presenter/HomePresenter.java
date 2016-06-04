package com.idurlen.foodordering.presenter;
import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.Restaurants;
import com.idurlen.foodordering.database.model.Restaurant;
import com.idurlen.foodordering.utils.MenuController;
import com.idurlen.foodordering.utils.Messenger;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.MainActivity;
import com.idurlen.foodordering.view.fragment.HomeFragment;
import com.idurlen.foodordering.view.ui.adapter.RestaurantsAdapter;

import java.util.List;




public class HomePresenter extends Presenter implements AdapterView.OnItemClickListener {

	private static final String TITLE = "Restorani ";

	List<Restaurant> lRestaurants;

	ListView lvRestaurants;

	SessionManager session;
	DatabaseManager databaseManager;


	public HomePresenter(Fragment fragment){
		super(fragment, TITLE);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) { }


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		session = SessionManager.getInstance(getApplicationContext());
		databaseManager = DatabaseManager.getInstance(getApplicationContext());
		lvRestaurants = ((HomeFragment) getFragment()).getListView();

		getActivity().getSupportActionBar().setTitle(TITLE + session.getCity());

		if(!isActivated()){
			getRestaurants();
		}
		else{
			setListAdapter();
		}
	}


	@Override
	public void onPause() { }


	@Override
	public void onStop() {
		session = null;
		databaseManager = null;
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		if(lRestaurants != null) {
			lRestaurants.clear();
		}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Messenger.clearAll();
		Restaurant restaurant = (Restaurant) lvRestaurants.getItemAtPosition(position);
		Messenger.putObject(Messenger.KEY_RESTAURANT_OBJECT, restaurant);

		((MainActivity) getActivity()).pushFragment(MenuController.OPTION_RESTAURANT);
	}


	private void getRestaurants(){
		BackgroundTask task = new BackgroundTask(((HomeFragment) getFragment()).getProgressBar(),
				((HomeFragment) getFragment()).getLayoutContainer(), new BackgroundOperation() {

			@Override
			public Object execInBackground() {
				SQLiteDatabase db = databaseManager.getReadableDatabase();
				lRestaurants =  Restaurants.getRestaurantsByCity(db, session.getCity());
				db.close();
				return null;
			}

			@Override
			public void execAfter(Object object) {
				setListAdapter();
				setIsActivated(true);
			}
		});

		task.execute();
	}



	private void setListAdapter(){
		RestaurantsAdapter adapter = new RestaurantsAdapter(
				(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
				 lRestaurants);
		lvRestaurants.setAdapter(adapter);
		lvRestaurants.setOnItemClickListener(this);
	}


}
