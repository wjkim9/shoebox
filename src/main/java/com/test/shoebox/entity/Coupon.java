package com.test.shoebox.entity;

import com.test.shoebox.dto.CouponDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coupon_seq_generator")
    @SequenceGenerator(name = "coupon_seq_generator", sequenceName = "coupon_seq", allocationSize = 1)
    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "coupon_name", nullable = false, length = 150)
    private String couponName;

    @Column(name = "discount_rate", nullable = false)
    private Double discountRate;

    @Column(name = "min_price", nullable = false)
    private Integer minPrice;

    @Column(name = "max_discount_price")
    private Integer maxDiscountPrice;



    public CouponDTO toDTO() {
        return CouponDTO.builder()
                .couponId(this.couponId)
                .couponName(this.couponName)
                .discountRate(this.discountRate)
                .minPrice(this.minPrice)
                .maxDiscountPrice(this.maxDiscountPrice)
                .build();
    }
}
