package com.test.shoebox.service.payment;

import com.test.shoebox.dto.CouponDTO;
import com.test.shoebox.dto.IssuedCouponDTO;
import com.test.shoebox.entity.Coupon;
import com.test.shoebox.entity.IssuedCoupon;
import com.test.shoebox.entity.Members;
import com.test.shoebox.repository.CouponRepository;
import com.test.shoebox.repository.IssuedCouponRepository;
import com.test.shoebox.repository.MemberGradeRepository;
import com.test.shoebox.repository.MembersRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {
    private final IssuedCouponRepository issuedCouponRepository;
    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public List<IssuedCouponDTO> getAvailableCoupons(Long membersId) {
        List<IssuedCoupon> issuedCoupons = issuedCouponRepository.findByMembers_MembersIdAndExpireDatetimeAfter(
            membersId,
            LocalDateTime.now()
        );
        return issuedCoupons.stream()
            .map(IssuedCoupon::toDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CouponDTO getCouponDetail(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("쿠폰을 찾을 수 없습니다."));
        return coupon.toDTO();
    }

    @Transactional(readOnly = true)
    public IssuedCouponDTO getIssuedCouponDetail(Long issuedCouponId) {
        IssuedCoupon issuedCoupon = issuedCouponRepository.findById(issuedCouponId)
                .orElseThrow(() -> new RuntimeException("발급된 쿠폰을 찾을 수 없습니다."));
        return issuedCoupon.toDTO();
    }
}
