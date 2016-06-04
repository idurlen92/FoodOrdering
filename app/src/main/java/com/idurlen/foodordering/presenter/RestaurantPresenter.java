package com.idurlen.foodordering.presenter;

import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.DishTypes;
import com.idurlen.foodordering.database.helper.Dishes;
import com.idurlen.foodordering.database.model.Dish;
import com.idurlen.foodordering.database.model.DishType;
import com.idurlen.foodordering.database.model.Restaurant;
import com.idurlen.foodordering.utils.MenuController;
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
 * MVC Presenter component for RestaurantFragment.
 */
public class RestaurantPresenter extends Presenter implements View.OnClickListener {

	private int iNumSelectedDishes = 0;

	private Restaurant selectedRestaurant;

	private List<DishType> lDishTypes;
	private List<Dish> lDishes;
	private Map<Integer, Integer> mDishQuantities = new HashMap<>();

	private DatabaseManager databaseManager;


	public RestaurantPresenter(Fragment fragment){
		super(fragment, "");
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("RestaurantPresenter", "ACTIVATED");
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		databaseManager = DatabaseManager.getInstance(getApplicationContext());

		if(selectedRestaurant == null){
			selectedRestaurant = (Restaurant) Messenger.getObject(Messenger.KEY_RESTAURANT_OBJECT);
		}
		getActivity().getSupportActionBar().setTitle(selectedRestaurant.getName());

		if(!isActivated()) {
			((RestaurantFragment) getFragment()).getBOrder().setEnabled(false);
			getDishes();
		}
		else{
			setListAdapter();
			setListeners();
		}
	}


	@Override
	public void onPause() { }


	@Override
	public void onStop() {
		databaseManager = null;
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		selectedRestaurant = null;
		if(mDishQuantities != null) {
			mDishQuantities.clear();
		}
	}


	@Override
	public void onClick(View v) {
		Messenger.clearAll();
		Messenger.putObject(Messenger.KEY_SELECTED_DISHES_MAP, mDishQuantities);
		Messenger.putObject(Messenger.KEY_RESTAURANT_OBJECT, selectedRestaurant);
		((MainActivity) getActivity()).pushFragment(MenuController.OPTION_CONFIRM_ORDER);
	}


	private void getDishes() {
		RestaurantFragment fragment = (RestaurantFragment) getFragment();

		BackgroundTask task = new BackgroundTask(fragment.getProgressBar(), fragment.getLayoutContainer(), new BackgroundOperation() {
			@Override
			public Object execInBackground() {
				SQLiteDatabase db = databaseManager.getReadableDatabase();
				lDishTypes = new ArrayList(DishTypes.getDishTypesOfRestaurant(db, selectedRestaurant.getId()));
				lDishes = new ArrayList<>(Dishes.getDishesOfRestaurant(db, selectedRestaurant.getId()));
				db.close();

				return null;
			}

			@Override
			public void execAfter(Object object) {
				setListAdapter();
				setListeners();
				setIsActivated(true);
			}
		});

		task.execute();
	}


	private void setListeners() {
		((RestaurantFragment) getFragment()).getBOrder().setOnClickListener(this);
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
		return (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

		((RestaurantFragment) getFragment()).getBOrder().setEnabled(iNumSelectedDishes > 0);
	}



	private void setListAdapter(){
		DishesAdapter adapter = new DishesAdapter(RestaurantPresenter.this,
				(LayoutInflater) getFragment().getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
				arrangeInMap(lDishTypes, lDishes), mDishQuantities);
		((RestaurantFragment) getFragment()).getListView().setAdapter(adapter);
		setListeners();
	}


}
