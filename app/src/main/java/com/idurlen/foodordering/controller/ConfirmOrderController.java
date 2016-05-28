package com.idurlen.foodordering.controller;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.Dishes;
import com.idurlen.foodordering.database.helper.OrderItems;
import com.idurlen.foodordering.database.helper.Orders;
import com.idurlen.foodordering.database.model.Dish;
import com.idurlen.foodordering.database.model.Order;
import com.idurlen.foodordering.database.model.OrderItem;
import com.idurlen.foodordering.database.model.Restaurant;
import com.idurlen.foodordering.net.OrderItemsRequest;
import com.idurlen.foodordering.net.OrdersRequest;
import com.idurlen.foodordering.utils.DateTimeUtils;
import com.idurlen.foodordering.utils.Messenger;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.MainActivity;
import com.idurlen.foodordering.view.fragment.ConfirmOrderFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 *
 * @author Ivan Durlen
 */
public class ConfirmOrderController implements Controller{

	final String TITLE_ACTIONBAR = "Potvrda narudžbe";
	final String OPTION_NOT_SELECTED = "Nije odabrano";
	final String OPTION_ALT_ADDRESS = "Druga adresa";
	final String OPTION_DEFAULT_ADDRESS = "Postojeća adresa";

	boolean isTimeValid = false;
	boolean isAddressValid = false;

	double dOrderTotal = 0;

	String sOrderAddress;

	Map<Integer, Integer> mDishIdQuantities;

	List<Dish> lSelectedDishes;
	List<String> lDeliveryTimeChoices;
	List<String> lAddressChoices;

	List<Object> lOrderItems;
	Order order;

	Restaurant selectedRestaurant;

	OrdersRequest ordersRequest;
	OrderItemsRequest orderItemsRequest;

	BackgroundTask task;
	BackgroundTask insertTask;
	SessionManager session;

	ConfirmOrderFragment fragment;


	public ConfirmOrderController(Fragment fragment){
		this.fragment = (ConfirmOrderFragment) fragment;

		lSelectedDishes = new ArrayList<>();
		lAddressChoices = new ArrayList<>(Arrays.asList(new String[]{OPTION_NOT_SELECTED, OPTION_DEFAULT_ADDRESS, OPTION_ALT_ADDRESS}));
		lDeliveryTimeChoices = new ArrayList<>(Arrays.asList(new String[]{OPTION_NOT_SELECTED}));

		ordersRequest = new OrdersRequest();
		orderItemsRequest = new OrderItemsRequest();

		session = SessionManager.getInstance(fragment.getActivity());
		sOrderAddress = session.getAddress();

		Log.d("ADDRESS", session.getAddress());

		selectedRestaurant = (Restaurant) Messenger.getObject(Messenger.KEY_RESTAURANT_OBJECT);
		mDishIdQuantities = new HashMap<>((HashMap<Integer, Integer>) Messenger.getObject(Messenger.KEY_SELECTED_DISHES_MAP));
		Messenger.clearAll();
	}


	@Override
	public void activate() {
		((AppCompatActivity) fragment.getActivity()).getSupportActionBar().setTitle(TITLE_ACTIONBAR);

		task = new BackgroundTask(fragment.getProgressBar(), fragment.getLayoutContainer(),
				new BackgroundOperation() {
					@Override
					public Object execInBackground() {
						SQLiteDatabase db = DatabaseManager.getInstance(fragment.getActivity()).getReadableDatabase();

						lSelectedDishes.addAll(Dishes.getDishesById(db, mDishIdQuantities.keySet()));
						for(Dish dish : lSelectedDishes){
							fragment.getLayoutOrderItems().addView(createItemLayout(dish, mDishIdQuantities.get(dish.getId())));
							dOrderTotal += (dish.getPrice() * mDishIdQuantities.get(dish.getId()));
							Log.d("DISH", dish.getTitle() + ": " + mDishIdQuantities.get(dish.getId()));
						}

						db.close();
						return null;
					}

					@Override
					public void execAfter(Object object) {
						setData();
						setListeners();
					}
				});
		task.execute();
	}



	@Override
	public void setListeners() {
		fragment.getBConfirmOrder().setOnClickListener(this);

		fragment.getSpOrderAddress().setAdapter(new ArrayAdapter<String>(fragment.getActivity(),
				R.layout.support_simple_spinner_dropdown_item, lAddressChoices));
		fragment.getSpOrderAddress().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String clickedItem = (String) parent.getItemAtPosition(position);

				if(! OPTION_NOT_SELECTED.equals(clickedItem) && lAddressChoices.contains(OPTION_NOT_SELECTED)) {
					lAddressChoices.remove(0);
					((ArrayAdapter<String>) fragment.getSpOrderAddress().getAdapter()).notifyDataSetChanged();
					fragment.getSpOrderAddress().setSelection(position - 1);
				}
				if(OPTION_DEFAULT_ADDRESS.equals(clickedItem)) {
					sOrderAddress = session.getAddress();
				}
				else {
					sOrderAddress = fragment.getEtOrderAltAddress().getText().toString();
				}
				fragment.getLayoutOrderAltAddress().setVisibility(OPTION_ALT_ADDRESS.equals(clickedItem) ? View.VISIBLE : View.GONE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) { /* --- Do nothing --- */ }
		});

		fragment.getSpDeliveryTime().setAdapter(new ArrayAdapter<String>(fragment.getActivity(),
				R.layout.support_simple_spinner_dropdown_item, lDeliveryTimeChoices));
		fragment.getSpDeliveryTime().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String clickedItem = (String) parent.getItemAtPosition(position);
				if(! OPTION_NOT_SELECTED.equals(clickedItem) && lDeliveryTimeChoices.contains(OPTION_NOT_SELECTED)) {
					lDeliveryTimeChoices.remove(0);
					((ArrayAdapter<String>) fragment.getSpDeliveryTime().getAdapter()).notifyDataSetChanged();
					fragment.getSpDeliveryTime().setSelection(position - 1);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) { /* --- Do nothing --- */ }
		});

		fragment.getEtOrderAltAddress().addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				sOrderAddress = s.toString();
			}
		});
	}



	@Override
	public void onClick(View v) {
		if(isInputValid() && (order == null || lOrderItems == null)) {
			insertOrder();
		}
		else{
			Snackbar.make(fragment.getBConfirmOrder(), "Unesite sve podatke...", Snackbar.LENGTH_SHORT).show();
		}
	}



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



	private void setData(){
		fragment.getTvOrderDate().setText(DateTimeUtils.getCurrentDateString());
		fragment.getTvOrderTotal().setText(String.format("%.2f KN", dOrderTotal));

		// --------- Currently working only for multiples of 30 min.
		float startDeliveryTime = Integer.parseInt(selectedRestaurant.getDeliveringFrom().substring(0,selectedRestaurant.getDeliveringFrom().indexOf(":"))) +
				(selectedRestaurant.getDeliveringFrom().contains("30") ? 0.5f : 0f);
		float endDeliveryTime = Integer.parseInt(selectedRestaurant.getDeliveringUntil().substring(0,selectedRestaurant.getDeliveringUntil().indexOf(":"))) +
				(selectedRestaurant.getDeliveringUntil().contains("30") ? 0.5f : 0f);
		for(float deliveryTime = startDeliveryTime; deliveryTime <= endDeliveryTime; deliveryTime += 0.5f){
			String sTime = Integer.toString((int) deliveryTime) + ":" + ( (deliveryTime - (int) deliveryTime == 0) ? "00" : 30);
			lDeliveryTimeChoices.add(sTime);
		}
	}



	private boolean isInputValid(){
		isAddressValid = (fragment.getSpOrderAddress().getCount() == 2 && fragment.getSpOrderAddress().getSelectedItemPosition() == 0) ||
				(fragment.getSpOrderAddress().getCount() == 2 && !fragment.getEtOrderAltAddress().getText().toString().isEmpty());
		isTimeValid = (! OPTION_NOT_SELECTED.equals((String) fragment.getSpDeliveryTime().getSelectedItem()));
		return isTimeValid && isAddressValid;
	}



	private void insertOrder(){
		insertTask = new BackgroundTask(fragment.getActivity(), "Unos podataka", new BackgroundOperation() {
			@Override
			public Object execInBackground() {
				SQLiteDatabase db = DatabaseManager.getInstance(fragment.getActivity()).getWritableDatabase();
				boolean isError = false;

				try{
					db.beginTransactionNonExclusive();
					int orderId = 0;
					// ---------- 1) Insert Order ----------
					if(order == null) {
						order = new Order(session.getUserId(), selectedRestaurant.getId(), session.getCity(), sOrderAddress,
								DateTimeUtils.getCurrentTimeStampString(), (String) fragment.getSpDeliveryTime().getSelectedItem());
						orderId = ordersRequest.insert(order);
						isError = (orderId <= 0);
						if(orderId > 0){
							order.setId(orderId);
							Orders.insertOrder(db, order);
						}
					}

					// ---------- 2) Insert Order Items ----------
					if(orderId > 0) {
						lOrderItems = new ArrayList<>();
						for(Dish dish : lSelectedDishes) {
							lOrderItems.add(new OrderItem(orderId, dish.getId(), mDishIdQuantities.get(dish.getId())));
						}

						orderItemsRequest.insertAll(lOrderItems);
						for(Object item : lOrderItems){
							OrderItem orderItem = (OrderItem) item;
							OrderItems.insertOrderItem(db, orderItem);
						}
						db.setTransactionSuccessful();
					}
				}
				catch(Exception e){
					isError = true;
					e.printStackTrace();
				}
				finally{
					db.endTransaction();
					db.close();
				}

				return isError;
			}

			@Override
			public void execAfter(Object object) {
				super.execAfter(object);
				boolean isError = (boolean) object;

				if(isError){
					Snackbar.make(fragment.getBConfirmOrder(), "Greška u unosu", Snackbar.LENGTH_SHORT).show();
				}
				else{
					((MainActivity) fragment.getActivity()).pushFragment(MenuController.OPTION_HOME);
					Toast toast = Toast.makeText(fragment.getActivity(), "Narudžba je poslana", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.TOP, 0, 0);
					toast.show();
				}
				insertTask = null;
			}
		});

		insertTask.execute();
	}


}
