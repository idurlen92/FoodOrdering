package com.idurlen.foodordering.database.helper;

import android.database.Cursor;
import android.util.Log;

import com.idurlen.foodordering.utils.StringUtils;




/**
 * Contains helper methods for SQLiteDatabase and POJO classes.
 * @author Ivan Durlen
 */
public class HelperMethods {


	/**
	 * Extracts values fetched from cursor to POJO object via reflection.
	 * Note: for using this method do not mix tables in queries!
	 * @param cursor
	 * @param obj
	 * @return
	 */
	protected static Object extractFields(Cursor cursor, Object obj){
		String methodName = "";

		for(int i = 0; i < cursor.getColumnCount(); i++) {
			if(cursor.isNull(i)){
				Log.d("NULL COL", cursor.getColumnName(i) + "(" + i + ")" );
				continue;
			}

			try {
				methodName = "set";
				String columnName = cursor.getColumnName(i);

				if(columnName.contains("_")){
					methodName += StringUtils.concatCamelCase(columnName.split("_"));
				}
				else{
					methodName += StringUtils.capitalize(columnName);
				}
				Log.d("METHOD", methodName);

				if(cursor.getType(i) == Cursor.FIELD_TYPE_INTEGER){
					obj.getClass().getMethod(methodName, Integer.class).invoke(obj, cursor.getInt(i));
					Log.d("REFLECTION", "String setter called: " + methodName);
				}
				else if(cursor.getType(i) == Cursor.FIELD_TYPE_FLOAT) {
					obj.getClass().getMethod(methodName, Float.class).invoke(obj, cursor.getFloat(i));
					Log.d("REFLECTION", "String setter called: " + methodName);
				}
				else if(cursor.getType(i) == Cursor.FIELD_TYPE_STRING) {
					obj.getClass().getMethod(methodName, String.class).invoke(obj, cursor.getString(i));
					Log.d("REFLECTION", "String setter called: " + methodName);
				}

			}
			catch(NoSuchMethodException e){
				Log.e("REFLECTION", "Method " + methodName + " not found ");
				e.printStackTrace();
			}
			catch(IllegalArgumentException e){
				Log.e("REFLECTION", "Wrong method args (" + methodName + ")");
				e.printStackTrace();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}// for(...)

		return obj;
	}

}
