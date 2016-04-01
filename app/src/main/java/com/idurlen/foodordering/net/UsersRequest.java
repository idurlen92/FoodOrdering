package com.idurlen.foodordering.net;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.idurlen.foodordering.database.model.User;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




/**
 * @author Ivan Durlen
 */
public class UsersRequest {


	public static User getUser(String email, String password){
		//TODO: business logic
		User user = new User();
		user.setId(5);
		user.setEmail("ivan@example.com");
		user.setFirstName("Ivan");
		user.setLastName("Durlen");
		user.setAddress("Savska cesta 106");
		user.setCity("Zagreb");
		user.setBirthDate("1992-10-30");

		return user;
	}


	public static void test(){
		try {
			URL url = new URL("http://httpbin.org/get");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			if(connection.getResponseCode() == 200){
				JsonReader reader = new JsonReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				JsonElement elem = new JsonParser().parse(reader);
				Log.d("JSON", elem.toString());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


}
