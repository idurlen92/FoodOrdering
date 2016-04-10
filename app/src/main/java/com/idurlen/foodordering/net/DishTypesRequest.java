package com.idurlen.foodordering.net;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.idurlen.foodordering.database.model.DishType;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class DishTypesRequest {


	/**
	 * Performs Web-service call to fetch list of DishTypes.
	 * @return
	 * @throws Exception
	 */
	public static List<DishType> getDishTypes() throws Exception{
		final String SERVICE_URL = "dish_types.php";
		final String ELEMENT_DISH_TYPES = "dishTypes";

		List<DishType> lDishTypes = new ArrayList<DishType>();
		RestService service = new RestService(RestService.HttpMethod.GET, SERVICE_URL, null);

		try {
			service.call();
			JSONResponse jsonResponse = service.getJSONResponse();
			if(jsonResponse.isError()) {
				Log.e("REST", jsonResponse.getErrorMessage());
			}
			else{
				TypeToken typeToken = new TypeToken<ArrayList<DishType>>(){};
				lDishTypes.addAll((ArrayList<DishType>) jsonResponse.getDataList(ELEMENT_DISH_TYPES, typeToken.getType()));
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("Network error");
		}
		finally{
			service.close();
		}

		return lDishTypes;
	}

}
