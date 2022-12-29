package com.feladat.webshop.rest;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
	public ResponseEntity<?> addToCart(@PathVariable Long productId, @PathVariable Long userId, @RequestBody CustomerCartItem cart, Principal principal) {
		
		Product product = productService.findProductById(productId);
		
		if(product.getAvailableInStock() < cart.getQuantity()) {
			cart.setQuantity(product.getAvailableInStock());
		}
		
		product.setReservedQuantity(product.getReservedQuantity() + cart.getQuantity());
		product.setAvailableInStock(product.getAvailableInStock() - cart.getQuantity());
		productService.createOrUpdateProduct(product);
		
		
//		Checking to see if the product is already in the cart and set accordingly
		
		Iterable<CustomerCartItem> carts = cartService.retrieveItems((byte) 0, principal.getName());
		
   		List<CustomerCartItem> cartsList = StreamSupport.stream(carts.spliterator(), false).collect(Collectors.toList());
   		
   		for(CustomerCartItem theCart : cartsList) {
   			
   				if(theCart.getProduct().getId().equals(productId)) {
   					
   					theCart.setQuantity(theCart.getQuantity() + cart.getQuantity());
   					cartService.createCartItem(theCart, principal.getName());
   					
   					return new ResponseEntity<CustomerCartItem>(theCart, HttpStatus.CREATED);
   				}
   		}
   		
//   	If product not in the cart yet
		
		product = productService.findProductById(productId);
		CustomerCartItem cartItem = new CustomerCartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(cart.getQuantity());
		cartItem.setStatus((byte) 0);
		
		cartService.createCartItem(cartItem, principal.getName());
		
		return new ResponseEntity<CustomerCartItem>(cartItem, HttpStatus.CREATED);
	}
	
	@GetMapping("")
	public Iterable<CustomerCartItem> retrieveCustomerCart(Principal principal) {

		return cartService.retrieveItems((byte) 0, principal.getName());
	}
	
	@GetMapping("/{cartId}")
	public ResponseEntity<?> getCartItemById(@PathVariable Long cartId){
		
		CustomerCartItem cartItem = cartService.findCartItemById(cartId);
		
		return new ResponseEntity<CustomerCartItem>(cartItem, HttpStatus.OK);
	}
	
	@GetMapping("/purchased")
	public Iterable<CustomerCartItem> retrievePurchasedItems(Principal principal) {

		return cartService.retrieveItems((byte) 1, principal.getName());
		
	}
	
	@PatchMapping("/{cartId}")
	public ResponseEntity<?> updateCartItem(@RequestBody CustomerCartItem cartItem, Principal principal) {

		cartService.createCartItem(cartItem, principal.getName());
		
		return new ResponseEntity<CustomerCartItem>(cartItem, HttpStatus.CREATED);
	}
	
	@PatchMapping("/purchase")
	public ResponseEntity<?> purchase(@RequestBody User user, Principal principal) {

        User updatedUser = userService.updateUser(user, true);
        
        Iterable<CustomerCartItem> cart = cartService.retrieveItems((byte) 0, principal.getName());
        
        for(CustomerCartItem c : cart) {
        	c.getProduct().setReservedQuantity(c.getProduct().getReservedQuantity() - c.getQuantity());
        }
		
		cartService.purchase(principal.getName());
		
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
