package com.idurlen.foodordering.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;




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
	 *
	 * @param inputStreamReader pass it from HttpUrlConnection.getInputStream()
	 */
	public JSONResponse(InputStreamReader inputStreamReader){
		jsonObject = new JsonParser().parse(new JsonReader(inputStreamReader)).getAsJsonObject();
		Log.d("JSON", jsonObject.toString());
	}


	public boolean isError(){
		JsonElement element = jsonObject.get(KEY_IS_ERROR);
		return element.getAsBoolean();
	}


	public String getErrorMessage(){
		JsonElement element = jsonObject.get(KEY_ERROR_MESSAGE);
		return element.getAsString();
	}


	public Object getDataObject(String elementName, Class objClass){
		Gson gson = new Gson();
		return gson.fromJson(jsonObject.get(elementName), objClass);
	}

}
