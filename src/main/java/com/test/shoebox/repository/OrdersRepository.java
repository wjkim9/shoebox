package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
