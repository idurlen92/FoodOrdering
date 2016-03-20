package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.controller.Controller;
import com.idurlen.foodordering.controller.OrderController;
import com.idurlen.foodordering.factory.ControllerFactory;




/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

	Controller controller;


	public OrderFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_order, container, false);
	}




	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		controller = (OrderController) ControllerFactory.newInstance(this);
		controller.activate();
	}
}
