package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

