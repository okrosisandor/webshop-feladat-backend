package com.feladat.webshop.rest;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feladat.webshop.entity.User;
import com.feladat.webshop.entity.UserHelper;
import com.feladat.webshop.service.UserService;
import com.feladat.webshop.service.ValidationService;
import com.feladat.webshop.utility.AuthRequest;
import com.feladat.webshop.utility.AuthResponse;
import com.feladat.webshop.utility.JwtTokenUtil;
import com.feladat.webshop.validator.UserValidator;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private ValidationService validationService;
	
	@Autowired
	AuthenticationManager authManager;
	
    @Autowired
    JwtTokenUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request, BindingResult result) {
    	ResponseEntity<?> errorMap = validationService.validate(result);
        if(errorMap != null) return errorMap;
    	
    	
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );
             
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getEmail(), user.getFirstName(), user.getLastName(), user.getUserAddress(), user.getUserDeliveryAddress(), accessToken);
             
            return ResponseEntity.ok().body(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {

    	// Validate passwords match
    	userValidator.validate(user, result);

        ResponseEntity<?> errorMap = validationService.validate(result);
        if(errorMap != null) return errorMap;

        User newUser = userService.saveUser(user);

        return  new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }
    
	
	@PatchMapping("")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result){
		
		
    	BindingResult newBindingResult = new BeanPropertyBindingResult(user, "user");

    	if((user.getPassword() != null) && (user.getConfirmPassword() != null) && (user.getPassword().length() > 0 || user.getConfirmPassword().length() > 0)) {
    		userValidator.validate(user, result);
        	
        	ResponseEntity<?> errorMap = validationService.validate(newBindingResult);
            if(errorMap != null) return errorMap;
    	}

        User updatedUser = userService.updateUser(user, false);

        return  new ResponseEntity<User>(updatedUser, HttpStatus.OK);    
    }
	
	@GetMapping("/user/{username}")
	public ResponseEntity<?> getUser(@PathVariable String username){
		
		User user = userService.getUserByName(username);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("/validate")
	public boolean validateUser(@RequestBody UserHelper helper, Principal principal){
		
		System.out.println(helper);
		
		boolean passwordsMatch = userService.validatePassword(helper, principal.getName());
		
		return passwordsMatch;
	}
	
	

}
