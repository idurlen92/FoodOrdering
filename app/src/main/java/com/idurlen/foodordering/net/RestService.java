package com.idurlen.foodordering.net;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;




/**
 *  @author Ivan Durlen
 */
public class RestService {

	public static int REST_INSERT_ERROR = -1;

	public enum HttpMethod { GET, POST, PUT, DELETE };

	private static final String DOMAIN_URL = "http://www.nikola-markotic.from.hr/food_ordering/service/";

	private final HttpMethod method;
	private final String sServiceUrl;
	private final Map<String, String> mRequestParams;

	private HttpURLConnection connection;




	/**
	 * Calls {@code  RestService(HttpMethod, String, Map<String, String>)} with last param null.
	 * @param method
	 * @param sServiceUrl
	 */
	public RestService(HttpMethod method, String sServiceUrl){
		this(method, sServiceUrl, null);
	}


	/**
	 *
	 * @param method
	 * @param sServiceUrl
	 * @param mRequestParams
	 */
	public RestService(HttpMethod method, String sServiceUrl, Map<String, String> mRequestParams){
		this.method = method;
		this.sServiceUrl = DOMAIN_URL + sServiceUrl;
		this.mRequestParams = mRequestParams;
	}


	/**
	 * Performs Web-service call.
	 * @throws Exception
	 */
	public void call() throws Exception{
		URL url = new URL(hasGETParams() ? (sServiceUrl + '?' + createQueryString()) : sServiceUrl);
		connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod(method.toString());
		if(hasParams()){
			connection.setDoOutput(true);
			createParams(connection.getOutputStream());
		}

		connection.connect();
	}


	/**
	 * Returns response as {@link  JSONResponse} or <strong>null</strong> if error occurred.
	 * @return
	 * @throws Exception
	 */
	public JSONResponse getJSONResponse() throws Exception{
		if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
			throw new Exception("HTTP request error: " + connection.getResponseMessage());
		}
		return  new JSONResponse(new InputStreamReader(connection.getInputStream(), "UTF-8"));
	}


	/**
	 * Closes HttpUrlConnection. Mandatory!
	 */
	public void close(){
		connection.disconnect();
	}


	/**
	 * Returns true if HTTP method is GET and there are request parameters.
	 * @return
	 */
	private boolean hasGETParams(){
		return (HttpMethod.GET.equals(method) && mRequestParams != null && mRequestParams.size() > 0);
	}


	/**
	 * Returns true if HTTP method is not GET and there are request parameters.
	 * @return
	 */
	private boolean hasParams() {
		return (! HttpMethod.GET.equals(method) && mRequestParams != null && mRequestParams.size() > 0);
	}


	/**
	 * Creates POST, PUT and DELETE parameters for HTTP request.
	 * @param outputStream
	 */
	private void createParams(OutputStream outputStream) throws IOException{
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
		}
		catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}

		writer.write(createQueryString());
		writer.flush();
		writer.close();
		outputStream.close();
	}


	/**
	 * Creates query string format parameters for the request.
	 * @return String
	 */
	private String createQueryString(){
		StringBuilder builder = new StringBuilder();

		try {
			int j = 0;
			for(Map.Entry<String, String> entry : mRequestParams.entrySet()) {
				builder.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "=");
				builder.append(URLEncoder.encode(entry.getValue(), "UTF-8") + (j++ < (mRequestParams.size() - 1) ? "&" : ""));
			}
		}
		catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}

		return builder.toString();
	}


}
