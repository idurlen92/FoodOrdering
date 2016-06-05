package com.idurlen.foodordering.net;

import java.io.BufferedWriter;
import java.io.IOException;
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

	public static int REST_NO_INSERT_UPDATE = 0;
	public static int REST_INSERT_UPDATE_ERROR = -1;

	public enum HttpMethod { GET, POST, PUT, DELETE };

	private static final String DOMAIN_URL = "http://www.nikola-markotic.from.hr/food_ordering/service/";

	private final String sServiceUrl;

	private String sJsonParams;

	private HttpMethod method;
	private Map<String, String> mRequestParams;

	private HttpURLConnection connection;




	/**
	 * Calls {@code  RestService(HttpMethod, String, Map<String, String>)} with no HTTP parameters.
	 * @param method
	 * @param sServiceUrl
	 */
	public RestService(HttpMethod method, String sServiceUrl){
		this(method, sServiceUrl, null, null);
	}


	/**
	 *
	 * @param method
	 * @param sEndpointAddress
	 * @param mRequestParams
	 */
	public RestService(HttpMethod method, String sEndpointAddress, Map<String, String> mRequestParams){
		this(method, sEndpointAddress, mRequestParams, null);
	}




	/**
	 *
	 * @param method
	 * @param sEndpointAddress
	 * @param sJsonParams
	 */
	public RestService(HttpMethod method, String sEndpointAddress, String sJsonParams){
		this(method, sEndpointAddress, null, sJsonParams);
	}




	/**
	 *
	 * @param method
	 * @param sEndpointAddress
	 * @param mRequestParams
	 * @param sJsonParams
	 */
	public RestService(HttpMethod method, String sEndpointAddress, Map<String, String> mRequestParams, String sJsonParams){
		this.method = method;
		this.sServiceUrl = DOMAIN_URL + sEndpointAddress;
		this.mRequestParams = mRequestParams;
		this.sJsonParams = sJsonParams;
	}


	/**
	 * Performs Web-service call.
	 * @throws IOException
	 */
	public void call() throws Exception{
		URL url = url = new URL(hasGETParams() ? (sServiceUrl + '?' + createQueryString()) : sServiceUrl);
		connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setRequestMethod(method.toString());
		if(hasParams()) {
			connection.setDoOutput(true);
			createParams(connection.getOutputStream());
		}

		connection.connect();
	}


	/**
	 * Returns response as {@link  JSONResponse} or {@link IOException} if error occurred.
	 * @return
	 * @throws IOException
	 */
	public JSONResponse getJSONResponse() throws IOException{
		if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
			throw new IOException("HTTP request error: " + connection.getResponseMessage());
		}
		return  new JSONResponse(connection.getInputStream());
	}


	/**
	 * Closes HttpUrlConnection. Mandatory!
	 */
	public void close(){
		if(connection != null) {
			connection.disconnect();
		}
	}


	/**
	 * Returns true if HTTP method is GET and there are request parameters.
	 * @return
	 */
	private boolean hasGETParams(){
		return (HttpMethod.GET.equals(method) && mRequestParams != null && !mRequestParams.isEmpty());
	}


	/**
	 * Returns true if HTTP method is not GET and there are request parameters.
	 * @return
	 */
	private boolean hasParams() {
		return (!HttpMethod.GET.equals(method) &&
				((mRequestParams != null && !mRequestParams.isEmpty()) ||
				(sJsonParams != null && ! sJsonParams.isEmpty())));
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
			if(mRequestParams != null) {
				int j = 0;
				for(Map.Entry<String, String> entry : mRequestParams.entrySet()) {
					builder.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "=");
					builder.append(URLEncoder.encode(entry.getValue(), "UTF-8") + (j++ < (mRequestParams.size() - 1) ? "&" : ""));
				}
			}
			else{
				builder.append(sJsonParams);
			}
		}
		catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}

		return builder.toString();
	}


}
