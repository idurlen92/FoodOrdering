package com.idurlen.foodordering.presenter;
import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.Orders;
import com.idurlen.foodordering.database.helper.Restaurants;
import com.idurlen.foodordering.database.model.Order;
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
public class OrdersPresenter extends Presenter implements AdapterView.OnItemLongClickListener{

	private static final String TITLE = "Izvršene narudžbe";

	List<Order> lOrders;

	Map<Integer, Restaurant> mRestaurants;

	DatabaseManager databaseManager;
	SessionManager session;


	public OrdersPresenter(Fragment fragment){
		super(fragment, TITLE);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) { }


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		databaseManager = DatabaseManager.getInstance(getApplicationContext());
		session = SessionManager.getInstance(getApplicationContext());
		getUserOrders();
	}


	@Override
	public void onPause() { }


	@Override
	public void onStop() {
		databaseManager = null;
		session = null;
		if(lOrders != null) {
			lOrders.clear();
		}
		if(mRestaurants != null) {
			mRestaurants.clear();
		}
	}


	public void setListeners() {
		((OrdersFragment) getFragment()).getLvUserOrders().setOnItemLongClickListener(OrdersPresenter.this);
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		//TODO:
		return false;
	}


	private void getUserOrders(){
		OrdersFragment fragment = (OrdersFragment) getFragment();

		BackgroundTask task = new BackgroundTask(fragment.getPbUserOrders(), fragment.getLvUserOrders(), new BackgroundOperation() {
			@Override
			public Object execInBackground() {
				SQLiteDatabase db = databaseManager.getReadableDatabase();
				lOrders = Orders.getOrdersOfUser(db, session.getUserId());
				mRestaurants = Restaurants.getRestaurantsMapByIds(db, getRestaurantIdsFromOrders());
				db.close();

				return null;
			}

			@Override
			public void execAfter(Object object) {
				super.execAfter(object);
				setListAdapter();
				setListeners();
				setIsActivated(true);
			}
		});

		task.execute();
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


	private void setListAdapter(){
		OrdersAdapter adapter = new OrdersAdapter(OrdersPresenter.this,
				(LayoutInflater) getFragment().getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
				lOrders, mRestaurants);
		((OrdersFragment) getFragment()).getLvUserOrders().setAdapter(adapter);
	}


}
