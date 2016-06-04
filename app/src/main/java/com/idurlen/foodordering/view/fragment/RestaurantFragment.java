package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.factory.PresenterFactory;
import com.idurlen.foodordering.presenter.Presenter;




/**
 * Fragment for viewing dishes of a restaurant and creating an order.
 * @author Ivan Durlen
 */
public class RestaurantFragment extends Fragment {

	private static final String KEY_RESTORED_STATE = "restored_state";

	Button bOrder;
	LinearLayout layoutContainer;
	ProgressBar progressBar;
	ListView listView;

	Presenter presenter;


	public RestaurantFragment() {
		if(this.presenter == null) {
			this.presenter = PresenterFactory.newInstance(this);
		}
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("RESTAURANT", "onCreate");
		presenter.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		Log.e("RESTAURANT", "onCreateView");
		return inflater.inflate(R.layout.fragment_restaurant, container, false);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.e("RESTAURANT", "onActivityCreated");
		findViews();
		presenter.onActivityCreated(savedInstanceState);
	}


	@Override
	public void onResume() {
		super.onResume();
		Log.e("RESTAURANT", "onResume");
		presenter.onResume();
	}


	@Override
	public void onStop() {
		super.onStop();
		Log.e("RESTAURANT", "onStop");
		presenter.onStop();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("RESTAURANT", "onDestroy");
		presenter.onDestroy();
		presenter = null;
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
