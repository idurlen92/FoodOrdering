package com.idurlen.foodordering.database.helper;

import com.idurlen.foodordering.database.model.Order;




/**
 * @author Ivan Durlen
 */
public class Orders {

	public static final String TABLE_NAME = "orders";

	public static final String COL_ID = "id";
	public static final String COL_USER_ID = "user_id";
	public static final String COL_RESTAURANT_ID = "restaurant_id";
	public static final String COL_ORDER_TIME = "order_time";
	public static final String COL_DELIVERY_TIME = "delivery_time";
	public static final String COL_ORDER_CITY = "order_city";
	public static final String COL_ORDER_ADDRESS = "order_address";
	public static final String COL_CANCELED = "is_canceled";


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
				COL_CANCELED + " INTEGER DEFAULT 0, " +
				"UNIQUE(" + COL_USER_ID + "," + COL_RESTAURANT_ID + "," + COL_ORDER_TIME + "))";
	}



	public static boolean insertOrder(Order order){
		//TODO:
		return false;
	}


}
