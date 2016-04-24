package com.idurlen.foodordering.database.helper;

import android.database.Cursor;
import android.util.Log;

import com.idurlen.foodordering.utils.StringUtils;

import java.util.Set;




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

				if(cursor.getType(i) == Cursor.FIELD_TYPE_INTEGER){
					//Log.d("REFLECTION", "Integer setter called: " + methodName);
					obj.getClass().getDeclaredMethod(methodName, int.class).invoke(obj, cursor.getInt(i));
				}
				else if(cursor.getType(i) == Cursor.FIELD_TYPE_FLOAT) {
					//Log.d("REFLECTION", "Float setter called: " + methodName);
					obj.getClass().getDeclaredMethod(methodName, float.class).invoke(obj, cursor.getFloat(i));
				}
				else if(cursor.getType(i) == Cursor.FIELD_TYPE_STRING) {
					//Log.d("REFLECTION", "String setter called: " + methodName);
					obj.getClass().getDeclaredMethod(methodName, String.class).invoke(obj, cursor.getString(i));
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



	/**
	 * Returns "?"s as param for IN operator in query.
	 * @param sParams
	 * @return
	 */
	protected static <T> String getInStatementQueryParams(Set<T> sParams){
		StringBuilder queryBuilder = new StringBuilder();
		for(int i=0; i < sParams.size(); i++){
			queryBuilder.append("?" + (i < (sParams.size() - 1) ? ", " : ""));
		}
		return queryBuilder.toString();
	}



	/**
	 * Converts Set of primitve types to String array.
	 * @param sParams
	 * @param <T>
	 * @return
	 */
	protected static <T> String[] getSetAsStringArray(Set<T> sParams){
		String[] asParams = new String[sParams.size()];
		int j=0;
		for(Object param : sParams){
			asParams[j++] = String.valueOf(param);
		}
		return asParams;
	}


}
