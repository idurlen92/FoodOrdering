package com.idurlen.foodordering.net;

import android.util.Log;

import com.idurlen.foodordering.database.helper.OrderItems;
import com.idurlen.foodordering.database.model.OrderItem;

import java.util.HashMap;
import java.util.Map;




/**
 * @author Ivan Durlen
 */
public class OrderItemsRequest {


	/**
	 * Calls Web-service to insert an OrderItem.
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public static int insertOrderItem(OrderItem orderItem) throws Exception{
		final String SERVICE_URL = "order_items.php";

		Map<String, String> mRequestParams = new HashMap<>();
		mRequestParams.put(OrderItems.COL_ORDER_ID, Integer.toString(orderItem.getOrderId()));
		mRequestParams.put(OrderItems.COL_DISH_ID, Integer.toString(orderItem.getDishId()));
		mRequestParams.put(OrderItems.COL_QUANTITY, Integer.toOctalString(orderItem.getQuantity()));

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
