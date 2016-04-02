package com.idurlen.foodordering.controller;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.Dishes;
import com.idurlen.foodordering.database.model.Dish;
import com.idurlen.foodordering.utils.Messenger;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.fragment.ConfirmOrderFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




/**
 *
 * @author Ivan Durlen
 */
public class ConfirmOrderController implements Controller {

	SQLiteDatabase db;

	List<Dish> lSelectedDishes;
	Map<Integer, Integer> mDishQuantities;

	BackgroundTask task;
	ConfirmOrderFragment fragment;


	public ConfirmOrderController(Fragment fragment){
		this.fragment = (ConfirmOrderFragment) fragment;
		lSelectedDishes = new ArrayList<Dish>();
		mDishQuantities = (Map<Integer, Integer>) Messenger.getObject(Messenger.KEY_SELECTED_DISHES_MAP);
	}


	@Override
	public void activate() {
		db = DatabaseManager.getInstance(fragment.getActivity()).getReadableDatabase();

		task = new BackgroundTask(fragment.getProgressBar(), fragment.getLayoutContainer(),
				new BackgroundOperation() {
					@Override
					public Object execInBackground() {
						lSelectedDishes.addAll(Dishes.getDishesById(db, mDishQuantities.keySet()));
						for(Dish dish : lSelectedDishes){
							Log.e("DISH", dish.getTitle() + ": " + mDishQuantities.get(dish.getId()));
						}
						return null;
					}

					@Override
					public void execAfter(Object object) {
						db.close();
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


}
