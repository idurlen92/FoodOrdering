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
	private String workingFrom;
	private String workingUntil;
	private String orderingFrom;
	private String orderingUntil;


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

	public String getWorkingFrom() {
		return workingFrom;
	}

	public String getWorkingUntil() {
		return workingUntil;
	}

	public String getOrderingFrom() {
		return orderingFrom;
	}

	public String getOrderingUntil() {
		return orderingUntil;
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

	public void setWorkingFrom(String workingFrom) {
		this.workingFrom = workingFrom;
	}

	public void setWorkingUntil(String workingUntil) {
		this.workingUntil = workingUntil;
	}

	public void setOrderingFrom(String orderingFrom) {
		this.orderingFrom = orderingFrom;
	}

	public void setOrderingUntil(String orderingUntil) {
		this.orderingUntil = orderingUntil;
	}

}
