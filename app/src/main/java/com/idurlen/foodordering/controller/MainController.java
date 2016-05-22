package com.idurlen.foodordering.controller;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.idurlen.foodordering.database.DatabaseManager;
import com.idurlen.foodordering.database.helper.DishTypes;
import com.idurlen.foodordering.database.helper.Dishes;
import com.idurlen.foodordering.database.helper.OrderItems;
import com.idurlen.foodordering.database.helper.Orders;
import com.idurlen.foodordering.database.helper.Restaurants;
import com.idurlen.foodordering.net.DishTypesRequest;
import com.idurlen.foodordering.net.DishesRequest;
import com.idurlen.foodordering.net.OrderItemsRequest;
import com.idurlen.foodordering.net.OrdersRequest;
import com.idurlen.foodordering.net.RestaurantsRequest;
import com.idurlen.foodordering.utils.AppSettings;
import com.idurlen.foodordering.utils.DateTimeUtils;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.utils.async.DownloadThread;
import com.idurlen.foodordering.view.MainActivity;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class MainController  implements Controller{

	boolean isErrorOccurred = false;

	List<DownloadThread> lThreads;

	List<Object> lRestaurants;
	List<Object> lDishTypes;
	List<Object> lDishes;
	List<Object> lOrders;
	List<Object> lOrderItems;

	ProgressDialog progressDialog;

	AppSettings settings;
	SessionManager session;
	DatabaseManager databaseManager;

	MainActivity activity;


	public MainController(AppCompatActivity activity) {
		this.activity = (MainActivity) activity;

		settings = AppSettings.getInstance(activity);
		session = SessionManager.getInstance(activity);
		databaseManager = DatabaseManager.getInstance(activity);
	}



	@Override
	public void activate() {
		if(settings.isAutoSync() && settings.isSyncRequired()) {
			progressDialog = new ProgressDialog(activity);
			progressDialog.setTitle("Preuzimanje podataka");
			progressDialog.setIndeterminate(true);
			progressDialog.show();

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
		lRestaurants = new ArrayList<>();
		lDishTypes = new ArrayList<>();
		lDishes = new ArrayList<>();
		lOrders = new ArrayList<>();
		lOrderItems = new ArrayList<>();

		SQLiteDatabase db = databaseManager.getWritableDatabase();
		isErrorOccurred = false;

		try {
			// ----- 1) Download data -----
		    lThreads = createThreads();

			for(Thread thread : lThreads){
				thread.start();
			}
			for(Thread thread : lThreads) {
				thread.join();
			}

			// ----- 2) Insert data into Database -----
			db.beginTransactionNonExclusive();
			performDatabaseInsert(db);
			db.setTransactionSuccessful();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Log.d("THREAD", "Interuppted");
		}
		finally{
			progressDialog.dismiss();
			lThreads.clear();

			if(!isErrorOccurred) {
				db.endTransaction();
				activity.pushFragment(MenuController.OPTION_HOME);
			}
			else{
				Toast.makeText(activity, "Greška u preuzimanju podataka", Toast.LENGTH_SHORT).show();
			}
			db.close();
		}
	}




	/**
	 * Called from the thread to stop other threads if error occurred.
	 */
	public synchronized void stopAllThreads(Thread t){
		for(Thread thr : lThreads) {
			if(thr.hashCode() != t.hashCode()) {
				thr.interrupt();
			}
		}
	}



	private List<DownloadThread> createThreads(){
		List<DownloadThread> lThreads = new ArrayList<>();

		if(!settings.isMainDataSynced()) {
			lThreads.add(new DownloadThread(activity, this, new RestaurantsRequest(), lRestaurants));
			lThreads.add(new DownloadThread(activity, this, new DishTypesRequest(), lDishTypes));
			lThreads.add(new DownloadThread(activity, this, new DishesRequest(), lDishes));
		}
		if(settings.isUserChanged()){
			lThreads.add(new DownloadThread(activity, this, new OrdersRequest(), lOrders));
			lThreads.add(new DownloadThread(activity, this, new OrderItemsRequest(), lOrderItems));
		}

		return lThreads;
	}



	/**
	 * Performs database insert.
	 * @param db
	 * @return
	 */
	private void performDatabaseInsert(SQLiteDatabase db){
		settings.setLastSyncTime(DateTimeUtils.getCurrentTimeStampString());

		if(!settings.isMainDataSynced()) {
			Restaurants.insertRestaurants(db, lRestaurants);
			DishTypes.insertDishTypes(db, lDishTypes);
			Dishes.insertDishes(db, lDishes);

			settings.setIsMainDataSynced(true);
		}
		if(settings.isUserChanged()){
			Orders.deleteOrders(db);
			Orders.insertOrders(db, lOrders);
			OrderItems.insertOrderItems(db, lOrderItems);

			settings.setIsUserChanged(false);
		}
	}

}
