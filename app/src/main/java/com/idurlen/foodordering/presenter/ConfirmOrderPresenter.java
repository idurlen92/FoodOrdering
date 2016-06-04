package com.idurlen.foodordering.presenter;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.idurlen.foodordering.utils.MenuController;
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
public class ConfirmOrderPresenter extends Presenter implements View.OnClickListener{

	private static final String TITLE = "Potvrda narudžbe";

	private static final String OPTION_NOT_SELECTED = "Nije odabrano";
	private static final String OPTION_ALT_ADDRESS = "Druga adresa";
	private static final String OPTION_DEFAULT_ADDRESS = "Postojeća adresa";

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

	SessionManager session;
	DatabaseManager databaseManager;


	public ConfirmOrderPresenter(Fragment fragment){
		super(fragment, TITLE);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) { }


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		lAddressChoices = new ArrayList<>(Arrays.asList(new String[]{OPTION_NOT_SELECTED, OPTION_DEFAULT_ADDRESS, OPTION_ALT_ADDRESS}));
		lDeliveryTimeChoices = new ArrayList<>(Arrays.asList(new String[]{OPTION_NOT_SELECTED}));

		selectedRestaurant = (Restaurant) Messenger.getObject(Messenger.KEY_RESTAURANT_OBJECT);
		mDishIdQuantities = new HashMap<>((HashMap<Integer, Integer>) Messenger.getObject(Messenger.KEY_SELECTED_DISHES_MAP));
		Messenger.clearAll();

		session = SessionManager.getInstance(getApplicationContext());
		databaseManager = DatabaseManager.getInstance(getApplicationContext());

		ordersRequest = new OrdersRequest();
		orderItemsRequest = new OrderItemsRequest();
		sOrderAddress = session.getAddress();

		getDishes();
	}


	@Override
	public void onPause() { }


	@Override
	public void onStop(){
		order = null;
		selectedRestaurant = null;
		ordersRequest = null;
		orderItemsRequest = null;
		session = null;
		databaseManager = null;

		mDishIdQuantities.clear();
		lSelectedDishes.clear();
		lDeliveryTimeChoices.clear();
		lAddressChoices.clear();
		if(lOrderItems != null) {
			lOrderItems.clear();
		}
	}


	@Override
	public void onClick(View v) {
		if(isInputValid() && (order == null || lOrderItems == null)) {
			insertOrder();
		}
		else{
			Snackbar.make( ((ConfirmOrderFragment) getFragment()).getBConfirmOrder(), "Unesite sve podatke...", Snackbar.LENGTH_SHORT).show();
		}
	}


	public void getDishes() {
		final ConfirmOrderFragment fragment = (ConfirmOrderFragment) getFragment();

		BackgroundTask task = new BackgroundTask(fragment.getProgressBar(), fragment.getLayoutContainer(),
			new BackgroundOperation() {
				@Override
				public Object execInBackground() {
					SQLiteDatabase db = databaseManager.getReadableDatabase();

					lSelectedDishes = Dishes.getDishesById(db, mDishIdQuantities.keySet());
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
					setIsActivated(true);
				}
		});

		task.execute();
	}



	public void setListeners() {
		final ConfirmOrderFragment fragment = (ConfirmOrderFragment) getFragment();

		fragment.getBConfirmOrder().setOnClickListener(this);

		fragment.getSpOrderAddress().setAdapter(new ArrayAdapter<String>(fragment.getActivity(),
				R.layout.support_simple_spinner_dropdown_item, lAddressChoices));
		setSpOrderAddressListener(fragment);

		fragment.getSpDeliveryTime().setAdapter(new ArrayAdapter<String>(fragment.getActivity(),
				R.layout.support_simple_spinner_dropdown_item, lDeliveryTimeChoices));
		setSpDeliveryTimeListener(fragment);

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



	private void setSpOrderAddressListener(final ConfirmOrderFragment fragment){
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
	}



	private void setSpDeliveryTimeListener(final ConfirmOrderFragment fragment){
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
	}


	private LinearLayout createItemLayout(Dish dish, int quantity){
		ConfirmOrderFragment fragment = (ConfirmOrderFragment) getFragment();

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
		ConfirmOrderFragment fragment = (ConfirmOrderFragment) getFragment();

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
		ConfirmOrderFragment fragment = (ConfirmOrderFragment) getFragment();

		isAddressValid = (fragment.getSpOrderAddress().getCount() == 2 && fragment.getSpOrderAddress().getSelectedItemPosition() == 0) ||
				(fragment.getSpOrderAddress().getCount() == 2 && !fragment.getEtOrderAltAddress().getText().toString().isEmpty());
		isTimeValid = (! OPTION_NOT_SELECTED.equals((String) fragment.getSpDeliveryTime().getSelectedItem()));
		return isTimeValid && isAddressValid;
	}



	private void insertOrder(){
		final ConfirmOrderFragment fragment = (ConfirmOrderFragment) getFragment();

		BackgroundTask task = new BackgroundTask(fragment.getActivity(), "Unos podataka", new BackgroundOperation() {
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
			}
		});

		task.execute();
	}


}
