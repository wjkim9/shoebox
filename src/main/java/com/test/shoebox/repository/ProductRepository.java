package com.test.shoebox.repository;

import com.test.shoebox.dto.ProductSearchCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> searchByCondition(ProductSearchCondition condition);
}

