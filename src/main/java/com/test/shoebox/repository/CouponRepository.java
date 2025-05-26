package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.shoebox.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}

