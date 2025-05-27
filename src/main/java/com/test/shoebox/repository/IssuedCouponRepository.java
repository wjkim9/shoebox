package com.test.shoebox.repository;

import com.test.shoebox.entity.IssuedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {
    
	@Query(value = "SELECT ic.issuedcoupon_id, ic.issue_datetime, ic.expire_datetime, " +
		       "ic.members_id, ic.coupon_id, c.coupon_name, " +
		       "c.discount_rate, c.min_price, c.max_discount_price " +
		       "FROM ISSUEDCOUPON ic " +  // 대문자로 테이블명 수정
		       "JOIN COUPON c ON ic.coupon_id = c.coupon_id " +  // 대문자로 테이블명 수정
		       "WHERE ic.members_id = :membersId " +
		       "AND ic.expire_datetime > SYSDATE " +  // Oracle에서는 NOW() 대신 SYSDATE 사용
		       "ORDER BY ic.expire_datetime ASC", 
		       nativeQuery = true)
		List<Object[]> findValidCouponsByMembersId(@Param("membersId") Long membersId);

    List<IssuedCoupon> findByMembers_MembersIdAndExpireDatetimeAfter(
        Long membersId,
        LocalDateTime now
    );

}
