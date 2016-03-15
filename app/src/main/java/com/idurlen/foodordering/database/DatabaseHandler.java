package com.idurlen.foodordering.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.idurlen.foodordering.database.schema.Dishes;
import com.idurlen.foodordering.database.schema.OrderItems;
import com.idurlen.foodordering.database.schema.Orders;
import com.idurlen.foodordering.database.schema.Restaurants;
import com.idurlen.foodordering.database.schema.Users;




public class DatabaseHandler extends SQLiteOpenHelper {

	private static final String DB_NAME = "orders.db";
	private static final int DB_VERSION = 1;

	private static DatabaseHandler instance = null;

	private DatabaseHandler(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Users.getCreateTableStatement());
		db.execSQL(Restaurants.getCreateTableStatement());
		db.execSQL(Dishes.getCreateTableStatement());
		db.execSQL(Orders.getCreateTableStatement());
		//TODO: food types table
		db.execSQL(OrderItems.getCreateTableStatement());
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


	public static synchronized DatabaseHandler getInstance(Context context){
		if(instance == null)
			instance = new DatabaseHandler(context);
		return instance;
	}



}
