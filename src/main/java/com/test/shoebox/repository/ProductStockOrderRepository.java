package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.ProductStockOrder;

public interface ProductStockOrderRepository extends JpaRepository<ProductStockOrder, Long> {

}
