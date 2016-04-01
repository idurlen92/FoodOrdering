package com.idurlen.foodordering.view.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.database.model.Dish;
import com.idurlen.foodordering.database.model.DishType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * Adapter for {@link android.widget.ListView} for {@link com.idurlen.foodordering.view.fragment.RestaurantFragment}.
 * @author Ivan Durlen
 */
public class DishesAdapter extends BaseAdapter{

	private final int TYPE_GROUP_TITLE = 0;
	private final int TYPE_DISH = 1;

	private int size = 0;

	private Map<Integer, DishType> mPosToDishType;
	private Map<Integer, Dish> mPosToDish;

	LayoutInflater inflater;


	public DishesAdapter(LayoutInflater inflater,  Map<DishType, List<Dish>> mDishesByType){
		this.inflater = inflater;
		mPosToDishType = new HashMap<Integer, DishType>();
		mPosToDish = new HashMap<>();

		int k = 0;
		for(DishType key : mDishesByType.keySet()){
			mPosToDishType.put(k++, key);
			for(Dish value : mDishesByType.get(key)){
				mPosToDish.put(k++, value);
			}
		}

		size = k;
	}


	@Override
	public int getCount() {
		return size;
	}


	@Override
	public Object getItem(int position) {
		return getItemViewType(position) == TYPE_GROUP_TITLE ? mPosToDishType.get(position) : mPosToDish.get(position);
	}


	@Override
	public int getItemViewType(int position) {
		return mPosToDishType.containsKey(position) ? TYPE_GROUP_TITLE : TYPE_DISH;
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
				convertView = inflater.inflate(R.layout.list_dish_group, null);
			}
			DishType dishType = mPosToDishType.get(position);
			((TextView) convertView.findViewById(R.id.tvDishListDishType)).setText(dishType.getTypeName());
		}
		else{
			if(convertView == null) {
				convertView = inflater.inflate(R.layout.list_dish, null);
			}
			Dish currentDish = mPosToDish.get(position);

			TextView tvTitle = (TextView) convertView.findViewById(R.id.tvDishListTitle);
			TextView tvDesc = (TextView) convertView.findViewById(R.id.tvDishListDescription);
			TextView tvCount = (TextView) convertView.findViewById(R.id.tvDishListCount);

			tvTitle.setText(currentDish.getTitle() + "\n" + currentDish.getPrice() + " HRK");
			tvDesc.setText(currentDish.getDescription());
			tvCount.setText("0");
		}
		return convertView;
	}



}
