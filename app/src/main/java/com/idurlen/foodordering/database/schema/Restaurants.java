package com.idurlen.foodordering.database.schema;

/**
 * @author Ivan Durlen
 */
public class Restaurants {

	public static final String TABLE_NAME = "restaurants";

	public static final String COL_ID = "id";
	public static final String COL_NAME = "name";
	public static final String COL_CITY = "city";
	public static final String COL_ADDRESS = "address";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_EMAIL = "email";
	public static final String COL_PHONE = "phone";
	public static final String COL_WORKS_FROM = "works_from";
	public static final String COL_WORKS_TO = "works_to";
	public static final String COL_ORDERS_FROM = "orders_from";
	public static final String COL_ORDERS_TO = "orders_to";


	public static String getCreateTableStatement(){
		return "CREATE TABLE " + TABLE_NAME +"(" +
				COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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

}
