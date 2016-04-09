package com.idurlen.foodordering.database.helper;

/**
 * @author Ivan Durlen
 */
public class Users {

	public static final String TABLE_NAME = "users";

	public static final String COL_ID = "id";
	public static final String COL_FIRST_NAME = "first_name";
	public static final String COL_LAST_NAME ="last_name";
	public static final String COL_BIRTH_DATE = "birth_date";
	public static final String COL_CITY = "city";
	public static final String COL_ADDRESS = "address";
	public static final String COL_EMAIL = "email";
	public static final String COL_PASSWORD = "password";

	public static String getCreateTableStatement(){
		return "CREATE TABLE " + TABLE_NAME + "(" +
				COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				COL_FIRST_NAME + " VARCHAR(30) NOT NULL, " +
				COL_LAST_NAME + " VARCHAR(30) NOT NULL, " +
				COL_BIRTH_DATE + " DATE, " +
				COL_CITY + " VARCHAR(45) NOT NULL, " +
				COL_ADDRESS + " VARCHAR(45) NOT NULL, " +
				COL_EMAIL + " VARCHAR(45) NOT NULL)";
	}


}
