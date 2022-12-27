package com.feladat.webshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feladat.webshop.entity.CustomerCartItem;
import com.feladat.webshop.repository.CartItemRepository;
import com.feladat.webshop.repository.ProductRepository;

@Service
public class CartService {
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepository productRepository;

	public CustomerCartItem createCartItem(CustomerCartItem cartItem) {
		
		return cartItemRepository.save(cartItem);
	}

	public Iterable<CustomerCartItem> retrieveItems(byte status) {
		return cartItemRepository.findAll(status);
	}
	
	public Iterable<CustomerCartItem> getAllItems() {
		return cartItemRepository.findAll();
	}

	public CustomerCartItem findCartItemById(Long cartId) {
		Optional<CustomerCartItem> result = cartItemRepository.findById(cartId);

		CustomerCartItem cartItem = null;

		if (result.isPresent()) {
			cartItem = result.get();
		}

		return cartItem;
	}

	public void removeCartItemById(Long cartId) {
		cartItemRepository.delete(findCartItemById(cartId));
	}
	
	
	@Transactional
	public void purchase() {
		cartItemRepository.purchase();
	}
	
	public CustomerCartItem findCartItemByProductId(Long productId) {
		Optional<CustomerCartItem> result = cartItemRepository.findById(productId);

		CustomerCartItem cartItem = null;

		if (result.isPresent()) {
			cartItem = result.get();
		}

		return cartItem;
	}

}
