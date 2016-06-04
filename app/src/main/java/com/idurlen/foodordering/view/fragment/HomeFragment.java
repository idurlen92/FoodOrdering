package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.factory.PresenterFactory;
import com.idurlen.foodordering.presenter.Presenter;


/**
 * @author Ivan Durlen
 */
public class HomeFragment extends Fragment{

	LinearLayout layoutContainer;
	ListView listView;
	ProgressBar progressBar;

	Presenter presenter;


	public HomeFragment() {
		if(this.presenter == null) {
			this.presenter = PresenterFactory.newInstance(this);
		}
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("HOME", "onCreate");
		presenter.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		Log.e("HOME", "onCreateView");
		return inflater.inflate(R.layout.fragment_home, container, false);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.e("HOME", "onActivityCreated");
		findViews();
		presenter.onActivityCreated(savedInstanceState);
	}


	@Override
	public void onResume() {
		super.onResume();
		Log.e("HOME", "onResume");
		presenter.onResume();
	}


	@Override
	public void onStop() {
		super.onStop();
		Log.e("HOME", "onStop");
		presenter.onStop();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("HOME", "onDestroy");
		presenter.onDestroy();
		presenter = null;
	}




	public LinearLayout getLayoutContainer() {
		return layoutContainer;
	}

	public ListView getListView() {
		return listView;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}


	private void findViews(){
		layoutContainer = (LinearLayout) getView().findViewById(R.id.layoutHome);
		listView = (ListView) getView().findViewById(R.id.lvRestaurants);
		progressBar = (ProgressBar) getView().findViewById(R.id.pbHome);
	}


}
