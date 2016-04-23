package com.idurlen.foodordering.database.helper;

import com.idurlen.foodordering.database.model.OrderItem;




/**
 * @author Ivan Durlen
 */
public class OrderItems extends HelperMethods{

	public static final String TABLE_NAME = "order_items";

	public static final String COL_ORDER_ID = "order_id";
	public static final String COL_DISH_ID = "dish_id";
	public static final String COL_QUANTITY = "quantity";


	public static String getCreateTableStatement(){
		return "CREATE TABLE " + TABLE_NAME + "(" +
				COL_ORDER_ID + " INTEGER REFERENCES " + Orders.TABLE_NAME +
					"(" + Orders.COL_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
				COL_DISH_ID + " INTEGER REFERENCES " + Dishes.TABLE_NAME +
					"(" + Dishes.COL_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
				COL_QUANTITY + " INTEGER DEFAULT 1)";
	}


	public static boolean insertOrderItem(OrderItem orderItem){
		//TODO
		return false;
	}
}
