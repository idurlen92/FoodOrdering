package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.controller.Controller;
import com.idurlen.foodordering.factory.ControllerFactory;




/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

	private ProgressBar pbUserOrders;
	private ListView lvUserOrders;

	Controller controller;

	public OrdersFragment() {
		// Required empty public constructor
	}




	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		controller = ControllerFactory.newInstance(this);
	}




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_orders, container, false);
	}




	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		findViews();
		controller.activate();
	}


	private void findViews(){
		pbUserOrders = (ProgressBar) getView().findViewById(R.id.pbUserOrders);
		lvUserOrders = (ListView) getView().findViewById(R.id.lvUserOrders);
	}


	public ListView getLvUserOrders(){ return lvUserOrders; }

	public ProgressBar getPbUserOrders(){ return pbUserOrders; }


}
