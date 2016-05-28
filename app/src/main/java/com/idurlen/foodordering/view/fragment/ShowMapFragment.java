package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;

import com.google.android.gms.maps.MapFragment;
import com.idurlen.foodordering.controller.Controller;
import com.idurlen.foodordering.factory.ControllerFactory;




/**
 * A simple {@link Fragment} subclass.
 */
public class ShowMapFragment extends MapFragment{

	Controller controller;

	public ShowMapFragment() {
		// Required empty constructor
		controller = ControllerFactory.newInstance(this);
	}




	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		controller.activate();
	}
}
