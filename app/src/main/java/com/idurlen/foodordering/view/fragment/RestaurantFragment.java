package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.factory.ControllerFactory;




/**
 * Fragment for viewing dishes of a restaurant and creating an order.
 * @author Ivan Durlen
 */
public class RestaurantFragment extends Fragment {

	LinearLayout layoutRestaurant;
	ProgressBar pbRestaurant;
	ListView lvMeals;


	public RestaurantFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_restaurant, container, false);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		findViews();
		ControllerFactory.newInstance(this).activate();
	}


	private void findViews(){
		lvMeals = (ListView) getView().findViewById(R.id.lvMeals);
		layoutRestaurant = (LinearLayout) getView().findViewById(R.id.layoutRestaurant);
		pbRestaurant = (ProgressBar) getView().findViewById(R.id.pbRestaurant);
	}

	public ListView getLvMeals(){
		return lvMeals;
	}

	public LinearLayout getLayoutRestaurant() {
		return layoutRestaurant;
	}

	public ProgressBar getPbRestaurant() {
		return pbRestaurant;
	}

}
