package com.feladat.webshop.utility;

import com.feladat.webshop.entity.User;
import com.feladat.webshop.entity.UserAddress;
import com.feladat.webshop.entity.UserDeliveryAddress;

public class AuthResponse {

	private String email;
	private String firstName;
	private String lastName;
	private UserAddress userAddress;
	private UserDeliveryAddress deliveryAddress;
	
    private String accessToken;
 
    public AuthResponse() { }
     

	public AuthResponse(String email, String firstName, String lastName, UserAddress userAddress,
			UserDeliveryAddress deliveryAddress, String accessToken) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userAddress = userAddress;
		this.deliveryAddress = deliveryAddress;
		this.accessToken = accessToken;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public UserAddress getUserAddress() {
		return userAddress;
	}


	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}


	public UserDeliveryAddress getDeliveryAddress() {
		return deliveryAddress;
	}


	public void setDeliveryAddress(UserDeliveryAddress deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


    
    
}
