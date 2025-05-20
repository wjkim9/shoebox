package com.test.shoebox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

	List<ProductImage> findByProductOrderBySortOrderAsc(Product product);

}
