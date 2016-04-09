package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.factory.ControllerFactory;
import com.idurlen.foodordering.utils.SessionManager;


/**
 * @author Ivan Durlen
 */
public class HomeFragment extends Fragment{

	SessionManager sessionManager;

	LinearLayout layoutContainer;
	ListView listView;
	ProgressBar progressBar;
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
		ControllerFactory.newInstance(this).activate();
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

	public TextView getTvTitle() {
		return tvTitle;
	}


	private void findViews(){
		layoutContainer = (LinearLayout) getView().findViewById(R.id.layoutHome);
		listView = (ListView) getView().findViewById(R.id.lvRestaurants);
		progressBar = (ProgressBar) getView().findViewById(R.id.pbHome);
		tvTitle = (TextView) getView().findViewById(R.id.tvHomeTitle);

		tvTitle.setText("Restorani za grad: " /*TODO: + sessionManager.getCity()*/);
	}


}
