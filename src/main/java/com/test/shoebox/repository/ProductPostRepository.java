package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.ProductPost;

public interface ProductPostRepository extends JpaRepository<ProductPost, Long> {
	
}
