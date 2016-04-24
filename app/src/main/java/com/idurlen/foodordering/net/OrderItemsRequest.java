package com.idurlen.foodordering.net;

import android.util.Log;

import com.idurlen.foodordering.database.helper.OrderItems;
import com.idurlen.foodordering.database.model.OrderItem;

import java.net.URLEncoder;
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



		StringBuilder builder = new StringBuilder("[");
		int k = 0;
		for(Object item : lObjects){
			OrderItem orderItem = (OrderItem) item;
			builder.append("{\"" + OrderItems.COL_ORDER_ID + "\":" + orderItem.getOrderId() + ",");
			builder.append("\"" + OrderItems.COL_DISH_ID + "\":" + orderItem.getDishId()+ ",");
			builder.append("\"" + OrderItems.COL_QUANTITY + "\":" + orderItem.getQuantity() + (k++ < (lObjects.size() - 1) ? "}," : "}]"));
		}

		Log.d("JSON OrdItm:", builder.toString());

		String sJsonArray = "orderItems=";
		try{
			sJsonArray += URLEncoder.encode(builder.toString(), "UTF-8");
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		RestService service = new RestService(RestService.HttpMethod.POST, ENDPOINT_ADDRESS, sJsonArray);
		List<Integer> lInsertIds = new ArrayList<>();

		try {
			service.call();
			JSONResponse jsonResponse = service.getJSONResponse();
			if(jsonResponse.isError()) {
				Log.e("REST", jsonResponse.getErrorMessage());
			}
		}
		catch(Exception e){
			e.printStackTrace();
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
