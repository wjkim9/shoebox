package com.test.shoebox.repository;

import com.test.shoebox.dto.ProductStockOrderDTO;
import com.test.shoebox.entity.Orders;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.ProductStockOrder;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ProductStockOrderRepository extends JpaRepository<ProductStockOrder, Long> {

    List<ProductStockOrder> findByOrders(Orders order);

}
