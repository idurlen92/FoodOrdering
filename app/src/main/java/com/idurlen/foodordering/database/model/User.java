package com.idurlen.foodordering.database.model;

/**
 * @author Ivan Durlen
 */
public class User {


	private int id;

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String birthDate;
	private String city;
	private String address;



	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword(){
		return password;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public String getCity() {
		return city;
	}

	public String getAddress() {
		return address;
	}



	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setAddress(String address) {
		this.address = address;
	}



}
