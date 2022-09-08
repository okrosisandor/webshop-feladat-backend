package com.feladat.webshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.feladat.webshop.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
	

}
