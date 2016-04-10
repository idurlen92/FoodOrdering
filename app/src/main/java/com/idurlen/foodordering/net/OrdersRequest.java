package com.idurlen.foodordering.net;

import android.util.Log;

import com.idurlen.foodordering.database.helper.Orders;
import com.idurlen.foodordering.database.model.Order;

import java.util.HashMap;
import java.util.Map;




/**
 * @author Ivan Durlen
 */
public class OrdersRequest {


	/**
	 * Performs Web-service call to insert an Order.
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public static int insertOrder(Order order) throws Exception{
		final String SERVICE_URL = "orders.php";

		Map<String, String> mRequestParams = new HashMap<>();
		mRequestParams.put(Orders.COL_USER_ID, Integer.toString(order.getUserId()));
		mRequestParams.put(Orders.COL_RESTAURANT_ID, Integer.toString(order.getRestaurantId()));
		mRequestParams.put(Orders.COL_ORDER_CITY, order.getOrderCity());
		mRequestParams.put(Orders.COL_ORDER_ADDRESS, order.getOrderAddress());
		mRequestParams.put(Orders.COL_ORDER_TIME, order.getOrderTime());

		int iResult = RestService.REST_NO_INSERT;
		RestService service = new RestService(RestService.HttpMethod.POST, SERVICE_URL, mRequestParams);

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


}
