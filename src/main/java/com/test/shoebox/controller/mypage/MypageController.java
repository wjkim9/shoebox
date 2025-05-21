package com.test.shoebox.controller.mypage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.test.shoebox.dto.CouponDTO;
import com.test.shoebox.dto.IssuedCouponDTO;
import com.test.shoebox.service.mypage.CouponService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.test.shoebox.entity.CartItem;
import com.test.shoebox.repository.CartItemRepository;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final CartItemRepository cartItemRepository;
    private final CouponService couponService;        // CouponService 주입
    private final HttpSession session;

    @GetMapping("/api/coupons")
    @ResponseBody
    public List<IssuedCouponDTO> getCoupons(HttpSession session) {
        Long membersId = (Long) session.getAttribute("membersId");
        if (membersId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        return couponService.getAvailableCoupons(membersId);
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        try {
            // 임시로 membersId를 1L로 설정 (테스트용)
            Long tempmembersId = 1L;
            session.setAttribute("membersId", tempmembersId);

            // 세션에서 사용자 ID 가져오기
            Long membersId = (Long) session.getAttribute("membersId");

            List<CartItem> cartItems = cartItemRepository.findByMembersIdWithProductAndImages(membersId);
            model.addAttribute("cartItems", cartItems);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("cartItems", new ArrayList<>());
        }

        return "mypage/cart";
    }

    // 쿠폰 상세 정보 조회 API 추가
    @GetMapping("/api/coupons/{couponId}")
    @ResponseBody
    public CouponDTO getCouponDetail(@PathVariable Long couponId) {
        return couponService.getCouponDetail(couponId);
    }

    // 발급된 쿠폰 상세 조회 API 추가
    @GetMapping("/api/issued-coupons/{issuedCouponId}")
    @ResponseBody
    public IssuedCouponDTO getIssuedCouponDetail(@PathVariable Long issuedCouponId) {
        return couponService.getIssuedCouponDetail(issuedCouponId);
    }

    // 할인 금액 계산 API 추가
    @GetMapping("/api/coupons/{issuedCouponId}/calculate")
    @ResponseBody
    public Map<String, Integer> calculateDiscountAmount(
            @PathVariable Long issuedCouponId,
            @RequestParam int price) {
        int discountAmount = couponService.calculateDiscountAmount(issuedCouponId, price);
        return Map.of(
                "originalPrice", price,
                "discountAmount", discountAmount,
                "finalPrice", price - discountAmount
        );
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {
        return "mypage/mypage";
    }
}