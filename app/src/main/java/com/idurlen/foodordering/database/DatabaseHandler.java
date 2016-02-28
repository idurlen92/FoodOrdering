package com.idurlen.foodordering.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class DatabaseHandler extends SQLiteOpenHelper {

	private static final String DB_NAME = "orders.db";
	private static final int DB_VERSION = 1;

	private static DatabaseHandler instance = null;


	private DatabaseHandler(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SchemaDefinition.createTableUsers);
		db.execSQL(SchemaDefinition.createTableRestaurants);
		db.execSQL(SchemaDefinition.createTableMenus);
		db.execSQL(SchemaDefinition.createTableOrders);
		db.execSQL(SchemaDefinition.createTableOrderItems);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for(String table : SchemaDefinition.getTableNames()) {
			db.execSQL("DROP TABLE IF EXISTS " + table);
		}
		onCreate(db);
	}


	public static DatabaseHandler getInsance(Context context){
		if(instance == null)
			instance = new DatabaseHandler(context);
		return instance;
	}



}
