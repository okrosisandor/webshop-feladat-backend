package com.feladat.webshop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feladat.webshop.entity.CustomerCartItem;
import com.feladat.webshop.repository.CartItemRepository;

@Service
public class CartService {
	
	@Autowired
	private CartItemRepository cartItemRepository;

	public CustomerCartItem createCartItem(CustomerCartItem cartItem) {

		return cartItemRepository.save(cartItem);
	}

	public Iterable<CustomerCartItem> retrieveItems(byte status) {
		return cartItemRepository.findAll(status);
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

}
