package com.feladat.webshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feladat.webshop.entity.CustomerCartItem;
import com.feladat.webshop.entity.Product;
import com.feladat.webshop.entity.User;
import com.feladat.webshop.repository.CartItemRepository;
import com.feladat.webshop.repository.ProductRepository;
import com.feladat.webshop.repository.UserRepository;

@Service
public class CartService {
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductService productService;

	public CustomerCartItem createCartItem(CustomerCartItem cartItem, String username) {
		
//			User user = userRepository.findByUsername(username);
//			cartItem.setUser(user);
//			cartItem.setUsername(user.getEmail());
		
		System.out.println("Id------------------------------------------------------");
		System.out.println(cartItem.getId());
		System.out.println("Id------------------------------------------------------");
			
			if(cartItem.getId() != null) {
				
				CustomerCartItem existingItem = null;

				Optional<CustomerCartItem> res = cartItemRepository.findById(cartItem.getId());
				
				if(res.isPresent()) {
					existingItem = res.get();
				}
				
				if(existingItem.getUsername().equals(username)) {
				
					Product product = productService.findProductById(existingItem.getProduct().getId());
				
					//	To find out how the amount has been changed in the existing cart item
				
					int difference = cartItem.getQuantity() - existingItem.getQuantity();
				
					product.setAvailableInStock(product.getAvailableInStock() - difference);
				
					productService.createOrUpdateProduct(product);
					
					cartItem.setUser(existingItem.getUser());
					cartItem.setUsername(existingItem.getUsername());
				}
			}else {
				User user = userRepository.findByUsername(username);
				cartItem.setUser(user);
				cartItem.setUsername(user.getEmail());
			}
		
		return cartItemRepository.save(cartItem);
	}

	public Iterable<CustomerCartItem> retrieveItems(byte status, String username) {
		return cartItemRepository.findAll(status, username);
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
	public void purchase(String username) {
		cartItemRepository.purchase(username);
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
