package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.OrderReview;

public interface OrderReviewRepository extends JpaRepository<OrderReview, Long> {

}
