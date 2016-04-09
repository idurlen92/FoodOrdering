package com.idurlen.foodordering.net;

import android.util.Log;

import com.idurlen.foodordering.database.helper.Users;
import com.idurlen.foodordering.database.model.User;

import java.util.HashMap;
import java.util.Map;




/**
 * @author Ivan Durlen
 */
public class UsersRequest {


	/**
	 *
	 * @param email
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static User getUser(String email, String password) throws Exception{
		final String SERVICE_URL = "users.php";
		final String ELEMENT_USER = "user";

		Map<String, String> mRequestParams = new HashMap<>();
		mRequestParams.put(Users.COL_EMAIL, email);
		mRequestParams.put(Users.COL_PASSWORD, password);

		RestService service = new RestService(RestService.HttpMethod.GET, SERVICE_URL, mRequestParams);
		service.call();

		JSONResponse jsonResponse = service.getJSONResponse();
		service.close();

		if(jsonResponse.isError()){
			Log.e("REST", jsonResponse.getErrorMessage());
			return null;
		}
		return (User) jsonResponse.getDataObject(ELEMENT_USER, User.class);
	}


	public static int insertUser(User user) throws Exception{
		final String SERVICE_URL = "users.php";

		Map<String, String> mRequestParams = new HashMap<>();
		mRequestParams.put(Users.COL_FIRST_NAME, user.getFirstName());
		mRequestParams.put(Users.COL_LAST_NAME, user.getLastName());
		mRequestParams.put(Users.COL_EMAIL, user.getEmail());
		mRequestParams.put(Users.COL_PASSWORD, user.getPassword());
		mRequestParams.put(Users.COL_CITY, user.getCity());
		mRequestParams.put(Users.COL_ADDRESS, user.getAddress());
		//TODO: mRequestParams.put(Users.COL_BIRTH_DATE, user.getBirthDate());

		RestService service = new RestService(RestService.HttpMethod.POST, SERVICE_URL, mRequestParams);
		service.call();

		JSONResponse jsonResponse = service.getJSONResponse();
		service.close();

		if(jsonResponse.isError()){
			Log.e("REST", jsonResponse.getErrorMessage());
			return RestService.REST_INSERT_ERROR;
		}
		return (int) jsonResponse.getDataObject(JSONResponse.KEY_INSERT_ID, int.class);
	}


}
