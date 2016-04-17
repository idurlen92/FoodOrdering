package com.idurlen.foodordering.database.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.idurlen.foodordering.database.model.Restaurant;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class Restaurants extends HelperMethods{

	public static final String TABLE_NAME = "restaurants";

	public static final String COL_ID = "id";
	public static final String COL_NAME = "name";
	public static final String COL_CITY = "city";
	public static final String COL_ADDRESS = "address";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_EMAIL = "email";
	public static final String COL_PHONE = "phone";
	public static final String COL_WORKS_FROM = "working_from";
	public static final String COL_WORKS_TO = "working_until";
	public static final String COL_ORDERS_FROM = "ordering_from";
	public static final String COL_ORDERS_TO = "ordering_until";


	public static String getCreateTableStatement(){
		return "CREATE TABLE " + TABLE_NAME +"(" +
				COL_ID + " INTEGER PRIMARY KEY, " +
				COL_NAME + " VARCHAR(30) NOT NULL, " +
				COL_CITY + " VARCHAR(45) NOT NULL, " +
				COL_ADDRESS + " VARCHAR(45) NOT NULL, " +
				COL_DESCRIPTION + " TEXT, " +
				COL_EMAIL + " VARCHAR(45), " +
				COL_PHONE + " VARCHAR(15), " +
				COL_WORKS_FROM + " VARCHAR(5), " +
				COL_WORKS_TO + " VARCHAR(5), " +
				COL_ORDERS_FROM + " VARCHAR(5), " +
				COL_ORDERS_TO + " VARCHAR(5) )";
	}



	public static void prepareTestData(SQLiteDatabase db){
		String[] astrCities = new String[]{"Zagreb", "Zaprešić", "Varaždin", "Sisak"};
		String[] astrWorksOrdersFrom = new String[]{"08:00", "09:00", "12:30"};
		String[] astrOrdersTo = new String[]{"11:00", "19:00", "20:00", "22:00", "23:00"};
		String[] astrWorksTo = new String[]{"12:00", "20:00", "21:00","23:00", "23:59"};

		db.beginTransactionNonExclusive();
		ContentValues values = new ContentValues();
		for(int i = 1; i < 41; i++){
			values.clear();

			values.put(COL_ID, i);
			values.put(COL_NAME, "Restoran " + i );
			values.put(COL_CITY, astrCities[i % 4]);
			values.put(COL_ADDRESS, "Adresa " + (34 + i) + " " + astrCities[i % 4] );
			values.put(COL_EMAIL, "restoran" + i + "@example.com");
			values.put(COL_DESCRIPTION, "Restoran " + i + " radi vrhunska jela od domaćih sastojaka.");
			values.put(COL_ORDERS_FROM, astrWorksOrdersFrom[i % 3]);
			values.put(COL_WORKS_FROM, astrWorksOrdersFrom[i % 3]);
			values.put(COL_WORKS_TO, astrWorksTo[i % 5]);
			values.put(COL_ORDERS_TO, astrOrdersTo[i % 5]);
			values.put(COL_PHONE, "+3851 2345 678");

			db.insert(TABLE_NAME, null, values);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}



	public static List<Restaurant> getRetaurantsByCity(SQLiteDatabase db, String city){
		final String sQuery = "SELECT * FROM " + TABLE_NAME +
				" WHERE " + COL_CITY + " LIKE ? " +
				" ORDER BY " + COL_NAME;

		ArrayList<Restaurant> lRestaurants = new ArrayList<Restaurant>();
		Cursor cursor = db.rawQuery(sQuery,  new String[]{"%" + city + "%"});

		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			Restaurant restaurant = new Restaurant();
			lRestaurants.add((Restaurant) extractFields(cursor, restaurant));
		}

		cursor.close();
		return lRestaurants;
	}



	public static Restaurant getRestaurantById(SQLiteDatabase db, int id){
		final String sQuery = "SELECT * FROM " + TABLE_NAME +
				"WHERE " + COL_ID + " = ?";

		Cursor cursor = db.rawQuery(sQuery,  new String[]{Integer.toString(id)});
		cursor.moveToFirst();

		Restaurant restaurant = (Restaurant) extractFields(cursor, new Restaurant());

		cursor.close();
		return restaurant;
	}

}
