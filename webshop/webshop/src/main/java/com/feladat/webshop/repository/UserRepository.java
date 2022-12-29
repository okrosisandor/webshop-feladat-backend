package com.feladat.webshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.feladat.webshop.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

//	User findByEmail(String email);
//
//	User getById(Integer id);
	
	Optional<User> findByEmail(String email);

	Optional<User> findById(Integer userId);

	User getById(Integer integer);

	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByUsername(String username);
}
