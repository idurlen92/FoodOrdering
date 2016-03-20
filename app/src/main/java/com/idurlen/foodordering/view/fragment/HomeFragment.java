package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.controller.HomeController;
import com.idurlen.foodordering.factory.ControllerFactory;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.view.MainActivity;


/**
 * @author Ivan Durlen
 */
public class HomeFragment extends Fragment{

	SessionManager sessionManager;

	HomeController controller;
	MainActivity activity;

	ListView lvRestaurants;
	TextView tvTitle;


	public HomeFragment() {

	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		return view;
	}




	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		sessionManager = SessionManager.getInstance(getActivity());
		findViews();
		controller = (HomeController) ControllerFactory.newInstance(this);
		controller.activate();
	}




	public ListView getLvRestaurants() {
		return lvRestaurants;
	}


	private void findViews(){
		lvRestaurants = (ListView) getView().findViewById(R.id.lvRestaurants);
		tvTitle = (TextView) getView().findViewById(R.id.tvHomeTitle);
		tvTitle.setText("Restorani za grad: " /*TODO: + sessionManager.getCity()*/);
	}


}
