package com.feladat.webshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class UserDeliveryAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @NotBlank(message = "Country is required")
	private String country;
	
	@NotBlank(message = "City is required")
	private String city;
	
	@NotBlank(message = "Zipcode is required")
	private String zipCode;
	
	@NotBlank(message = "Address is required")
	private String address;
	
	@NotBlank(message = "Phone is required")
    private String phone;

	public UserDeliveryAddress() {
		super();
	}

	public UserDeliveryAddress(@NotBlank(message = "Country is required") String country,
			@NotBlank(message = "City is required") String city,
			@NotBlank(message = "Zipcode is required") String zipCode,
			@NotBlank(message = "Address is required") String address,
			@NotBlank(message = "Phone is required") String phone) {
		super();
		this.country = country;
		this.city = city;
		this.zipCode = zipCode;
		this.address = address;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "UserAddress [id=" + id + ", country=" + country + ", city=" + city + ", zipCode=" + zipCode
				+ ", address=" + address + ", phone=" + phone + "]";
	}

	
	
	
}
