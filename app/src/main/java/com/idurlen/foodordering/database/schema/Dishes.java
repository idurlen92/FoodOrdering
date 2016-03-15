package com.idurlen.foodordering.database.schema;

/**
 * @author Ivan Durlen
 */
public class Dishes {

	public static final String TABLE_NAME = "dishes";

	public static final String COL_ID = "id";
	public static final String COL_RESTAURANT_ID = "restaurant_id";
	public static final String COL_TITLE = "title";
	public static final String COL_PRICE = "price";
	public static final String COL_DESCRIPTION = "description";


	public static String getCreateTableStatement(){
		//TODO: categories
		return "CREATE TABLE " + TABLE_NAME + "(" +
				COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				COL_RESTAURANT_ID + " INTEGER REFERENCES " + Restaurants.TABLE_NAME +
					"(" + Restaurants.COL_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
				COL_TITLE + " VARCHAR(30) NOT NULL, " +
				COL_PRICE + " REAL NOT NULL, "  +
				COL_DESCRIPTION + " TEXT)";

	}


}
