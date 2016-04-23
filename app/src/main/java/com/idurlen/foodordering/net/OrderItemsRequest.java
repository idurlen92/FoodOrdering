package com.idurlen.foodordering.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idurlen.foodordering.database.model.OrderItem;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Ivan Durlen
 */
public class OrderItemsRequest extends RestClient{


	public OrderItemsRequest() {
		super("order_items.php");
	}


	@Override
	public List<Object> getAll() throws Exception {
		return null;
	}


	@Override
	public int insert(Object theObject) throws Exception {
		return REST_NO_INSERT;
	}




	@Override
	public List<Integer> insertAll(List<Object> lObjects) throws Exception {
		List<OrderItem> lOrderItems = new ArrayList<>();
		for(Object item : lObjects){
			lOrderItems.add((OrderItem) item);
		}

		Gson gson = new Gson();
		String sJsonParam = gson.toJson(lOrderItems);
		Log.d("Order items", sJsonParam);

		RestService service = new RestService(RestService.HttpMethod.POST, ENDPOINT_ADDRESS, sJsonParam);
		List<Integer> lInsertIds = new ArrayList<>();

		try {
			service.call();
			JSONResponse jsonResponse = service.getJSONResponse();
			if(jsonResponse.isError()) {
				Log.e("REST", jsonResponse.getErrorMessage());
			}
			else{
				TypeToken typeToken = new TypeToken<ArrayList<Integer>>(){};
				lInsertIds.addAll((ArrayList<Integer>) jsonResponse.getDataList(JSONResponse.KEY_INSERT_ID, typeToken.getType()));
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("Network error");
		}
		finally{
			service.close();
		}

		return lInsertIds;
	}




	@Override
	public int update(Object theObject) throws Exception {
		return 0;
	}


}
