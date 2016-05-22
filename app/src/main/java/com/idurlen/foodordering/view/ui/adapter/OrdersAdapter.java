package com.idurlen.foodordering.view.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.controller.OrdersController;
import com.idurlen.foodordering.database.model.Order;
import com.idurlen.foodordering.database.model.Restaurant;
import com.idurlen.foodordering.utils.DateTimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * Adapter for {@link android.widget.ListView} for {@link com.idurlen.foodordering.view.fragment.RestaurantFragment}.
 * @author Ivan Durlen
 */
public class OrdersAdapter extends BaseAdapter{

	public final int TYPE_GROUP_TITLE = 0;
	public final int TYPE_ORDER = 1;

	private Map<Integer, Restaurant> mIdToRestaurant;
	private Map<Integer, String> mPosToGroupTitle = new HashMap<>();
	private Map<Integer, Order> mPosToOrder = new HashMap<>();

	OrdersController controller;
	LayoutInflater inflater;


	public OrdersAdapter(OrdersController controller, LayoutInflater inflater, List<Order> lOrders, Map<Integer, Restaurant> mIdToRestaurant){
		this.controller = controller;
		this.inflater = inflater;
		this.mIdToRestaurant = mIdToRestaurant;

		mPosToGroupTitle.put(0, "Današnje narudžbe");
		int k = 1;

		for(Order ord : lOrders){
			if(!DateTimeUtils.isTodayDate(ord.getOrderTime())){
				String sOrderDate = DateTimeUtils.getDateFromTimestamp(ord.getOrderTime());
				if(! mPosToGroupTitle.containsValue(sOrderDate)) {
					mPosToGroupTitle.put(k++, sOrderDate);
				}
			}
			mPosToOrder.put(k++, ord);
		}
	}


	@Override
	public int getCount() {
		return mPosToGroupTitle.size() + mPosToOrder.size();
	}


	@Override
	public Object getItem(int position) {
		return getItemViewType(position) == TYPE_GROUP_TITLE ? mPosToGroupTitle.get(position) : mPosToOrder.get(position);
	}


	@Override
	public int getItemViewType(int position) {
		return mPosToGroupTitle.containsKey(position) ? TYPE_GROUP_TITLE : TYPE_ORDER;
	}


	@Override
	public int getViewTypeCount() {
		return 2;
	}


	@Override
	public long getItemId(int position) {
		return position;
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(getItemViewType(position) == TYPE_GROUP_TITLE){
			if(convertView == null) {
				convertView = inflater.inflate(R.layout.list_group_item, null);
			}
			((TextView) convertView.findViewById(R.id.tvGroupTItle)).setText(mPosToGroupTitle.get(position));
		}
		else{
			if(convertView == null) {
				convertView = inflater.inflate(R.layout.list_order , null);
			}
			setDataAndListeners(position, convertView);
		}
		return convertView;
	}



	/**
	 * Sets content and listeners for ListView item.
	 * @param position
	 * @param convertView
	 */
	private void setDataAndListeners(final int position, View convertView){
		Order currentOrder = mPosToOrder.get(position);
		((TextView) convertView.findViewById(R.id.tvOrdersRestaurant)).setText(
				mIdToRestaurant.get(currentOrder.getRestaurantId()).getName());
		((TextView) convertView.findViewById(R.id.tvOrdersTime)).setText(
				DateTimeUtils.getFormatedTimestamp(currentOrder.getOrderTime()));
		((TextView) convertView.findViewById(R.id.tvOrdersTotal)).setText("? KN");
	}


}
