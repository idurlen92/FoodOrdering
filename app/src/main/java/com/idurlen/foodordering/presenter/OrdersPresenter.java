package com.idurlen.foodordering.presenter;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.Orders;
import com.idurlen.foodordering.database.helper.Restaurants;
import com.idurlen.foodordering.database.model.Order;
import com.idurlen.foodordering.database.model.Restaurant;
import com.idurlen.foodordering.utils.DateTimeUtils;
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
 * @author Ivan Durlen
 */
public class OrdersPresenter extends Presenter implements ListView.OnItemLongClickListener{

	private static final String TITLE = "Izvršene narudžbe";

	List<Order> lOrders;
	Map<Integer, Restaurant> mIdToRestaurant;

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
		if(mIdToRestaurant != null) {
			mIdToRestaurant.clear();
		}
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		Order order = (Order) ((OrdersFragment) getFragment()).getLvUserOrders().getItemAtPosition(position);
		if( DateTimeUtils.isTodayDate(order.getOrderTime()) ){
			AlertDialog dialog = createAlertDialog(order);
			dialog.show();
		}
		return false;
	}


	private void getUserOrders(){
		OrdersFragment fragment = (OrdersFragment) getFragment();

		BackgroundTask task = new BackgroundTask(fragment.getPbUserOrders(), fragment.getLvUserOrders(), new BackgroundOperation() {
			@Override
			public Object execInBackground() {
				SQLiteDatabase db = databaseManager.getReadableDatabase();
				lOrders = Orders.getOrdersOfUser(db, session.getUserId());
				mIdToRestaurant = Restaurants.getRestaurantsMapByIds(db, getRestaurantIdsFromOrders());
				db.close();

				return null;
			}

			@Override
			public void execAfter(Object object) {
				super.execAfter(object);
				setListAdapter();
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
				lOrders, mIdToRestaurant);
		((OrdersFragment) getFragment()).getLvUserOrders().setAdapter(adapter);
		((OrdersFragment) getFragment()).getLvUserOrders().setOnItemLongClickListener(this);
	}


	private AlertDialog createAlertDialog(Order order){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Otkazivanje narudžbe").setMessage("Otkazati narudžbu: " +
				mIdToRestaurant.get(order.getRestaurantId()).getName() + ", " +
				order.getDeliveryTime() + "?");

		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which == DialogInterface.BUTTON_POSITIVE){
					//TODO
					dialog.dismiss();
				}
				else{
					dialog.dismiss();
				}
			}
		};
		builder.setPositiveButton("Da", listener);
		builder.setNegativeButton("Ne", listener);

		return builder.create();
	}


}
