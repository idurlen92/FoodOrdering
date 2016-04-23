package com.idurlen.foodordering.net;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.idurlen.foodordering.database.model.DishType;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class DishTypesRequest extends RestClient{


	public DishTypesRequest(){
		super("dish_types.php");
	}


	/**
	 * Performs Web-service call to fetch list of DishTypes.
	 * @return
	 * @throws Exception
	 */
	@Override
	public  List<Object> getAll() throws Exception {
		List<Object> lDishTypes = new ArrayList<>();
		RestService service = new RestService(RestService.HttpMethod.GET, ENDPOINT_ADDRESS);

		try {
			service.call();
			JSONResponse jsonResponse = service.getJSONResponse();
			if(jsonResponse.isError()) {
				Log.e("REST", jsonResponse.getErrorMessage());
			}
			else{
				final String ELEMENT_DISH_TYPES = "dishTypes";
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
