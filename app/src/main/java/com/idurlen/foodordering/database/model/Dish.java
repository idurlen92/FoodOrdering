package com.idurlen.foodordering.database.model;

/**
 * @author Ivan Durlen
 */
public class Dish {

	private int id;
	private int dishTypeId;
	private int restaurantId;

	private float price;

	private String title;
	private String description;



	public int getId() {
		return id;
	}

	public int getDishTypeId() { return dishTypeId; }

	public int getRestaurantId() {
		return restaurantId;
	}

	public String getTitle() {
		return title;
	}

	public float getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}



	public void setId(int id) {
		this.id = id;
	}

	public void setDishTypeId(int dishTypeId) { this.dishTypeId = dishTypeId; }

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
