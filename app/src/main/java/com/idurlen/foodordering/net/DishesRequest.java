package com.idurlen.foodordering.net;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.idurlen.foodordering.database.model.Dish;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class DishesRequest {


	/**
	 * Performs Web-service call to fetch list of Dishes.
	 * @return
	 * @throws Exception
	 */
	public static List<Dish> getDishes() throws Exception{
		final String SERVICE_URL = "dishes.php";
		final String ELEMENT_DISHES = "dishes";

		List<Dish> lDishes = new ArrayList<Dish>();
		RestService service = new RestService(RestService.HttpMethod.GET, SERVICE_URL, null);

		try {
			service.call();
			JSONResponse jsonResponse = service.getJSONResponse();
			if(jsonResponse.isError()) {
				Log.e("REST", jsonResponse.getErrorMessage());
			}
			else{
				TypeToken typeToken = new TypeToken<ArrayList<Dish>>(){};
				lDishes.addAll( (ArrayList<Dish>) jsonResponse.getDataList(ELEMENT_DISHES, typeToken.getType()));
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("Network error");
		}
		finally{
			service.close();
		}

		return lDishes;
	}

}
