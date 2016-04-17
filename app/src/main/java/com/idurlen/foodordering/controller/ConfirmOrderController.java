package com.idurlen.foodordering.controller;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.Dishes;
import com.idurlen.foodordering.database.model.Dish;
import com.idurlen.foodordering.database.model.Order;
import com.idurlen.foodordering.database.model.Restaurant;
import com.idurlen.foodordering.utils.DateTimeUtils;
import com.idurlen.foodordering.utils.Messenger;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.fragment.ConfirmOrderFragment;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 *
 * @author Ivan Durlen
 */
public class ConfirmOrderController implements Controller, AdapterView.OnItemSelectedListener{

	final String TITLE_ACTIONBAR = "Potvrda narudžbe";

	boolean isTimeValid = false;
	boolean isAddressValid = false;

	double dOrderTotal = 0;

	Restaurant selectedRestaurant;

	List<String> lAddressChoices;
	List<Dish> lSelectedDishes;
	Map<Integer, Integer> mDishIdQuantities;
	Deque<Object> qOrdersAndItems;

	SQLiteDatabase db;
	BackgroundTask task;
	SessionManager session;

	ConfirmOrderFragment fragment;


	public ConfirmOrderController(Fragment fragment){
		this.fragment = (ConfirmOrderFragment) fragment;

		session = SessionManager.getInstance(fragment.getActivity());
		lSelectedDishes = new ArrayList<>();
		qOrdersAndItems = new ArrayDeque<>();
		lAddressChoices = new ArrayList<>(Arrays.asList(new String[]{"Nije odabrano", "Postojeća adresa", "Druga adresa"}));

		selectedRestaurant = (Restaurant) Messenger.getObject(Messenger.KEY_RESTAURANT_OBJECT);
		mDishIdQuantities = new HashMap<>((HashMap<Integer, Integer>) Messenger.getObject(Messenger.KEY_SELECTED_DISHES_MAP));
		Messenger.clearAll();
	}


	@Override
	public void activate() {
		((AppCompatActivity) fragment.getActivity()).getSupportActionBar().setTitle(TITLE_ACTIONBAR);
		db = DatabaseManager.getInstance(fragment.getActivity()).getReadableDatabase();

		task = new BackgroundTask(fragment.getProgressBar(), fragment.getLayoutContainer(),
				new BackgroundOperation() {
					@Override
					public Object execInBackground() {
						lSelectedDishes.addAll(Dishes.getDishesById(db, mDishIdQuantities.keySet()));
						for(Dish dish : lSelectedDishes){
							fragment.getLayoutOrderItems().addView(createItemLayout(dish, mDishIdQuantities.get(dish.getId())));
							dOrderTotal += (dish.getPrice() * mDishIdQuantities.get(dish.getId()));
							Log.d("DISH", dish.getTitle() + ": " + mDishIdQuantities.get(dish.getId()));
						}
						Order order = new Order();

						return null;
					}

					@Override
					public void execAfter(Object object) {
						db.close();
						fragment.getTvOrderDate().setText(DateTimeUtils.getCurrentDateString());
						fragment.getTvOrderTotal().setText(String.format("%.2f KN", dOrderTotal));
						setListeners();
					}
		});
		task.execute();
	}


	@Override
	public void setListeners() {
		fragment.getBConfirmOrder().setOnClickListener(this);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(fragment.getActivity(),
				R.layout.support_simple_spinner_dropdown_item, lAddressChoices);
		fragment.getSpOrderAddress().setAdapter(adapter);
		fragment.getSpOrderAddress().setOnItemSelectedListener(this);

		fragment.getEtDeliveryTime().addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//TODO:
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}


	@Override
	public void onClick(View v) {
		if(isInputValid()) {
			insertOrder();
		}
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if(position > 0 && fragment.getSpOrderAddress().getCount() > 2){
			lAddressChoices.remove(0);
			((ArrayAdapter<String>) fragment.getSpOrderAddress().getAdapter()).notifyDataSetChanged();
		}
		else{
			fragment.getLayoutOrderAltAddress().setVisibility(position == 1  ? View.VISIBLE : View.GONE);
		}
	}


	@Override
	public void onNothingSelected(AdapterView<?> parent) { /* --- Do nothing --- */ }




	private LinearLayout createItemLayout(Dish dish, int quantity){
		LinearLayout layoutItem = new LinearLayout(fragment.getActivity());
		layoutItem.setOrientation(LinearLayout.HORIZONTAL);
		layoutItem.setVisibility(View.VISIBLE);
		LinearLayout.LayoutParams itemLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		itemLayoutParams.setMargins(0, 15, 0, 0);
		layoutItem.setLayoutParams(itemLayoutParams);

		TextView tvTitle = new TextView(fragment.getActivity());
		TextView tvPrice = new TextView(fragment.getActivity());
		TextView tvQuantity = new TextView(fragment.getActivity());

		tvTitle.setTextSize(16);
		tvTitle.setTypeface(null, Typeface.BOLD);
		tvQuantity.setTypeface(null, Typeface.BOLD);

		tvTitle.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2));
		tvPrice.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

		LinearLayout.LayoutParams tvQuantityParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
		tvQuantityParams.setMargins(10, 0, 0, 0);
		tvQuantity.setLayoutParams(tvQuantityParams);

		tvTitle.setText(dish.getTitle());
		tvPrice.setText(String.format("%.2f KN", dish.getPrice()));
		tvQuantity.setText(Integer.toString(quantity));

		layoutItem.addView(tvTitle, tvTitle.getLayoutParams());
		layoutItem.addView(tvPrice, tvPrice.getLayoutParams());
		layoutItem.addView(tvQuantity, tvQuantity.getLayoutParams());

		return layoutItem;
	}


	private boolean isInputValid(){
		isAddressValid = (fragment.getSpOrderAddress().getCount() == 2 && fragment.getSpOrderAddress().getSelectedItemPosition() == 0) ||
				(fragment.getSpOrderAddress().getCount() == 2 && !fragment.getEtOrderAltAddress().getText().toString().isEmpty());
		return isTimeValid && isAddressValid;
	}



	private void insertOrder(){
		
	}


}
