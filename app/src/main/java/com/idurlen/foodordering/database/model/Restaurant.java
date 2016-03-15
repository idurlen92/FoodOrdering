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
	private String works_from;
	private String works_to;
	private String orders_from;
	private String orders_to;




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




	public String getWorks_from() {
		return works_from;
	}




	public String getWorks_to() {
		return works_to;
	}




	public String getOrders_from() {
		return orders_from;
	}




	public String getOrders_to() {
		return orders_to;
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




	public void setWorks_from(String works_from) {
		this.works_from = works_from;
	}




	public void setWorks_to(String works_to) {
		this.works_to = works_to;
	}




	public void setOrders_from(String orders_from) {
		this.orders_from = orders_from;
	}




	public void setOrders_to(String orders_to) {
		this.orders_to = orders_to;
	}
}
