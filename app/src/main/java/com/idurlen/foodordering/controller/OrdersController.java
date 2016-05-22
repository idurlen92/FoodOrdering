package com.idurlen.foodordering.controller;
import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.Orders;
import com.idurlen.foodordering.database.helper.Restaurants;
import com.idurlen.foodordering.database.model.Order;
import com.idurlen.foodordering.database.model.OrderItem;
import com.idurlen.foodordering.database.model.Restaurant;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.fragment.OrdersFragment;
import com.idurlen.foodordering.view.ui.adapter.OrdersAdapter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;




/**
 * Created by Duky on 22.5.2016..
 */
public class OrdersController implements Controller, AdapterView.OnItemLongClickListener{

	List<Order> lOrders;
	List<OrderItem> lOrderItems;

	Map<Integer, Restaurant> mRestaurants;

	DatabaseManager databaseManager;
	SessionManager session;

	BackgroundTask ordersTask;
	OrdersAdapter adapter;

	OrdersFragment fragment;


	public OrdersController(Fragment fragment){
		this.fragment = (OrdersFragment) fragment;

		databaseManager = DatabaseManager.getInstance(fragment.getActivity());
		session = SessionManager.getInstance(fragment.getActivity());
	}


	@Override
	public void activate() {
		((AppCompatActivity) fragment.getActivity()).getSupportActionBar().setTitle("Izvršene narudžbe");
		getUserOrders();
		setListeners();
	}




	@Override
	public void setListeners() {
		fragment.getLvUserOrders().setOnItemLongClickListener(OrdersController.this);
	}


	@Override
	public void onClick(View v) {
	}




	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		//TODO:
		return false;
	}




	private void getUserOrders(){
		ordersTask = new BackgroundTask(fragment.getPbUserOrders(), fragment.getLvUserOrders(), new BackgroundOperation() {
			@Override
			public Object execInBackground() {
				SQLiteDatabase db = databaseManager.getReadableDatabase();

				lOrders = Orders.getOrdersOfUser(db, session.getUserId());
				//lOrderItems = OrderItems.getOrderItemsOfUser(db, getOrderIds());
				mRestaurants = Restaurants.getRestaurantsMapByIds(db, getRestaurantIdsFromOrders());

				db.close();

				return null;
			}

			@Override
			public void execAfter(Object object) {
				super.execAfter(object);
				adapter = new OrdersAdapter(OrdersController.this,
						(LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
						lOrders, mRestaurants);
				fragment.getLvUserOrders().setAdapter(adapter);
			}
		});

		ordersTask.execute();
	}


	private Set<Integer> getOrderIds(){
		Set<Integer> sOrderIds = new TreeSet<>();
		for(Order ord : lOrders){
			sOrderIds.add(ord.getId());
		}
		return sOrderIds;
	}


	private Set<Integer> getRestaurantIdsFromOrders(){
		Set<Integer> sRestaurantIds = new TreeSet<>();
		for(Order ord : lOrders){
			sRestaurantIds.add(ord.getRestaurantId());
		}
		return sRestaurantIds;
	}


}
