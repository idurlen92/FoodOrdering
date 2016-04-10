package com.idurlen.foodordering.net;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.idurlen.foodordering.database.model.Restaurant;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class RestaurantsRequest {


	/**
	 * Performs Web-service call to fetch list of Restaurants.
	 * @return
	 * @throws Exception
	 */
	public static List<Restaurant> getRestaurants() throws Exception {
		final String SERVICE_URL = "restaurants.php";
		final String ELEMENT_RESTAURANTS = "restaurants";

		List<Restaurant> lRestaurants = new ArrayList<Restaurant>();
		RestService service = new RestService(RestService.HttpMethod.GET, SERVICE_URL, null);

		try {
			service.call();
			JSONResponse jsonResponse = service.getJSONResponse();
			if(jsonResponse.isError()) {
				Log.e("REST", jsonResponse.getErrorMessage());
			}
			else{
				TypeToken typeToken = new TypeToken<ArrayList<Restaurant>>(){};
				lRestaurants = (ArrayList<Restaurant>) jsonResponse.getDataList(ELEMENT_RESTAURANTS, typeToken.getType());
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("Network error");
		}
		finally{
			service.close();
		}

		return lRestaurants;
	}


}
