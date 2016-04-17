package com.idurlen.foodordering.view.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.database.model.Restaurant;

import java.util.List;




/**
 * Adapter for {@link android.widget.ListView} for {@link com.idurlen.foodordering.view.fragment.HomeFragment}.
 * @author Ivan Durlen
 */
public class RestaurantsAdapter extends BaseAdapter{

	List<Restaurant> lRestaurants;
	LayoutInflater inflater;


	public RestaurantsAdapter(LayoutInflater inflater, List<Restaurant> lRestaurants){
		this.inflater = inflater;
		this.lRestaurants = lRestaurants;
	}


	@Override
	public int getCount() {
		return lRestaurants.size();
	}


	@Override
	public Object getItem(int position) {
		return lRestaurants.get(position);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = inflater.inflate(R.layout.list_restaurant, null);

		TextView tvTitle = (TextView) convertView.findViewById(R.id.tvRestaurantItemTitle);
		TextView tvWorking = (TextView) convertView.findViewById(R.id.tvRestaurantItemWorking);
		TextView tvAddress = (TextView) convertView.findViewById(R.id.tvRestaurantItemAddress);
		TextView tvDescription = (TextView) convertView.findViewById(R.id.tvRestaurantItemDesription);

		Restaurant restaurant = lRestaurants.get(position);
		tvTitle.setText(restaurant.getName());
		tvWorking.setText("Radi");//TODO: obojeni text koji označava da li prima narudžbe
		tvDescription.setText(restaurant.getDescription());
		tvAddress.setText(restaurant.getAddress());

		return convertView;
	}


}
