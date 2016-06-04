package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;

import com.google.android.gms.maps.MapFragment;
import com.idurlen.foodordering.presenter.Presenter;
import com.idurlen.foodordering.factory.PresenterFactory;




/**
 * A simple {@link Fragment} subclass.
 */
public class ShowMapFragment extends MapFragment{

	Presenter presenter;


	public ShowMapFragment() {
		presenter = PresenterFactory.newInstance(this);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter.onCreate(savedInstanceState);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		presenter.onActivityCreated(savedInstanceState);
	}


	@Override
	public void onStop() {
		super.onStop();
		presenter.onStop();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		presenter.onDestroy();
		presenter = null;
	}
}
