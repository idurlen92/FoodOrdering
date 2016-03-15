package com.idurlen.foodordering.database.model;

/**
 * @author Ivan Durlen
 */
public class Dish {

	private int id;
	private int restaurantId;

	private String title;
	private String price;
	private String description;




	public int getId() {
		return id;
	}




	public int getRestaurantId() {
		return restaurantId;
	}




	public String getTitle() {
		return title;
	}




	public String getPrice() {
		return price;
	}




	public String getDescription() {
		return description;
	}




	public void setId(int id) {
		this.id = id;
	}




	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public void setPrice(String price) {
		this.price = price;
	}




	public void setDescription(String description) {
		this.description = description;
	}
}
