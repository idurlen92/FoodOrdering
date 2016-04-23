package com.idurlen.foodordering.net;

import java.util.List;




/**
 *
 * @author Ivan Durlen
 */
public abstract class RestClient {

	public static int REST_NO_INSERT = -100;
	public static int REST_INSERT_ERROR = -101;
	public static int REST_NO_UPDATE = -200;
	public static int REST_UPDATE_ERROR = -201;

	protected final String ENDPOINT_ADDRESS;

	public RestClient(String endpointAddress){
		this.ENDPOINT_ADDRESS = endpointAddress;
	}


	/**
	 * Performs Web-service call to get all rows from the table.
	 * @return {@code List<Object>}
	 * @throws Exception
	 */
	public abstract List<Object> getAll() throws Exception;

	/**
	 * Performs Web-service call to insert a row.
	 * @param theObject
	 * @return {@code integer} Insert ID or status code
	 * @throws Exception
	 */
	public abstract int insert(Object theObject) throws Exception;

	/**
	 * Performs Web-service call to insert a row.
	 * @param lObjects
	 * @return {@code integer} List of Insert IDs or status code
	 * @throws Exception
	 */
	public abstract List<Integer> insertAll(List<Object> lObjects) throws Exception;

	/**
	 * Performs Web-service call to update a row.
	 * @param theObject
	 * @return {@code integer} List of Insert ID or status code
	 * @throws Exception
	 */
	public abstract int update(Object theObject) throws Exception;

}
