package com.idurlen.foodordering.database.model;

/**
 *  @author Ivan Durlen
 */
public class Order {

	private boolean isCanceled;

	private int id;
	private int userId;
	private int restaurantId;

	private String orderTime;
	private String deliveryTime;
	private String orderAddress;
	private String orderCity;


	public Order(){/* Required default constructor */ }



	public Order(int userId, int restaurantId, String orderCity, String orderAddress, String orderTime, String deliveryTime){
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.orderCity = orderCity;
		this.orderAddress = orderAddress;
		this.orderTime = orderTime;
		this.deliveryTime = deliveryTime;
	}


	public boolean isCanceled() { return isCanceled; }

	public int getId() { return id; }

	public int getUserId() { return userId; }

	public int getRestaurantId() { return restaurantId; }

	public String getOrderTime() { return orderTime; }

	public String getDeliveryTime() { return deliveryTime; }

	public String getOrderAddress() { return orderAddress; }

	public String getOrderCity() { return orderCity; }



	public void setIsCanceled(boolean isCanceled) { this.isCanceled = isCanceled; }

	public void setId(int id) { this.id = id; }

	public void setUserId(int userId) { this.userId = userId; }

	public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

	public void setOrderTime(String orderTime) { this.orderTime = orderTime; }

	public void setDeliveryTime(String deliveryTime) { this.deliveryTime = deliveryTime; }

	public void setOrderAddress(String orderAddress) { this.orderAddress = orderAddress; }

	public void setOrderCity(String orderCity) { this.orderCity = orderCity; }


}
