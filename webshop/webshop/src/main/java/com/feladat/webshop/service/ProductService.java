package com.feladat.webshop.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feladat.webshop.entity.Product;
import com.feladat.webshop.exception.IdException;
import com.feladat.webshop.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public Product createOrUpdateProduct(@Valid Product product) {

		return productRepository.save(product);
	}

	public Iterable<Product> getProducts() {
		return productRepository.findAll();
	}

	
	public Product findProductById(Long productId) {

		Optional<Product> result = productRepository.findById(productId);

		Product product = null;

		if (result.isPresent()) {
			product = result.get();
		}else {
			throw new IdException("Could not find product with id '" + productId + "'. It may not exist.");
		}

		return product;
	}

	public void removeProductById(Long productId) {
		productRepository.delete(findProductById(productId));
		
	}

}
