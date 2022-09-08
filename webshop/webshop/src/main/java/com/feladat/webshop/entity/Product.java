package com.feladat.webshop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Product name is required")
	private String name;
	
	@Min(value = 1, message = "Price must be greater than 0")
	private double price;
	
//	@Lob
	private String image;
	
	@NotBlank(message = "Product description is required")
	private String description;
	
	@Min(value = 0, message = "Number of available items must be specified")
	private int availableInStock;
	
	private int reservedQuantity;

	public Product() {
		super();
	}

	public Product(@NotBlank(message = "Product name is required") String name,
			@Min(value = 0, message = "Price must not be below 0") double price,
			@NotBlank(message = "Product description is required") String description,
			@Min(value = 0, message = "Number of available items must be specified") int availableInStock) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.availableInStock = availableInStock;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAvailableInStock() {
		return availableInStock;
	}

	public void setAvailableInStock(int availableInStock) {
		this.availableInStock = availableInStock;
	}

	public int getReservedQuantity() {
		return reservedQuantity;
	}

	public void setReservedQuantity(int reservedQuantity) {
		this.reservedQuantity = reservedQuantity;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description
				+ ", availableInStock=" + availableInStock + ", reservedQuantity=" + reservedQuantity + "]";
	}

	
	
	

}
