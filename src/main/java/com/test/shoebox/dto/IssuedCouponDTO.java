package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.Coupon;
import com.test.shoebox.entity.IssuedCoupon;
import com.test.shoebox.entity.Members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssuedCouponDTO {
    private Long issuedCouponId;

    private LocalDateTime issueDatetime;

    private LocalDateTime expireDatetime;

    private Long couponId;

    private Long membersId;
    
    private CouponDTO coupon;

    public IssuedCoupon toEntity(Members members, Coupon coupon) {
        return IssuedCoupon.builder()
                .issuedCouponId(this.issuedCouponId)
                .issueDatetime(this.issueDatetime)
                .expireDatetime(this.expireDatetime)
                .members(members)
                .coupon(coupon)
                .build();
    }
    //FIXME
    // 쿠폰 정보 (조인용)
    private String couponName;
    private Double discountRate;
    private Integer minPrice;
    private Integer maxDiscountPrice;
}
