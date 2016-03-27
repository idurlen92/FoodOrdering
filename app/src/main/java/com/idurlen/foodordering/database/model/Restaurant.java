package com.idurlen.foodordering.database.model;

/**
 * @author Ivan Durlen
 */
public class Restaurant {

	private int id;

	private String name;
	private String city;
	private String address;
	private String description;
	private String email;
	private String phone;
	private String worksFrom;
	private String worksTo;
	private String ordersFrom;
	private String ordersTo;


	public Restaurant(){}



	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public String getAddress() {
		return address;
	}

	public String getDescription() {
		return description;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getWorksFrom() {
		return worksFrom;
	}

	public String getWorksTo() {
		return worksTo;
	}

	public String getOrdersFrom() {
		return ordersFrom;
	}

	public String getOrdersTo() {
		return ordersTo;
	}



	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setWorksFrom(String worksFrom) {
		this.worksFrom = worksFrom;
	}

	public void setWorksTo(String worksTo) {
		this.worksTo = worksTo;
	}

	public void setOrdersFrom(String ordersFrom) {
		this.ordersFrom = ordersFrom;
	}

	public void setOrdersTo(String ordersTo) {
		this.ordersTo = ordersTo;
	}

}
