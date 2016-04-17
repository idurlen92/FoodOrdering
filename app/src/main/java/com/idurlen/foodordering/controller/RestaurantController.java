package com.idurlen.foodordering.controller;

import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.DishTypes;
import com.idurlen.foodordering.database.helper.Dishes;
import com.idurlen.foodordering.database.model.Dish;
import com.idurlen.foodordering.database.model.DishType;
import com.idurlen.foodordering.database.model.Restaurant;
import com.idurlen.foodordering.utils.Messenger;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.MainActivity;
import com.idurlen.foodordering.view.fragment.RestaurantFragment;
import com.idurlen.foodordering.view.ui.adapter.DishesAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;




/**
 * MVC Controller component for RestaurantFragment.
 */
public class RestaurantController implements Controller{

	private int iNumSelectedDishes = 0;

	private Map<Integer, Integer> mDishQuantities;
	private Restaurant restaurant;

	LayoutInflater inflater;

	DishesAdapter adapter;
	SQLiteDatabase db;

	MainActivity activity;
	RestaurantFragment fragment;

	public RestaurantController(Fragment fragment){
		this.fragment = (RestaurantFragment) fragment;
		this.activity = (MainActivity) fragment.getActivity();
		restaurant = (Restaurant) Messenger.getObject(Messenger.KEY_RESTAURANT_OBJECT);

		mDishQuantities = new HashMap<>();
		Log.d("RESTAURANT ID", Integer.toString(restaurant.getId()));
	}


	@Override
	public void activate() {
		activity.getSupportActionBar().setTitle(restaurant.getName());
		db = DatabaseManager.getInstance(fragment.getActivity()).getReadableDatabase();

		BackgroundTask task = new BackgroundTask(fragment.getProgressBar(), fragment.getLayoutContainer(), new BackgroundOperation() {
			@Override
			public Object execInBackground() {
				List<DishType> lDishTypes = new ArrayList(DishTypes.getDishTypesOfRestaurant(db, restaurant.getId()));
				List<Dish> lDishes = new ArrayList<>(Dishes.getDishesOfRestaurant(db, restaurant.getId()));
				return arrangeInMap(lDishTypes, lDishes);
			}

			@Override
			public void execAfter(Object object) {
				db.close();
				adapter = new DishesAdapter(RestaurantController.this,
						(LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
						(Map<DishType, List<Dish>>) object, mDishQuantities);
				setListeners();
			}
		});

		task.execute();
	}


	@Override
	public void setListeners() {
		fragment.getBOrder().setOnClickListener(this);
		fragment.getListView().setAdapter(adapter);
	}


	@Override
	public void onClick(View v) {
		// clicking confirm button - put info and create fragment
		Messenger.clearAll();
		Messenger.putObject(Messenger.KEY_SELECTED_DISHES_MAP, mDishQuantities);
		Messenger.putObject(Messenger.KEY_RESTAURANT_OBJECT, restaurant);
		((MainActivity) fragment.getActivity()).pushFragment(MenuController.OPTION_CONFIRM_ORDER);
	}



	/**
	 * Arranges Dishes in a Map according to relevent DishType.
	 * @param lDishTypes
	 * @param lDishes
	 * @return
	 */
	private Map<DishType, List<Dish>> arrangeInMap(List<DishType> lDishTypes, List<Dish> lDishes){
		Map<DishType, List<Dish>> mDishesByType = new LinkedHashMap<>();
		Map<Integer, DishType> mIdToDishType = new LinkedHashMap<>();

		for(DishType dishType : lDishTypes){
			mIdToDishType.put(dishType.getId(), dishType);
			mDishesByType.put(dishType, new ArrayList<Dish>());
		}

		for(Dish dish : lDishes){
			mDishesByType.get(mIdToDishType.get(dish.getDishTypeId())).add(dish);
		}

		return mDishesByType;
	}


	public LayoutInflater getLayoutInflater(){
		if(inflater == null){
			inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		return inflater;
	}



	/**
    *  Used in {@link DishesAdapter} to handle add/substract buttons of list item.
	* @param dish
	* @param isAdd
	*/
	public void listItemButtonClick(Dish dish, boolean isAdd){
		int iQuantity = mDishQuantities.containsKey(dish.getId()) ? mDishQuantities.get(dish.getId()) : 0;
		if(isAdd && iNumSelectedDishes < 30){
			iQuantity++;
			iNumSelectedDishes++;
		}
		else if(!isAdd && iNumSelectedDishes > 0 && iQuantity > 0){
			iQuantity--;
			iNumSelectedDishes--;
		}

		if(iQuantity > 0) {
			mDishQuantities.put(dish.getId(), iQuantity);
		}
		else if(mDishQuantities.containsKey(dish.getId())){
			mDishQuantities.remove(dish.getId());
		}

		fragment.getBOrder().setEnabled(iNumSelectedDishes > 0);
	}


}
