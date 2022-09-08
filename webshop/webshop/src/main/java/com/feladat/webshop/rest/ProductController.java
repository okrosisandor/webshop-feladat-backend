package com.feladat.webshop.rest;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feladat.webshop.entity.Product;
import com.feladat.webshop.service.ProductService;
import com.feladat.webshop.service.ValidationService;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
	
	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private ProductService productService;

	
	@PostMapping("")
//	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult result){
		
		ResponseEntity<?> errors = validationService.validate(result);
		
		if(errors != null) {
			return errors;
		}
		
		if(product.getAvailableInStock() < 1) product.setAvailableInStock(1);
		
		Product newProduct = productService.createOrUpdateProduct(product);
		
		return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
	}
	
	@GetMapping("")
	public Iterable<Product> getAllProducts(){
		return productService.getProducts();
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable Long productId){
		
		Product product = productService.findProductById(productId);
		
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
		productService.removeProductById(productId);
		
		return new ResponseEntity<String>("Product with id '" + productId + "' has successfully been deleted", HttpStatus.OK);
	}
	
	
	
	
}
