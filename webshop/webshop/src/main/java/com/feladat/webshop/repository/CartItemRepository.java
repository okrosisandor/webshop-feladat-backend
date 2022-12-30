package com.feladat.webshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.feladat.webshop.entity.CustomerCartItem;

@Repository
public interface CartItemRepository extends CrudRepository<CustomerCartItem, Long>{

	@Query("SELECT c FROM CustomerCartItem c WHERE c.status = ?1 and c.username = ?2")
	public Iterable<CustomerCartItem> findAll(byte status, String username);
	
	@Modifying
	@Query("UPDATE CustomerCartItem c SET c.status = 1 WHERE c.status = 0 and c.username = ?1")
	public void purchase(String username);

	@Modifying
	@Query("delete from CustomerCartItem c where c.id = ?1")
	public void removeById(Long cartId);
	
}
