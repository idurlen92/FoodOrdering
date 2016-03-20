package com.idurlen.foodordering.controller;

import android.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.idurlen.foodordering.database.model.Restaurant;




/**
 * MVC Controller component for OrderFragment.
 */
public class OrderController implements Controller{

	public static Restaurant restaurant;

	Fragment fragment;

	public OrderController(Fragment fragment){
		this.fragment = fragment;
	}


	@Override
	public void activate() {
		Toast.makeText(fragment.getActivity(), restaurant.getName(), Toast.LENGTH_SHORT).show();
	}




	@Override
	public void setListeners() {

	}




	@Override
	public void onClick(View v) {

	}
}
