package com.idurlen.foodordering.database.helper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;




/**
 * @author Ivan Durlen
 */
public class DishTypes {

	public static final String TABLE_NAME = "dish_types";

	public static final String COL_ID = "id";
	public static final String COL_TYPE_NAME = "type_name";

	public static String getCreateTableStatement(){
		return "CREATE TABLE " + TABLE_NAME + "(" +
				COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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

}
