package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
