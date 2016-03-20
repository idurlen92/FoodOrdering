package com.idurlen.foodordering.database.helper;

/**
 * @author Ivan Durlen
 */
public class OrderItems {

	public static final String TABLE_NAME = "order_items";

	public static final String COL_ORDER_ID = "order_id";
	public static final String COL_MENU_ID = "menu_id";
	public static final String COL_QUANTITY = "quantity";


	public static String getCreateTableStatement(){
		return "CREATE TABLE " + TABLE_NAME + "(" +
				COL_ORDER_ID + " INTEGER REFERENCES " + Orders.TABLE_NAME +
					"(" + Orders.COL_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
				COL_MENU_ID + " INTEGER REFERENCES " + Dishes.TABLE_NAME +
					"(" + Dishes.COL_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
				COL_QUANTITY + " INTEGER DEFAULT 1)";
	}
}
