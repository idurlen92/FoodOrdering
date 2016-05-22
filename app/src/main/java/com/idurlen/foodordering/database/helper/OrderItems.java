package com.idurlen.foodordering.database.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.idurlen.foodordering.database.model.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;




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


	public static List<OrderItem> getOrderItemsOfUser(SQLiteDatabase db, Set<Integer> sOrderIds){
		final String sQuery = "SELECT * FROM " + TABLE_NAME +
								" WHERE " + COL_ORDER_ID + " IN(" +  getInStatementQueryParams(sOrderIds) + ")" +
								" ORDER BY " + COL_ORDER_ID;

		ArrayList<OrderItem> lOrderItems = new ArrayList<>();

		Cursor cursor = db.rawQuery(sQuery, getSetAsStringArray(sOrderIds));
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			lOrderItems.add((OrderItem) extractFields(cursor, new OrderItem()));
		}
		cursor.close();

		return lOrderItems;
	}


	public static void insertOrderItem(SQLiteDatabase db, OrderItem orderItem){
		ContentValues values = new ContentValues();
		values.put(COL_ORDER_ID, orderItem.getOrderId());
		values.put(COL_DISH_ID, orderItem.getDishId());
		values.put(COL_QUANTITY, orderItem.getQuantity());
		db.insert(TABLE_NAME, null, values);
	}


	public static void insertOrderItems(SQLiteDatabase db, List<Object> lOrderITems){
		ContentValues values = new ContentValues();

		for(Object obj : lOrderITems){
			OrderItem orderItem = (OrderItem) obj;

			values.put(COL_ORDER_ID, orderItem.getOrderId());
			values.put(COL_DISH_ID, orderItem.getDishId());
			values.put(COL_QUANTITY, orderItem.getQuantity());

			db.insert(TABLE_NAME, null, values);
			values.clear();
		}
	}

}
