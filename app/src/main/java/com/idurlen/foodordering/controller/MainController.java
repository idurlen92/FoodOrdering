package com.idurlen.foodordering.controller;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.DishTypes;
import com.idurlen.foodordering.database.helper.Dishes;
import com.idurlen.foodordering.database.helper.Restaurants;
import com.idurlen.foodordering.database.model.OrderItem;
import com.idurlen.foodordering.net.DishTypesRequest;
import com.idurlen.foodordering.net.DishesRequest;
import com.idurlen.foodordering.net.RestaurantsRequest;
import com.idurlen.foodordering.utils.AppSettings;
import com.idurlen.foodordering.utils.DateTimeUtils;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.utils.async.DownloadThread;
import com.idurlen.foodordering.view.MainActivity;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class MainController  implements Controller{

	List<Object> lRestaurants;
	List<Object> lDishTypes;
	List<Object> lDishes;

	AppSettings settings;
	BackgroundTask downloadTask;
	DatabaseManager databaseManager;

	MainActivity activity;


	public MainController(AppCompatActivity activity) {
		this.activity = (MainActivity) activity;

		settings = AppSettings.getInstance(activity);
		databaseManager = DatabaseManager.getInstance(activity);

		lRestaurants = new ArrayList<>();
		lDishTypes = new ArrayList<>();
		lDishes = new ArrayList<>();
	}



	@Override
	public void activate() {
		if(!settings.isSchemaCreated()) {
			performDownload();
		}
		else{
			activity.pushFragment(MenuController.OPTION_HOME);
		}
	}


	@Override
	public void setListeners() { /* ----- Nothing to do ----- */}

	@Override
	public void onClick(View v) { /* ----- Nothing to do ----- */}


	private void performDownload(){
		downloadTask = new BackgroundTask(activity, "Preuzimanje podataka", new BackgroundOperation() {
			@Override
			public Object execInBackground() {
				SQLiteDatabase db = databaseManager.getWritableDatabase();

				boolean isErrorOccurred = false;
				try {
					// ----- 1) Download data -----
					List<DownloadThread> lThreads = new ArrayList<>();
					lThreads.add(new DownloadThread(activity, new RestaurantsRequest(), lRestaurants));
					lThreads.add(new DownloadThread(activity, new DishTypesRequest(), lDishTypes));
					lThreads.add(new DownloadThread(activity, new DishesRequest(), lDishes));

					for(DownloadThread thread : lThreads){
						thread.start();
					}
					for(DownloadThread thread : lThreads) {
						thread.join();
					}

					isErrorOccurred = getIsErrorOccurred(lThreads);
					lThreads.clear();

					// ----- 2) Insert data into Database -----
					if(!isErrorOccurred) {
						db.beginTransactionNonExclusive();
						performDatabaseInsert(db);
						db.setTransactionSuccessful();
						settings.setLastSyncTime(DateTimeUtils.getCurrentTimeStampString());
						settings.setIsSchemaCreated(true);
					}
				}
				catch(InterruptedException e){
					e.printStackTrace();
					Log.d("THREAD", "Interuppted");
				}
				finally{
					if(!isErrorOccurred) {
						db.endTransaction();
					}
					db.close();
				}

				return isErrorOccurred;
			}

			@Override
			public void execAfter(Object object) {
				super.execAfter(object);

				boolean isErrorOccurred = (boolean) object;
				if(!isErrorOccurred) {
					activity.pushFragment(MenuController.OPTION_HOME);
				}
				else{
						Toast.makeText(activity, "Greška u preuzimanju podataka", Toast.LENGTH_SHORT).show();
				}
			}
		});

		downloadTask.execute();
	}



	private boolean getIsErrorOccurred(List<DownloadThread> lDownloadThreads){
		for(DownloadThread thr : lDownloadThreads){
			if(thr.isErrorOccurred()){
				return true;
			}
		}
		return false;
	}


	private boolean performDatabaseInsert(SQLiteDatabase db){
		Restaurants.insertRestaurants(db, lRestaurants);
		DishTypes.insertDishTypes(db, lDishTypes);
		Dishes.insertDishes(db, lDishes);
		return true;
	}

}
