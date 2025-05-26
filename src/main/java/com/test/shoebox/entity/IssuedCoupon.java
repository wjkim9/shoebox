package com.test.shoebox.entity;

import java.time.LocalDateTime;

import com.test.shoebox.dto.IssuedCouponDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "IssuedCoupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issuedcoupon_seq_generator")
    @SequenceGenerator(name = "issuedcoupon_seq_generator", sequenceName = "issuedcoupon_seq", allocationSize = 1)
    @Column(name = "issuedcoupon_id")
    private Long issuedCouponId;

    @Column(name = "issue_datetime", nullable = false)
    private LocalDateTime issueDatetime;

    @Column(name = "expire_datetime", nullable = false)
    private LocalDateTime expireDatetime;

    @ManyToOne
    @JoinColumn(name = "members_id", nullable = false)
    private Members members;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    public IssuedCouponDTO toDTO() {
        return IssuedCouponDTO.builder()
                .issuedCouponId(this.issuedCouponId)
                .issueDatetime(this.issueDatetime)
                .expireDatetime(this.expireDatetime)
                .membersId(this.members.getMembersId())
                .couponId(this.coupon.getCouponId())
                .coupon(this.coupon.toDTO())
                .build();
    }
    
    @PrePersist
    public void prePersist() {
        if (this.issueDatetime == null) {
            this.issueDatetime = LocalDateTime.now();
        }
        if (this.expireDatetime == null) {
            this.expireDatetime = LocalDateTime.now().plusDays(30);
        }
    }

}


