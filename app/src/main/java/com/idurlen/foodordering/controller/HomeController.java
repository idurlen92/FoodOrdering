package com.idurlen.foodordering.controller;
import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.idurlen.foodordering.database.model.Restaurant;
import com.idurlen.foodordering.view.fragment.HomeFragment;
import com.idurlen.foodordering.view.ui.adapter.RestaurantsAdapter;

import java.util.ArrayList;
import java.util.List;




public class HomeController implements Controller, AdapterView.OnItemClickListener {

	RestaurantsAdapter adapter;
	HomeFragment fragment;

	List<Restaurant> lRestaurants;//TODO: REAL DATA

	public HomeController(Fragment fragment){
		this.fragment = (HomeFragment) fragment;
		adapter = new RestaurantsAdapter(fragment);
	}


	@Override
	public void activate() {
		//TODO: real data!
		lRestaurants = new ArrayList<Restaurant>();
		for(int i = 1; i < 16; i++){
			Restaurant restaurant = new Restaurant();
			restaurant.setId(i + 1);
			restaurant.setName("Restoran " + i);
			restaurant.setAddress("Adresa " + i);
			restaurant.setCity("Grad " + i);
			restaurant.setDescription("Opis domene restorana " + i);
			lRestaurants.add(restaurant);
		}

		adapter.setList(lRestaurants);
		fragment.getLvRestaurants().setAdapter(adapter);
	}




	@Override
	public void setListeners() {
		fragment.getLvRestaurants().setOnItemClickListener(this);
	}


	@Override
	public void onClick(View v) { }


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}


}
