package com.idurlen.foodordering.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.idurlen.foodordering.database.helper.DishTypes;
import com.idurlen.foodordering.database.helper.Dishes;
import com.idurlen.foodordering.database.helper.OrderItems;
import com.idurlen.foodordering.database.helper.Orders;
import com.idurlen.foodordering.database.helper.Restaurants;
import com.idurlen.foodordering.database.helper.Users;




public class DatabaseManager extends SQLiteOpenHelper {

	private static final String DB_NAME = "orders.db";
	private static final int DB_VERSION = 1;

	private static DatabaseManager instance = null;

	private DatabaseManager(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Users.getCreateTableStatement());
		db.execSQL(Restaurants.getCreateTableStatement());
		db.execSQL(DishTypes.getCreateTableStatement());
		db.execSQL(Dishes.getCreateTableStatement());
		db.execSQL(Orders.getCreateTableStatement());
		db.execSQL(OrderItems.getCreateTableStatement());
		Restaurants.prepareTestData(db);
		DishTypes.prepareTestData(db);
		Dishes.prepareTestData(db);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String[] tableNames = new String[]{
				Users.TABLE_NAME, Restaurants.TABLE_NAME, Dishes.TABLE_NAME, Orders.TABLE_NAME, OrderItems.TABLE_NAME  };//TODO: food types table
		for(String tableName : tableNames){
			db.execSQL("DROP TABLE IF EXISTS " + tableName);
		}
		onCreate(db);
	}


	public static synchronized DatabaseManager getInstance(Context context){
		if(instance == null)
			instance = new DatabaseManager(context);
		return instance;
	}



}
