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
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.fragment.RestaurantFragment;
import com.idurlen.foodordering.view.ui.adapter.DishesAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * MVC Controller component for RestaurantFragment.
 */
public class RestaurantController implements Controller{

	int restaurantId;

	DishesAdapter adapter;

	DatabaseManager databaseManager;
	SQLiteDatabase db;
	RestaurantFragment fragment;

	public RestaurantController(Fragment fragment){
		this.fragment = (RestaurantFragment) fragment;
		restaurantId = Messenger.getBundle().getInt(Messenger.KEY_RESTAURANT_ID);
		Log.d("RESTAURANT ID", Integer.toString(restaurantId));
	}


	@Override
	public void activate() {
		databaseManager = DatabaseManager.getInstance(fragment.getActivity());
		db = databaseManager.getReadableDatabase();

		BackgroundTask task = new BackgroundTask(fragment.getPbRestaurant(), fragment.getLayoutRestaurant(), new BackgroundOperation() {
			@Override
			public Object execInBackground() {
				List<DishType> lDishTypes = DishTypes.getDishTypesOfRestaurant(db, restaurantId);
				List<Dish> lDishes = Dishes.getDishesOfRestaurant(db, restaurantId);
				return arrangeInMap(lDishTypes, lDishes);
			}

			@Override
			public void execAfter(Object object) {
				db.close();
				adapter = new DishesAdapter((LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
						(Map<DishType, List<Dish>>) object);
				fragment.getLvMeals().setAdapter(adapter);
			}
		});

		task.execute();
	}




	@Override
	public void setListeners() {

	}




	@Override
	public void onClick(View v) {
	}


	private Map<DishType, List<Dish>> arrangeInMap(List<DishType> lDishTypes, List<Dish> lDishes){
		Map<DishType, List<Dish>> mDishesByType = new HashMap<DishType, List<Dish>>();
		Map<Integer, DishType> mIdToDishType = new HashMap<Integer, DishType>();

		for(DishType dishType : lDishTypes){
			mIdToDishType.put(dishType.getId(), dishType);
			mDishesByType.put(dishType, new ArrayList<Dish>());
		}
		Log.d("DISH TYPES", Integer.toString(mDishesByType.keySet().size()));

		for(Dish dish : lDishes){
			mDishesByType.get(mIdToDishType.get(dish.getDishType())).add(dish);
		}
		Log.d("DISHES", Integer.toString(lDishes.size()));

		return mDishesByType;
	}


}
