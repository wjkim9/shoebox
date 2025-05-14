package com.test.shoebox.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class CouponDTO {
	private Long couponId;
	private String couponName;
	private Double discountRate;
	private Integer minPrice;
	private Integer maxDiscountPrice;

}
