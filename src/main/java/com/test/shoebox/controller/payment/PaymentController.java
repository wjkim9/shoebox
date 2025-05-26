package com.test.shoebox.controller.payment;

import com.test.shoebox.entity.MemberAddress;
import com.test.shoebox.entity.Orders;
import com.test.shoebox.repository.MemberAddressRepository;
import com.test.shoebox.repository.OrdersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.test.shoebox.service.payment.PaymentService;
import com.test.shoebox.repository.CartItemRepository;
import com.test.shoebox.repository.MembersRepository;
import com.test.shoebox.entity.Members;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    
    private final PaymentService paymentService;
    private final CartItemRepository cartItemRepository;
    private final MembersRepository membersRepository;
    private final OrdersRepository ordersRepository;
    private final MemberAddressRepository memberAddressRepository;

    @Value("${imp.api.key}")
    private String apiKey;

    @GetMapping("/orderlist")
    public String getOrderList(
            @RequestParam List<Long> cartItemIds,
            @RequestParam(required = false) String appliedCoupons,
            @RequestParam(defaultValue = "0") int usePoint,
            @RequestParam(defaultValue = "false") boolean useAllPoint,
            Model model) {
        
        // 임시로 첫 번째 회원 정보 가져오기 (나중에 로그인 구현 후 수정 필요)
        Members currentMember = membersRepository.findAll().stream().findFirst().orElse(null);
        if (currentMember == null) {
            return "redirect:/login";
        }

        // 회원 정보를 모델에 추가
        model.addAttribute("member", Map.of(
            "name", currentMember.getName(),
            "phone", currentMember.getContact(),
            "email", currentMember.getEmail(),
                "point", currentMember.getPoint()
        ));

        // 기본 배송지 정보를 모델에 추가
        MemberAddress memberAddress = memberAddressRepository.findAll().stream()
                .filter(order -> order.getMembers().getMembersId().equals(currentMember.getMembersId()))
                .findFirst()
                .orElse(null);
        model.addAttribute("defaultAddress", Map.of(
            "name", currentMember.getName(),
            "phone", currentMember.getContact(),
            "zipcode", memberAddress.getZipCode(), // 우편번호
            "address", memberAddress.getRoadAddress(), // 주소
            "detailAddress", memberAddress.getDetailAddress() // 상세주소
        ));

        // 최근 배송지 정보 모델에 추가
        Orders orders = ordersRepository.findAll().stream()
                .filter(order -> order.getMembers().getMembersId().equals(currentMember.getMembersId()))
                .sorted((o1, o2) -> o2.getOrdersDate().compareTo(o1.getOrdersDate())) // 내림차순 정렬
                .findFirst()
                .orElse(null);
        model.addAttribute("recentAddress", Map.of(
                "name", orders.getReceiverName(),
                "phone", orders.getReceiverContact(),
                "zipcode", orders.getDestinationZipCode(), // 임시 우편번호
                "address", orders.getDestinationRoadAddress(), // 임시 주소
                "detailAddress", orders.getDestinationDetailAddress() // 임시 상세주소
        ));



        // 주문 금액 계산
        Map<String, Object> amounts = paymentService.calculateOrderAmounts(
            cartItemIds, appliedCoupons, usePoint, useAllPoint);
        
        // 각각의 값을 모델에 추가
        model.addAttribute("selectedItems", amounts.get("selectedItems"));
        model.addAttribute("totalAmount", amounts.get("totalAmount"));
        model.addAttribute("totalDiscountAmount", amounts.get("totalDiscountAmount"));
        model.addAttribute("finalAmount", amounts.get("finalAmount"));
        
        return "payment/orderlist";
    }

    @GetMapping("/payment_completed")
    public String payment_completed(Model model) {
        return "payment/payment_completed";
    }

    @GetMapping("/nonuser_order_terms")
    public String nonuser_order_terms(Model model) {
        return "payment/nonuser_order_terms";
    }

    @RestController
    @RequestMapping("/api/v1/payments")
    public class PaymentApiController {

        @GetMapping("/verify/{imp_uid}")
        public ResponseEntity<?> verifyPayment(
                @PathVariable String imp_uid,
                @RequestParam String merchant_uid,
                @RequestParam BigDecimal amount) {
            try {
                // 수정: .getResponse() 호출 제거
                Payment payment = paymentService.verifyPayment(imp_uid, merchant_uid, amount);
                return ResponseEntity.ok(payment);
            } catch (Exception e) {
                log.error("결제 검증 중 오류 발생", e);
                return ResponseEntity.badRequest().body("결제 검증 중 오류가 발생했습니다: " + e.getMessage());
            }
        }

        @GetMapping("/status/{imp_uid}")
        public ResponseEntity<?> getPaymentStatus(@PathVariable String imp_uid) {
            try {
                // 수정: .getResponse() 호출 제거
                Payment payment = paymentService.getPaymentStatus(imp_uid);
                return ResponseEntity.ok(payment);
            } catch (Exception e) {
                log.error("결제 상태 조회 중 오류 발생", e);
                return ResponseEntity.badRequest().body("결제 상태 조회 중 오류가 발생했습니다: " + e.getMessage());
            }
        }

        @PostMapping("/verify")
        public ResponseEntity<?> verifyPayment(@RequestBody Map<String, Object> request) {
            try {
                String impUid = (String) request.get("imp_uid");
                String merchantUid = (String) request.get("merchant_uid");
                BigDecimal amount = new BigDecimal(request.get("amount").toString());
                Map<String, Object> orderData = (Map<String, Object>) request.get("orderData");

                // 결제 검증
                Payment payment = paymentService.verifyPayment(impUid, merchantUid, amount);
                
                // 결제 금액 검증
                if (payment.getAmount().compareTo(amount) != 0) {
                    throw new RuntimeException("결제 금액이 일치하지 않습니다.");
                }

                // 주문 처리
                paymentService.processOrderCompletion(impUid, merchantUid, amount, orderData);

                return ResponseEntity.ok(Map.of("status", "success"));
            } catch (Exception e) {
                log.error("결제 검증 중 오류 발생", e);
                return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
                ));
            }
        }

    }
}
