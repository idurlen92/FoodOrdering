package com.idurlen.foodordering.database;


public class SchemaDefinition {

	public static final String TABLE_USERS = "users";
	public static final String TABLE_RESTAURANTS = "restaurants";
	public static final String TABLE_MENUS = "menus";
	public static final String TABLE_ORDERS = "orders";
	public static final String TABLE_ORDER_ITEMS = "order_items";



	public static String[] getTableNames(){
		return new String[]{
				TABLE_USERS, TABLE_RESTAURANTS, TABLE_MENUS,
				TABLE_ORDERS, TABLE_ORDER_ITEMS
		};
	}


	public static final String createTableUsers = "CREATE TABLE " + TABLE_USERS + "(" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"first_name VARCHAR(30) NOT NULL, " +
			"last_name VARCHAR(30) NOT NULL, " +
			"birth_date DATE NOT NULL, " +
			"city VARCHAR(45) NOT NULL, " +
			"address VARCHAR(45) NOT NULL, " +
			"email VARCHAR(45) NOT NULL)";

	public static final String createTableRestaurants = "CREATE TABLE " + TABLE_RESTAURANTS +"(" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"name VARCHAR(30) NOT NULL, " +
			"city VARCHAR(45) NOT NULL, " +
			"address VARCHAR(45) NOT NULL, " +
			"email VARCHAR(45), phone VARCHAR(15) )";

	public static final String createTableMenus = "CREATE TABLE " + TABLE_MENUS + "(" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"restaurant_id INTEGER REFERENCES restaurants(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
			"title VARCHAR(30) NOT NULL, " +
			"price REAL NOT NULL, "  +
			"description TEXT)";

	public static final String createTableOrders = "CREATE TABLE " + TABLE_ORDERS + "(" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"user_id INTEGER REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
			"order_time DATETIME DEFAULT CURRENT_TIMESTAMP, " +
			"order_address VARCHAR(45), " +
			"canceled INTEGER DEFAULT 0, " +
			"delivered INTEGER DEFAULT 0)";

	public static final String createTableOrderItems = "CREATE TABLE " + TABLE_ORDER_ITEMS + "(" +
			"order_id INTEGER REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
			"menu_id INTEGER REFERENCES menus(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
			"quantity INTEGER DEFAULT 1)";
}
