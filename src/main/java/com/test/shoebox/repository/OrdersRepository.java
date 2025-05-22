package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Orders;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByOrderByOrdersDateDesc();
}
