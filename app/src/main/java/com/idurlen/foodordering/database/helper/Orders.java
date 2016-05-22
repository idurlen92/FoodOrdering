package com.idurlen.foodordering.database.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.idurlen.foodordering.database.model.Order;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class Orders extends HelperMethods{

	public static final String TABLE_NAME = "orders";

	public static final String COL_ID = "id";
	public static final String COL_USER_ID = "user_id";
	public static final String COL_RESTAURANT_ID = "restaurant_id";
	public static final String COL_ORDER_TIME = "order_time";
	public static final String COL_DELIVERY_TIME = "delivery_time";
	public static final String COL_ORDER_CITY = "order_city";
	public static final String COL_ORDER_ADDRESS = "order_address";
	public static final String COL_IS_CANCELED = "is_canceled";


	public static String getCreateTableStatement(){
		return "CREATE TABLE " + TABLE_NAME + "(" +
				COL_ID + " INTEGER PRIMARY KEY, " +
				COL_USER_ID + " INTEGER, " +
				COL_RESTAURANT_ID + " INTEGER REFERENCES " + Restaurants.TABLE_NAME +
					"(" + Restaurants.COL_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
				COL_ORDER_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
				COL_DELIVERY_TIME + " VARCHAR(5) NOT NULL, " +
				COL_ORDER_CITY + " VARCHAR(45), " +
				COL_ORDER_ADDRESS + " VARCHAR(45), " +
				COL_IS_CANCELED + " INTEGER DEFAULT 0, " +
				"UNIQUE(" + COL_USER_ID + "," + COL_RESTAURANT_ID + "," + COL_ORDER_TIME + "))";
	}



	public static List<Order> getOrdersOfUser(SQLiteDatabase db, int userId){
		final String sQuery = "SELECT * FROM " + TABLE_NAME +
								" WHERE " + COL_USER_ID + " = ? " +
								"ORDER BY " + COL_ORDER_TIME + " DESC";

		ArrayList<Order> lOrders = new ArrayList<>();

		Cursor cursor = db.rawQuery(sQuery,  new String[]{ Integer.toString(userId) });
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			Order order = new Order();
			lOrders.add((Order) extractFields(cursor, order));
		}
		cursor.close();

		return lOrders;
	}



	public static void insertOrder(SQLiteDatabase db, Order order){
		ContentValues values = new ContentValues();

		values.put(COL_ID, order.getId());
		values.put(COL_USER_ID, order.getUserId());
		values.put(COL_RESTAURANT_ID, order.getRestaurantId());
		values.put(COL_ORDER_CITY, order.getOrderCity());
		values.put(COL_ORDER_ADDRESS, order.getOrderAddress());
		values.put(COL_ORDER_TIME, order.getOrderTime());
		values.put(COL_DELIVERY_TIME, order.getDeliveryTime());

		db.insert(TABLE_NAME, null, values);
	}


	public static void insertOrders(SQLiteDatabase db, List<Object> lOrders){
		ContentValues values = new ContentValues();

		for(Object obj : lOrders){
			Order order = (Order) obj;

			values.put(COL_ID, order.getId());
			values.put(COL_USER_ID, order.getUserId());
			values.put(COL_RESTAURANT_ID, order.getRestaurantId());
			values.put(COL_IS_CANCELED, order.getIsCanceled());
			values.put(COL_ORDER_CITY, order.getOrderCity());
			values.put(COL_ORDER_ADDRESS, order.getOrderAddress());
			values.put(COL_ORDER_TIME, order.getOrderTime());
			values.put(COL_DELIVERY_TIME, order.getDeliveryTime());

			db.insert(TABLE_NAME, null, values);
			values.clear();
		}
	}


	public static void deleteOrders(SQLiteDatabase db){
		db.delete(TABLE_NAME, "", new String[]{});
	}


}
