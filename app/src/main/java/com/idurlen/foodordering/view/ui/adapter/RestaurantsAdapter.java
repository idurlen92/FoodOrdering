package com.idurlen.foodordering.view.ui.adapter;

import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.idurlen.foodordering.database.model.Restaurant;

import java.util.List;




/**
 * Adapter for {@link android.widget.ListView} for {@link com.idurlen.foodordering.view.fragment.HomeFragment}.
 * @author Ivan Durlen
 */
public class RestaurantsAdapter extends BaseAdapter{


	private List<Restaurant> lRestaurants;


	public RestaurantsAdapter(Fragment fragment, List<Restaurant> lRestaurants){
		this.lRestaurants = lRestaurants;
		//TODO
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
		//TODO:
		return null;
	}


}
