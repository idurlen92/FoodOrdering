package com.idurlen.foodordering.presenter;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.idurlen.foodordering.utils.MenuController;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.utils.async.DownloadThread;
import com.idurlen.foodordering.view.MainActivity;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class MainPresenter extends Presenter {

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


	public MainPresenter(AppCompatActivity activity) {
		super(activity);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		isErrorOccurred = false;
	}


	@Override
	public void onStart() {
		super.onStart();
		settings = AppSettings.getInstance(getApplicationContext());
		session = SessionManager.getInstance(getApplicationContext());
		databaseManager = DatabaseManager.getInstance(getApplicationContext());

		checkForDownload();
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) { }


	@Override
	public void onPause() { }


	@Override
	public void onStop() {
		progressDialog = null;
		settings = null;
		session = null;
		databaseManager = null;
	}


	@Override
	public void onDestroy() { }


	public void checkForDownload() {
		if(settings.isAutoSync() && settings.isSyncRequired()) {
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setTitle("Preuzimanje podataka");
			progressDialog.setIndeterminate(true);
			progressDialog.show();

			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					performDownload();
				}
			});
			thread.start();
		}
		else{
			((MainActivity) getActivity()).pushFragment(MenuController.OPTION_HOME, true);
		}
	}


	private void performDownload(){
		lRestaurants = new ArrayList<>();
		lDishTypes = new ArrayList<>();
		lDishes = new ArrayList<>();
		lOrders = new ArrayList<>();
		lOrderItems = new ArrayList<>();

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

			lThreads.clear();

			// ----- 2) Insert data into Database -----
			performDatabaseInsert(databaseManager.getWritableDatabase());
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					progressDialog.dismiss();
					((MainActivity) getActivity()).pushFragment(MenuController.OPTION_HOME, true);
				}
			});

		}
		catch(InterruptedException e){
			e.printStackTrace();
			Log.d("THREAD", "Interuppted");

			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getActivity(), "Greška u preuzimanju podataka", Toast.LENGTH_SHORT).show();
				}
			});
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
			lThreads.add(new DownloadThread(getActivity(), this, new RestaurantsRequest(), lRestaurants));
			lThreads.add(new DownloadThread(getActivity(), this, new DishTypesRequest(), lDishTypes));
			lThreads.add(new DownloadThread(getActivity(), this, new DishesRequest(), lDishes));
		}
		if(settings.isUserChanged()){
			lThreads.add(new DownloadThread(getActivity(), this, new OrdersRequest(), lOrders));
			lThreads.add(new DownloadThread(getActivity(), this, new OrderItemsRequest(), lOrderItems));
		}

		return lThreads;
	}



	/**
	 * Performs database insert.
	 * @param db
	 * @return
	 */
	private void performDatabaseInsert(SQLiteDatabase db){
		db.beginTransactionNonExclusive();

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

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}

}
