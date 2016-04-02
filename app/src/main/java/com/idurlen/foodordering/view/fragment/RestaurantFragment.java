package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

	Button bOrder;
	LinearLayout layoutContainer;
	ProgressBar progressBar;
	ListView listView;


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
		bOrder = (Button) getView().findViewById(R.id.bOrder);
		listView = (ListView) getView().findViewById(R.id.lvMeals);
		layoutContainer = (LinearLayout) getView().findViewById(R.id.layoutRestaurant);
		progressBar = (ProgressBar) getView().findViewById(R.id.pbRestaurant);
	}

	public Button getBOrder(){ return bOrder; }

	public ListView getListView(){
		return listView;
	}

	public LinearLayout getLayoutContainer() {
		return layoutContainer;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

}