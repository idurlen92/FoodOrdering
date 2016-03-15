package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.view.ui.adapter.RestaurantsAdapter;



/**
 * @author Ivan Durlen
 */
public class HomeFragment extends Fragment{


	public HomeFragment() { }




	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RestaurantsAdapter adapter = new RestaurantsAdapter(this, null);

		//TODO
	}




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_home, container, false);
	}


}
