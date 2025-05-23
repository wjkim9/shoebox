package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.IssuedCoupon;

import java.time.LocalDateTime;
import java.util.List;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long>{

    List<IssuedCoupon> findByMembers_MembersIdAndExpireDatetimeAfter(
        Long membersId,
        LocalDateTime now
    );

}
