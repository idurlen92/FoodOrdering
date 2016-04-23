package com.idurlen.foodordering.net;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.idurlen.foodordering.database.model.Restaurant;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class RestaurantsRequest extends RestClient{


	public RestaurantsRequest() {
		super("restaurants.php");
	}


	@Override
	public List<Object> getAll() throws Exception {
		List<Object> lRestaurants = new ArrayList<>();
		RestService service = new RestService(RestService.HttpMethod.GET, ENDPOINT_ADDRESS);

		try {
			service.call();
			JSONResponse jsonResponse = service.getJSONResponse();
			if(jsonResponse.isError()) {
				Log.e("REST", jsonResponse.getErrorMessage());
			}
			else{
				final String ELEMENT_RESTAURANTS = "restaurants";
				TypeToken typeToken = new TypeToken<ArrayList<Restaurant>>(){};
				lRestaurants.addAll((ArrayList<Restaurant>) jsonResponse.getDataList(ELEMENT_RESTAURANTS, typeToken.getType()));
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


	@Override
	public int insert(Object theObject) throws Exception {
		return REST_NO_INSERT;
	}


	@Override
	public List<Integer> insertAll(List<Object> lObjects) throws Exception {
		return null;
	}


	@Override
	public int update(Object theObject) throws Exception {
		return REST_NO_UPDATE;
	}


}
