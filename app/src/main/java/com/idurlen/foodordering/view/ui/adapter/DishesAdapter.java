package com.idurlen.foodordering.view.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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
	private Map<Integer, Integer> mDishQuantities;

	LayoutInflater inflater;


	public DishesAdapter(LayoutInflater inflater,  Map<DishType, List<Dish>> mDishesByType){
		this.inflater = inflater;

		mPosToDishType = new HashMap<Integer, DishType>();
		mPosToDish = new HashMap<>();
		mDishQuantities = new HashMap<>();

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
			setDataAndListeners(position, convertView);
		}
		return convertView;
	}



	private void setDataAndListeners(final int position, View convertView){
		Dish currentDish = mPosToDish.get(position);

		ImageButton ibAdd = (ImageButton) convertView.findViewById(R.id.ibAdd);
		ImageButton ibRemove = (ImageButton) convertView.findViewById(R.id.ibRemove);

		TextView tvTitle = (TextView) convertView.findViewById(R.id.tvDishListTitle);
		TextView tvDesc = (TextView) convertView.findViewById(R.id.tvDishListDescription);
		final TextView tvCount = (TextView) convertView.findViewById(R.id.tvDishListCount);

		View.OnClickListener clickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clickHandler(mPosToDish.get(position), (ImageButton) v, tvCount);
			}
		};
		ibAdd.setOnClickListener(clickListener);
		ibRemove.setOnClickListener(clickListener);

		tvTitle.setText(currentDish.getTitle() + "\n" + currentDish.getPrice() + " HRK");
		tvDesc.setText(currentDish.getDescription());
		tvCount.setText(mDishQuantities.containsKey(currentDish.getId()) ?
				Integer.toString(mDishQuantities.get(currentDish.getId())) : "0");
	}



	private void clickHandler(Dish dish, ImageButton ibClicked, TextView tvCount){
		boolean isAdd = (ibClicked.getId() == R.id.ibAdd);

		int iQuantity = mDishQuantities.containsKey(dish.getId()) ? mDishQuantities.get(dish.getId()) : 0;
		iQuantity += (isAdd ? (iQuantity < 30 ? 1 : 0) : (iQuantity > 0 ? -1 : 0));

		if(iQuantity > 0) {
			mDishQuantities.put(dish.getId(), iQuantity);
		}
		else if(mDishQuantities.containsKey(dish.getId())){
			mDishQuantities.remove(dish.getId());
		}

		tvCount.setText(Integer.toString(iQuantity));
	}


	public Map<Integer, Integer> getMDishQuantities(){
		return mDishQuantities;
	}



}
