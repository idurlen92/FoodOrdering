package com.idurlen.foodordering.database.model;

/**
 * @author Ivan Durlen
 */
public class OrderItem {

	private int id;
	private int dishId;
	private int quantity;




	public int getId() {
		return id;
	}

	public int getDishId() {
		return dishId;
	}

	public int getQuantity() {
		return quantity;
	}



	public void setId(int id) {	this.id = id; }

	public void setDishId(int dishId) {
		this.dishId = dishId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
