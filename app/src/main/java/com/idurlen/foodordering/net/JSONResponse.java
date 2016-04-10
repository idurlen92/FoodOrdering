package com.idurlen.foodordering.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;




/**
 * Utillites methods for parsing JSON using Gson.
 * It is specific for JSON of type: {isError:"", errorMsg:"", ?, array/obj:[]/{}}
 */
public class JSONResponse {

	public static final String KEY_INSERT_ID = "insertId";
	public static final String KEY_IS_UPDATED = "isUpdated";

	private final String KEY_IS_ERROR = "isError";
	private final String KEY_ERROR_MESSAGE = "errorMessage";

	private JsonObject jsonObject;


	/**
	 * Creates new JSONResponse object using {@link Gson}.
	 * @param inputStream pass it from HttpUrlConnection.getInputStream()
	 */
	public JSONResponse(InputStream inputStream) throws IOException{
		BufferedInputStream bufferedInputStream = null;
		InputStreamReader inputStreamReader = null;

		try {
			bufferedInputStream = new BufferedInputStream(inputStream);
			inputStreamReader = new InputStreamReader(bufferedInputStream, "UTF-8");
			jsonObject = new JsonParser().parse(new JsonReader(inputStreamReader)).getAsJsonObject();
			Log.d("JSON response", jsonObject.toString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			bufferedInputStream.close();
			if(inputStreamReader != null){
				inputStream.close();
			}
		}
	}


	/**
	 * Returns the field isError in response.
	 * @return
	 */
	public boolean isError(){
		JsonElement element = jsonObject.get(KEY_IS_ERROR);
		return element.getAsBoolean();
	}


	/**
	 * Returns the field errorMessage in response.
	 * @return
	 */
	public String getErrorMessage(){
		JsonElement element = jsonObject.get(KEY_ERROR_MESSAGE);
		return element.getAsString();
	}


	/**
	 * Returns data object from JSON response - {@link com.idurlen.foodordering.database.model.User}, {@link com.idurlen.foodordering.database.model.Restaurant}, etc.
	 * @param elementName String name of data element
	 * @param objClass Class of data object
	 * @return
	 */
	public Object getDataObject(String elementName, Class objClass){
		Gson gson = new Gson();
		return gson.fromJson(jsonObject.get(elementName), objClass);
	}


	public Object getDataList(String elementName, Type listType){
		Gson gson = new Gson();
		return gson.fromJson(jsonObject.get(elementName), listType);
	}

}
