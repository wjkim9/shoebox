package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.IssuedCoupon;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long>{

}
