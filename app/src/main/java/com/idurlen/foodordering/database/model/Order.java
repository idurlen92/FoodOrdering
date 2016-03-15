package com.idurlen.foodordering.database.model;

/**
 *  @author Ivan Durlen
 */
public class Order {

	private boolean canceled;

	private int id;
	private int userId;
	private int restaurantId;

	private String orderTime;
	private String orderAddress;




	public boolean isCanceled() {
		return canceled;
	}




	public int getId() {
		return id;
	}




	public int getUserId() {
		return userId;
	}




	public int getRestaurantId() {
		return restaurantId;
	}




	public String getOrderTime() {
		return orderTime;
	}




	public String getOrderAddress() {
		return orderAddress;
	}




	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}




	public void setId(int id) {
		this.id = id;
	}




	public void setUserId(int userId) {
		this.userId = userId;
	}




	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}




	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}




	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}
}