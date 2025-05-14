package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.ProductGroup;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {

}
