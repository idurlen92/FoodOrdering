package com.idurlen.foodordering.net;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.idurlen.foodordering.database.helper.Orders;
import com.idurlen.foodordering.database.model.Order;
import com.idurlen.foodordering.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * @author Ivan Durlen
 */
public class OrdersRequest extends RestClient{


	public OrdersRequest() {
		super("orders.php");
	}




	@Override
	public List<Object> getAll() throws Exception {
		SessionManager session = SessionManager.getInstance(null);

		List<Object> lOrders = new ArrayList<>();
		Map<String, String> mRequestParams = new HashMap<>();
		mRequestParams.put(Orders.COL_USER_ID, Integer.toString(session.getUserId()));

		RestService service = new RestService(RestService.HttpMethod.GET, ENDPOINT_ADDRESS, mRequestParams);

		try {
			service.call();
			JSONResponse jsonResponse = service.getJSONResponse();
			if(jsonResponse.isError()) {
				Log.e("REST", jsonResponse.getErrorMessage());
			}
			else{
				final String ELEMENT_RESTAURANTS = "orders";
				TypeToken typeToken = new TypeToken<ArrayList<Order>>(){};
				lOrders.addAll((ArrayList<Order>) jsonResponse.getDataList(ELEMENT_RESTAURANTS, typeToken.getType()));
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("Network error");
		}
		finally{
			service.close();
		}

		return lOrders;
	}



	@Override
	public int insert(Object theObject) throws Exception {
		Order order = (Order) theObject;

		Map<String, String> mRequestParams = new HashMap<>();
		mRequestParams.put(Orders.COL_USER_ID, Integer.toString(order.getUserId()));
		mRequestParams.put(Orders.COL_RESTAURANT_ID, Integer.toString(order.getRestaurantId()));
		mRequestParams.put(Orders.COL_ORDER_CITY, order.getOrderCity());
		mRequestParams.put(Orders.COL_ORDER_ADDRESS, order.getOrderAddress());
		mRequestParams.put(Orders.COL_ORDER_TIME, order.getOrderTime());
		mRequestParams.put(Orders.COL_DELIVERY_TIME, order.getDeliveryTime());

		//Log.d("TIMESTAMP", order.getOrderTime());

		int iResult = REST_NO_INSERT;
		RestService service = new RestService(RestService.HttpMethod.POST, ENDPOINT_ADDRESS, mRequestParams);

		try {
			service.call();
			JSONResponse jsonResponse = service.getJSONResponse();
			if(jsonResponse.isError()) {
				Log.e("REST", jsonResponse.getErrorMessage());
			}
			else{
				iResult = (int) jsonResponse.getDataObject(JSONResponse.KEY_INSERT_ID, int.class);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("Network error");
		}
		finally{
			service.close();
		}

		return iResult;
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
