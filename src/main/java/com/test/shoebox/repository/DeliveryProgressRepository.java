package com.test.shoebox.repository;

import com.test.shoebox.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.DeliveryProgress;

public interface DeliveryProgressRepository extends JpaRepository<DeliveryProgress, Long>{

    DeliveryProgress findByOrders(Orders order);
}
