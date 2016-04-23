package com.idurlen.foodordering.net;

import android.util.Log;

import com.idurlen.foodordering.database.helper.Users;
import com.idurlen.foodordering.database.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * @author Ivan Durlen
 */
public class UsersRequest extends RestClient{


	public UsersRequest() {
		super("users.php");
	}


	@Override
	public List<Object> getAll() throws Exception {
		return null;
	}


	@Override
	public int insert(Object theObject) throws Exception {
		User user = (User) theObject;

		Map<String, String> mRequestParams = new HashMap<>();
		mRequestParams.put(Users.COL_FIRST_NAME, user.getFirstName());
		mRequestParams.put(Users.COL_LAST_NAME, user.getLastName());
		mRequestParams.put(Users.COL_EMAIL, user.getEmail());
		mRequestParams.put(Users.COL_PASSWORD, user.getPassword());
		mRequestParams.put(Users.COL_CITY, user.getCity());
		mRequestParams.put(Users.COL_ADDRESS, user.getAddress());
		//TODO: mRequestParams.put(Users.COL_BIRTH_DATE, user.getBirthDate());

		int iResult = RestService.REST_NO_INSERT;
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
		return 0;
	}




	/**
	 * Performs Web-service call to check User credentials.
	 * @param email
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public User getUser(String email, String password) throws Exception {
		final String ELEMENT_USER = "user";

		Map<String, String> mRequestParams = new HashMap<>();
		mRequestParams.put(Users.COL_EMAIL, email);
		mRequestParams.put(Users.COL_PASSWORD, password);

		User user = null;
		RestService service = new RestService(RestService.HttpMethod.GET, ENDPOINT_ADDRESS, mRequestParams);

		try {
			service.call();
			JSONResponse jsonResponse = service.getJSONResponse();
			if(jsonResponse.isError()) {
				Log.e("REST", jsonResponse.getErrorMessage());
			}
			else{
				user = (User) jsonResponse.getDataObject(ELEMENT_USER, User.class);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("Network error");
		}
		finally{
			service.close();
		}

		return user;
	}


}
