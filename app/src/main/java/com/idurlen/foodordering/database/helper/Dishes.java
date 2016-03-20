package com.idurlen.foodordering.database.helper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;




/**
 * @author Ivan Durlen
 */
public class Dishes {

	public static final String TABLE_NAME = "dishes";

	public static final String COL_ID = "id";
	public static final String COL_DISH_TYPE = "dish_type";
	public static final String COL_RESTAURANT_ID = "restaurant_id";
	public static final String COL_TITLE = "title";
	public static final String COL_PRICE = "price";
	public static final String COL_DESCRIPTION = "description";


	public static String getCreateTableStatement(){
		//TODO: categories
		return "CREATE TABLE " + TABLE_NAME + "(" +
				COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				COL_DISH_TYPE + " INTEGER REFERENCES " + DishTypes.TABLE_NAME +
					"(" + DishTypes.COL_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
				COL_RESTAURANT_ID + " INTEGER REFERENCES " + Restaurants.TABLE_NAME +
					"(" + Restaurants.COL_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
				COL_TITLE + " VARCHAR(30) NOT NULL, " +
				COL_PRICE + " REAL NOT NULL, "  +
				COL_DESCRIPTION + " TEXT)";

	}


	public static void prepareTestData(SQLiteDatabase db){
		String[] astrTitles = new String[]{
				"Tostada de Pollo", "Pileća juha", "Žabe u pikantnom umaku",
				"Lignje na žaru", "Chimichunga de Pollo", "Purica i mlinci",
				"Pečena patka", "Jegulje", "Crni rižoto", "Specijalitet",
				"Tuna salata", "Piletina natur", "Đevrek s kajmakom",
				"Vješalica sa sirom"
		};
		Integer[] aintPrices = new Integer[]{25, 35, 42, 68, 80, 93, 57};

		db.beginTransactionNonExclusive();
		ContentValues values = new ContentValues();

		for(int i = 1; i < 41; i++) {
			for(int j = 1; j < 16; j++) {
				values.clear();
				values.put(COL_TITLE, astrTitles[j % astrTitles.length]);
				values.put(COL_DISH_TYPE, (j % 6) + 1);
				values.put(COL_PRICE, aintPrices[j % 7]);
				values.put(COL_DESCRIPTION, "Jako fino i pikantno jelo " + j);
				db.insert(TABLE_NAME, null, values);
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}


}
