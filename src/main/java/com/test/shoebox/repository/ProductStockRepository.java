package com.test.shoebox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long>  {

	List<ProductStock> findByProduct(Product product);

}
