package com.feladat.webshop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feladat.webshop.entity.Role;
import com.feladat.webshop.entity.User;
import com.feladat.webshop.entity.UserDeliveryAddress;
import com.feladat.webshop.exception.UsernameAlreadyExistsException;
import com.feladat.webshop.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User updateUser(User user, boolean settingDeliveryDate) {
        	
        	Optional<User> result = userRepository.findByEmail(user.getEmail());

    		User updated = null;

    		if (result.isPresent()) {
    			updated = result.get();
    		}else {
    			System.out.println("Error");
    		}

        	if(settingDeliveryDate) {
        		updateDeliveryAddress(user, updated);
        	}else {
        		updateProperties(user, updated);
        	}
        	
        	if(user.getPassword().length() > 5) {
        		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        		updated.setPassword(passwordEncoder.encode(user.getPassword()));
        	}

            return userRepository.save(updated);
	}

	public void updateProperties(User user, User updated) {
		if (user.getFirstName() != "") {
			updated.setFirstName(user.getFirstName());
		}

		if (user.getLastName() != "") {
			updated.setLastName(user.getLastName());
		}

		if (user.getPassword() != "") {
			updated.setConfirmPassword("");
		}

		updateUserAddress(user, updated);
	}

	public void updateUserAddress(User user, User updated) {

		
		if (user.getUserAddress().getCountry() != "") {
			updated.getUserAddress().setCountry(user.getUserAddress().getCountry());
		}

		if (user.getUserAddress().getCity() != "") {
			updated.getUserAddress().setCity(user.getUserAddress().getCity());
		}

		if (user.getUserAddress().getZipCode() != "") {
			updated.getUserAddress().setZipCode(user.getUserAddress().getZipCode());
		}

		if (user.getUserAddress().getAddress() != "") {
			updated.getUserAddress().setAddress(user.getUserAddress().getAddress());
		}

		if (user.getUserAddress().getPhone() != "") {
			updated.getUserAddress().setPhone(user.getUserAddress().getPhone());
		}
	}

	public void updateDeliveryAddress(User user, User updated) {
		
		if (user.getUserDeliveryAddress().getCountry() != "") {
			updated.getUserDeliveryAddress().setCountry(user.getUserDeliveryAddress().getCountry());
		}

		if (user.getUserDeliveryAddress().getCity() != "") {
			updated.getUserDeliveryAddress().setCity(user.getUserDeliveryAddress().getCity());
		}

		if (user.getUserDeliveryAddress().getZipCode() != "") {
			updated.getUserDeliveryAddress().setZipCode(user.getUserDeliveryAddress().getZipCode());
		}

		if (user.getUserDeliveryAddress().getAddress() != "") {
			updated.getUserDeliveryAddress().setAddress(user.getUserDeliveryAddress().getAddress());
		}

		if (user.getUserDeliveryAddress().getPhone() != "") {
			updated.getUserDeliveryAddress().setPhone(user.getUserDeliveryAddress().getPhone());
		}
	}

	public User getUserById(Long userId) {
//		return userRepository.getById(userId);
		return null;
	}

	public User saveUser(User newUser) {
		try {
			
			UserDeliveryAddress deliveryAddress = new UserDeliveryAddress();
			deliveryAddress.setCountry(newUser.getUserAddress().getCountry());
			deliveryAddress.setCity(newUser.getUserAddress().getCity());
			deliveryAddress.setZipCode(newUser.getUserAddress().getZipCode());
			deliveryAddress.setAddress(newUser.getUserAddress().getAddress());
			deliveryAddress.setPhone(newUser.getUserAddress().getPhone());
			
			newUser.setUserDeliveryAddress(deliveryAddress);


			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
			newUser.setConfirmPassword("");
			
			newUser = userRepository.save(newUser);

			newUser.addRole(new Role(1));

			if (newUser.getSelectedRole().equalsIgnoreCase("admin")) {
				newUser.addRole(new Role(2));
			}
			
			System.out.println(newUser.getUsername());
			
			return userRepository.save(newUser);

		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
		}
	}

	public User getUserByName(String name) {
		
		Optional<User> result = userRepository.findByEmail(name);

		User user = null;

		if (result.isPresent()) {
			user = result.get();
		}else {
			System.out.println("Error");
		}
		
		return user;
	}
}
