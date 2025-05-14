package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long>  {

}
