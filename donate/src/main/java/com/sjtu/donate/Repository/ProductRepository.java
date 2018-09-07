package com.sjtu.donate.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sjtu.donate.Entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{
	List<Product> findByAmountGreaterThan(int lowerBound);
	
}
