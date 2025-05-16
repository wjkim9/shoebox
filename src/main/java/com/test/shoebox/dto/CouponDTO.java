package com.test.shoebox.dto;

import com.test.shoebox.entity.Coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponDTO {
    private Long couponId;
    private String couponName;
    private Double discountRate;
    private Integer minPrice;
    private Integer maxDiscountPrice;

    public Coupon toEntity() {
        return Coupon.builder()
                .couponId(this.couponId)
                .couponName(this.couponName)
                .discountRate(this.discountRate)
                .minPrice(this.minPrice)
                .maxDiscountPrice(this.maxDiscountPrice)
                .build();
    }
}
