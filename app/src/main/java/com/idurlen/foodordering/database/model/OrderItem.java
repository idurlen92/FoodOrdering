package com.idurlen.foodordering.database.model;

/**
 * @author Ivan Durlen
 */
public class OrderItem {

	private int dishId;
	private int orderId;
	private int quantity;


	public int getDishId() {
		return dishId;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getQuantity() {
		return quantity;
	}


	public void setDishId(int dishId) {
		this.dishId = dishId;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
