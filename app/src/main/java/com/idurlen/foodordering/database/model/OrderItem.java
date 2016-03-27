package com.idurlen.foodordering.database.model;

/**
 * @author Ivan Durlen
 */
public class OrderItem {

	private int id;
	private int menuId;
	private int quantity;




	public int getId() {
		return id;
	}

	public int getMenuId() {
		return menuId;
	}

	public int getQuantity() {
		return quantity;
	}



	public void setId(int id) {	this.id = id; }

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
