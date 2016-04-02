package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.factory.ControllerFactory;




public class ConfirmOrderFragment extends Fragment {

	private ProgressBar progressBar;
	private LinearLayout layoutContainer;

	public ConfirmOrderFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_confirm_order, container, false);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		findViews();
		ControllerFactory.newInstance(this).activate();
	}


	private void findViews(){
		layoutContainer = (LinearLayout) getView().findViewById(R.id.layoutConfirmOrder);
		progressBar = (ProgressBar) getView().findViewById(R.id.pbConfirmOrder);
	}


	public ProgressBar getProgressBar() { return progressBar; }

	public LinearLayout getLayoutContainer() { return layoutContainer; }

}