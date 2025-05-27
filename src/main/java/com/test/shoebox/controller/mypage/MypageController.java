package com.test.shoebox.controller.mypage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.test.shoebox.dto.CouponDTO;
import com.test.shoebox.dto.IssuedCouponDTO;
import com.test.shoebox.service.payment.CouponService;
import com.test.shoebox.service.payment.PaymentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.test.shoebox.entity.CartItem;
import com.test.shoebox.repository.CartItemRepository;
import com.test.shoebox.entity.Members;
import com.test.shoebox.repository.MemberGradeRepository;
import com.test.shoebox.service.main.CustomOAuth2User;
import com.test.shoebox.service.payment.CartItemService;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final CartItemRepository cartItemRepository;
    private final CouponService couponService;
    private final HttpSession session;
    private final PaymentService paymentService;
    private final CartItemService cartItemService;

    @GetMapping("/api/coupons")
    @ResponseBody
    public List<Map<String, Object>> getCoupons() {
        Long membersId = (Long) session.getAttribute("membersId");
        if (membersId == null) {
            // 테스트용 회원 ID 사용
            membersId = 1L;
        }
        List<IssuedCouponDTO> issuedCoupons = couponService.getAvailableCoupons(membersId);
        
        // 필요한 정보만 추출하여 반환
        return issuedCoupons.stream()
            .map(issuedCoupon -> {
                Map<String, Object> couponInfo = new HashMap<>();
                couponInfo.put("couponId", issuedCoupon.getIssuedCouponId());
                couponInfo.put("couponName", issuedCoupon.getCoupon().getCouponName());
                couponInfo.put("discountRate", issuedCoupon.getCoupon().getDiscountRate());
                return couponInfo;
            })
            .collect(Collectors.toList());
    }

    @GetMapping("/cart")
    public String cart(Model model, @AuthenticationPrincipal CustomOAuth2User user) {
        try {
        	
            // 연관 엔티티를 함께 로드하는 메서드 사용
            List<CartItem> cartItems = cartItemRepository.findByMembersIdWithProductAndImages(user.getMembersDTO().getMembersId());
            
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }
            model.addAttribute("cartItems", cartItems);

            // 장바구니 아이템들의 ID 리스트 생성
            List<Long> cartItemIds = cartItems.stream()
                .map(CartItem::getCartItemId)
                .toList();

            // PaymentService를 사용하여 금액 계산
            Map<String, Object> amounts = paymentService.calculateOrderAmounts(cartItemIds, null, 0, false);
            
            model.addAttribute("totalAmount", amounts.get("totalAmount"));
            model.addAttribute("totalDiscountAmount", amounts.get("totalDiscountAmount"));
            model.addAttribute("finalAmount", amounts.get("finalAmount"));

            return "mypage/cart";
        } catch (Exception e) {
            e.printStackTrace(); // 에러 로그 출력
            // 에러 발생 시 빈 장바구니로 처리
            model.addAttribute("cartItems", new ArrayList<>());
            model.addAttribute("totalAmount", 0);
            model.addAttribute("totalDiscountAmount", 0);
            model.addAttribute("finalAmount", 0);
            return "mypage/cart";
        }
    }

    // 쿠폰 상세 정보 조회 API
    @GetMapping("/api/coupons/{couponId}")
    @ResponseBody
    public CouponDTO getCouponDetail(@PathVariable Long couponId) {
        return couponService.getCouponDetail(couponId);
    }

    // 발급된 쿠폰 상세 조회 API
    @GetMapping("/api/issued-coupons/{issuedCouponId}")
    @ResponseBody
    public IssuedCouponDTO getIssuedCouponDetail(@PathVariable Long issuedCouponId) {
        return couponService.getIssuedCouponDetail(issuedCouponId);
    }

    // 장바구니 금액 계산 API
    @PostMapping("/api/cart/calculate")
    @ResponseBody
    public Map<String, Object> calculateCartAmount(
            @RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Long> cartItemIds = (List<Long>) request.get("cartItemIds");
        String appliedCoupons = (String) request.get("appliedCoupons");
        Integer usePoint = (Integer) request.get("usePoint");
        Boolean useAllPoint = (Boolean) request.get("useAllPoint");

        return paymentService.calculateOrderAmounts(
            cartItemIds,
            appliedCoupons,
            usePoint != null ? usePoint : 0,
            useAllPoint != null ? useAllPoint : false
        );
    }

    @PostMapping("/api/cart/quantity")
    @ResponseBody
    public ResponseEntity<?> updateCartItemQuantity(@RequestBody Map<String, Object> request) {
        try {
            Long cartItemId = Long.parseLong(request.get("cartItemId").toString());
            Integer quantity = Integer.parseInt(request.get("quantity").toString());
            
            cartItemService.updateQuantity(cartItemId, quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("수량 변경 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {
        return "mypage/mypage";
    }
}
