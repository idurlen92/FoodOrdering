package com.idurlen.foodordering.view.ui.adapter;

import android.app.Fragment;
import android.content.Context;
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

	Fragment fragment;
	LayoutInflater inflater;


	public RestaurantsAdapter(Fragment fragment){
		this.fragment = fragment;
		this.inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	public int getItemViewType(int position) {
		//TODO:
		return super.getItemViewType(position);
	}



	@Override
	public int getViewTypeCount() {
		//TODO:
		return super.getViewTypeCount();
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



	public void setList(List<Restaurant> lRestaurants){
		this.lRestaurants = lRestaurants;
	}


}
