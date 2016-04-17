package com.idurlen.foodordering.database.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.idurlen.foodordering.database.model.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;




/**
 * @author Ivan Durlen
 */
public class Dishes extends HelperMethods{

	public static final String TABLE_NAME = "dishes";

	public static final String COL_ID = "id";
	public static final String COL_DISH_TYPE = "dish_type_id";
	public static final String COL_RESTAURANT_ID = "restaurant_id";
	public static final String COL_TITLE = "title";
	public static final String COL_PRICE = "price";
	public static final String COL_DESCRIPTION = "description";


	public static String getCreateTableStatement(){
		return "CREATE TABLE " + TABLE_NAME + "(" +
				COL_ID + " INTEGER PRIMARY KEY, " +
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

		int k=1;
		for(int i = 1; i < 41; i++) {
			for(int j = 1; j < astrTitles.length; j++) {
				values.clear();
				values.put(COL_ID, k++);
				values.put(COL_TITLE, astrTitles[j % astrTitles.length]);
				values.put(COL_DISH_TYPE, (j % 8) + 1);
				values.put(COL_PRICE, aintPrices[j % 7]);
				values.put(COL_RESTAURANT_ID, i);
				values.put(COL_DESCRIPTION, "Jako fino i pikantno jelo " + j);
				db.insert(TABLE_NAME, null, values);
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}




	/**
	 *
	 * @param db
	 * @param restaurantId
	 * @return
	 */
	public static List<Dish> getDishesOfRestaurant(SQLiteDatabase db, int restaurantId){
		final String strStatement = "SELECT * FROM " + TABLE_NAME +
				" WHERE " + COL_RESTAURANT_ID + " = ? " +
				" ORDER BY " + COL_DISH_TYPE + ", " + COL_TITLE;

		ArrayList<Dish> lDishes = new ArrayList<Dish>();
		Cursor cursor = db.rawQuery(strStatement,  new String[]{Integer.toString(restaurantId)} );

		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			Dish dish = new Dish();
			lDishes.add((Dish) extractFields(cursor, dish));
		}

		cursor.close();
		return lDishes;
	}




	/**
	 *
	 * @param db
	 * @param sDishIds
	 * @return
	 */
	public static List<Dish> getDishesById(SQLiteDatabase db, Set<Integer> sDishIds){
		final String strStatement = "SELECT * FROM " + TABLE_NAME +
				" WHERE " + COL_ID + " IN(" +  getInStatementQueryParams(sDishIds) + ")" +
				" ORDER BY " + COL_DISH_TYPE + ", " + COL_TITLE;


		Cursor cursor = db.rawQuery(strStatement, getSetAsStringArray(sDishIds));
		ArrayList<Dish> lDishes = new ArrayList<Dish>();

		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			Dish dish = new Dish();
			lDishes.add((Dish) extractFields(cursor, dish));
		}

		cursor.close();
		return lDishes;
	}




}
