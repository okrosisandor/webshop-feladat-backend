package com.feladat.webshop.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.feladat.webshop.entity.CustomerCartItem;

@Repository
public interface CartItemRepository extends CrudRepository<CustomerCartItem, Long>{

	@Query("SELECT c FROM CustomerCartItem c WHERE c.status = ?1")
	public Iterable<CustomerCartItem> findAll(byte status);
	
	@Modifying
	@Query("UPDATE CustomerCartItem c SET c.status = 1 WHERE c.status = 0")
	public void purchase();
	
}
