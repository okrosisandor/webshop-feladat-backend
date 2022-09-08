package com.feladat.webshop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feladat.webshop.entity.CustomerCartItem;
import com.feladat.webshop.entity.Product;
import com.feladat.webshop.entity.User;
import com.feladat.webshop.service.CartService;
import com.feladat.webshop.service.ProductService;
import com.feladat.webshop.service.UserService;

@RestController
@RequestMapping("/carts")
@CrossOrigin
public class CartController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserController userController;

	@PostMapping("/{userId}/{productId}")
	public ResponseEntity<?> addToCart(@PathVariable Long productId, @PathVariable Long userId, @RequestBody CustomerCartItem cart) {
		Product product = productService.findProductById(productId);
		
		if(product.getAvailableInStock() < cart.getQuantity()) {
			cart.setQuantity(product.getAvailableInStock());
		}
		
		product.setReservedQuantity(product.getReservedQuantity() + cart.getQuantity());
		product.setAvailableInStock(product.getAvailableInStock() - cart.getQuantity());
		productService.createOrUpdateProduct(product);
		
		product = productService.findProductById(productId);
		CustomerCartItem cartItem = new CustomerCartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(cart.getQuantity());
		cartItem.setStatus((byte) 0);
		
		cartService.createCartItem(cartItem);
		
		return new ResponseEntity<CustomerCartItem>(cartItem, HttpStatus.CREATED);
	}
	
	@GetMapping("")
	public Iterable<CustomerCartItem> retrieveCustomerCart() {

		return cartService.retrieveItems((byte) 0);
		
	}
	
	@GetMapping("/{cartId}")
	public ResponseEntity<?> getCartItemById(@PathVariable Long cartId){
		
		CustomerCartItem cartItem = cartService.findCartItemById(cartId);
		
		return new ResponseEntity<CustomerCartItem>(cartItem, HttpStatus.OK);
	}
	
	@GetMapping("/purchased")
	public Iterable<CustomerCartItem> retrievePurchasedItems() {

		return cartService.retrieveItems((byte) 1);
		
	}
	
	@PatchMapping("/{cartId}")
	public ResponseEntity<?> updateCartItem(@RequestBody CustomerCartItem cartItem) {

		cartService.createCartItem(cartItem);
		
		return new ResponseEntity<CustomerCartItem>(cartItem, HttpStatus.CREATED);
	}
	
	@PatchMapping("/purchase")
	public ResponseEntity<?> purchase(@RequestBody User user) {

        User updatedUser = userService.updateUser(user, true);
        
        Iterable<CustomerCartItem> cart = cartService.retrieveItems((byte) 0);
        for(CustomerCartItem c : cart) {
        	c.getProduct().setReservedQuantity(c.getProduct().getReservedQuantity() - c.getQuantity());
        }
		
		cartService.purchase();
		
		return new ResponseEntity<String>("Successful purchase", HttpStatus.OK);
	}
	
	@DeleteMapping("/{cartId}")
	public ResponseEntity<?> deleteCartItem(@PathVariable Long cartId){
		
		CustomerCartItem cartItem = cartService.findCartItemById(cartId);
		cartItem.getProduct().setAvailableInStock(cartItem.getProduct().getAvailableInStock() + cartItem.getQuantity());
		cartItem.getProduct().setReservedQuantity(cartItem.getProduct().getReservedQuantity() - cartItem.getQuantity());
		
		cartService.removeCartItemById(cartId);
		
		return new ResponseEntity<String>("CartItem with id '" + cartId + "' has successfully been deleted", HttpStatus.OK);
	}

}
