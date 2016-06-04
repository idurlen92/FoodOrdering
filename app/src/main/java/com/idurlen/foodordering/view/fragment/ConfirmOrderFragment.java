package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.factory.PresenterFactory;
import com.idurlen.foodordering.presenter.Presenter;




public class ConfirmOrderFragment extends Fragment {

	private Button bConfirmOrder;

	private EditText etOrderAltAddress;
	private LinearLayout layoutOrderItems;
	private Spinner spDeliveryTime;
	private Spinner spOrderAddress;
	private TextView tvOrderDate;
	private TextView tvOrderTotal;

	private TextInputLayout layoutOrderAltAddress;

	private ProgressBar progressBar;
	private ScrollView layoutContainer;

	Presenter presenter;


	public ConfirmOrderFragment() {
		presenter = PresenterFactory.newInstance(this);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("CONFIRM ORDER", "onCreate");
		presenter.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		Log.e("CONFIRM ORDER", "onCreateView");
		return inflater.inflate(R.layout.fragment_confirm_order, container, false);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.e("CONFIRM ORDER", "onActivityCreated");
		findViews();
		presenter.onActivityCreated(savedInstanceState);
	}


	@Override
	public void onStart() {
		super.onStart();
		presenter.onStart();
	}


	@Override
	public void onResume() {
		super.onResume();
		Log.e("CONFIRM ORDER", "onResume");
		presenter.onResume();
	}


	@Override
	public void onStop() {
		super.onStop();
		Log.e("CONFIRM ORDER", "onStop");
		presenter.onStop();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("CONFIRM ORDER", "onDestroy");
		presenter.onDestroy();
		presenter = null;
	}


	private void findViews(){
		layoutContainer = (ScrollView) getView().findViewById(R.id.layoutConfirmOrder);
		progressBar = (ProgressBar) getView().findViewById(R.id.pbConfirmOrder);

		layoutOrderItems = (LinearLayout) getView().findViewById(R.id.layoutOrderItems);
		layoutOrderAltAddress = (TextInputLayout) getView().findViewById(R.id.layoutOrderAltAddress);

		bConfirmOrder = (Button) getView().findViewById(R.id.bConfirmOrder);
		etOrderAltAddress = (EditText) getView().findViewById(R.id.etOrderAltAddress);
		spDeliveryTime = (Spinner) getView().findViewById(R.id.spDeliveryTIme);
		spOrderAddress = (Spinner) getView().findViewById(R.id.spOrderAddress);
		tvOrderDate = (TextView) getView().findViewById(R.id.tvOrderDate);
		tvOrderTotal = (TextView) getView().findViewById(R.id.tvOrderTotal);
	}


	public ProgressBar getProgressBar() { return progressBar; }

	public ScrollView getLayoutContainer() { return layoutContainer; }

	public TextInputLayout getLayoutOrderAltAddress() { return layoutOrderAltAddress; }

	public LinearLayout getLayoutOrderItems() { return layoutOrderItems; }

	public Button getBConfirmOrder() { return bConfirmOrder; }

	public EditText getEtOrderAltAddress() { return etOrderAltAddress; }

	public Spinner getSpDeliveryTime() { return spDeliveryTime; }

	public Spinner getSpOrderAddress() { return spOrderAddress; }

	public TextView getTvOrderDate() { return tvOrderDate; }

	public TextView getTvOrderTotal() { return tvOrderTotal; }

}