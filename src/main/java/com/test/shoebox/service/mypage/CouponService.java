package com.test.shoebox.service.mypage;

import com.test.shoebox.dto.CouponDTO;
import com.test.shoebox.dto.IssuedCouponDTO;
import com.test.shoebox.entity.Coupon;
import com.test.shoebox.entity.IssuedCoupon;
import com.test.shoebox.entity.Members;
import com.test.shoebox.repository.CouponRepository;
import com.test.shoebox.repository.IssuedCouponRepository;
import com.test.shoebox.repository.MembersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final IssuedCouponRepository issuedCouponRepository;
    private final CouponRepository couponRepository;
    private final MembersRepository membersRepository;

    // 사용 가능한 발급 쿠폰 목록 조회
    public List<IssuedCouponDTO> getAvailableCoupons(Long membersId) {
        List<IssuedCoupon> issuedCoupons = issuedCouponRepository.findByMembers_MembersIdAndExpireDatetimeAfter(
            membersId,
            LocalDateTime.now()
        );
        return issuedCoupons.stream()
            .map(IssuedCoupon::toDTO)
            .collect(Collectors.toList());
    }

    // 쿠폰 발급
    public IssuedCouponDTO issueCoupon(Long membersId, Long couponId) {
        Members member = membersRepository.findById(membersId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("쿠폰을 찾을 수 없습니다."));

        IssuedCoupon issuedCoupon = IssuedCoupon.builder()
                .members(member)
                .coupon(coupon)
                .issueDatetime(LocalDateTime.now())
                .expireDatetime(LocalDateTime.now().plusDays(30)) // 기본 30일
                .build();

        return issuedCouponRepository.save(issuedCoupon).toDTO();
    }

    // 쿠폰 상세 정보 조회
    public CouponDTO getCouponDetail(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("쿠폰을 찾을 수 없습니다."));
        return coupon.toDTO();
    }

    // 발급된 쿠폰 상세 조회
    public IssuedCouponDTO getIssuedCouponDetail(Long issuedCouponId) {
        IssuedCoupon issuedCoupon = issuedCouponRepository.findById(issuedCouponId)
                .orElseThrow(() -> new RuntimeException("발급된 쿠폰을 찾을 수 없습니다."));
        return issuedCoupon.toDTO();
    }

    // 할인 금액 계산
    public int calculateDiscountAmount(Long issuedCouponId, int originalPrice) {
        IssuedCoupon issuedCoupon = issuedCouponRepository.findById(issuedCouponId)
                .orElseThrow(() -> new RuntimeException("발급된 쿠폰을 찾을 수 없습니다."));

        Coupon coupon = issuedCoupon.getCoupon();

        // 최소 구매 금액 체크
        if (originalPrice < coupon.getMinPrice()) {
            throw new RuntimeException("최소 구매 금액을 충족하지 않습니다.");
        }

        // 할인 금액 계산
        int discountAmount = (int) (originalPrice * (coupon.getDiscountRate() / 100.0));

        // 최대 할인 금액 제한
        if (coupon.getMaxDiscountPrice() != null) {
            discountAmount = Math.min(discountAmount, coupon.getMaxDiscountPrice());
        }

        return discountAmount;
    }

    //쿠폰 사용 메서드
    @Transactional
    public void useCoupon(Long issuedCouponId) {
        issuedCouponRepository.deleteById(issuedCouponId);
    }


}
