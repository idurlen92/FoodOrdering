package com.idurlen.foodordering.net;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.idurlen.foodordering.database.model.Dish;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class DishesRequest extends RestClient{


	public DishesRequest() {
		super("dishes.php");
	}



	@Override
	public List<Object> getAll() throws Exception {
		List<Object> lDishes = new ArrayList<>();
		RestService service = new RestService(RestService.HttpMethod.GET, ENDPOINT_ADDRESS);

		try {
			service.call();
			JSONResponse jsonResponse = service.getJSONResponse();
			if(jsonResponse.isError()) {
				Log.e("REST", jsonResponse.getErrorMessage());
			}
			else{
				final String ELEMENT_DISHES = "dishes";
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
