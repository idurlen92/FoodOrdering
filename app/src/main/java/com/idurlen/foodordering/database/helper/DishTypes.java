package com.idurlen.foodordering.database.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.idurlen.foodordering.database.model.DishType;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class DishTypes extends HelperMethods{

	public static final String TABLE_NAME = "dish_types";

	public static final String COL_ID = "id";
	public static final String COL_TYPE_NAME = "type_name";

	public static String getCreateTableStatement(){
		return "CREATE TABLE " + TABLE_NAME + "(" +
				COL_ID + " INTEGER PRIMARY KEY, " +
				COL_TYPE_NAME + " VARCHAR(45) NOT NULL )";
	}


	public static void prepareTestData(SQLiteDatabase db){
		String[] astrTypeNames = new String[]{
				"Hladna predjelo", "Topla predjela", "Meksička jela",
				"Jela s roštilja", "Pizze", "Indijska jela", "Vegetarijanska jela",
				"Prilozi", "Deserti" };

		db.beginTransactionNonExclusive();
		ContentValues values = new ContentValues();
		for(int i = 0; i < astrTypeNames.length; i++) {
			values.clear();
			values.put(COL_ID, i + 1);
			values.put(COL_TYPE_NAME, astrTypeNames[i]);
			db.insert(TABLE_NAME, null, values);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}


	public static List<DishType> getDishTypesOfRestaurant(SQLiteDatabase db, int restaurantId){
		final String strStatement = "SELECT * FROM " + TABLE_NAME +
				" WHERE " + COL_ID + " IN " +
					"(SELECT DISTINCT " + Dishes.COL_DISH_TYPE + " FROM " + Dishes.TABLE_NAME +
					" WHERE " + Dishes.COL_RESTAURANT_ID + " = ?)" +
				" ORDER BY " + COL_TYPE_NAME;

		ArrayList<DishType> lDishTypes = new ArrayList<DishType>();
		Cursor cursor = db.rawQuery(strStatement,  new String[]{Integer.toString(restaurantId)} );

		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			DishType type = new DishType();
			lDishTypes.add((DishType) extractFields(cursor, type));
		}

		cursor.close();
		return lDishTypes;
	}



}
