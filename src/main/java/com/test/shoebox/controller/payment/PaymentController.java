package com.test.shoebox.controller.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.test.shoebox.entity.CartItem;
import com.test.shoebox.repository.CartItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Value("${imp.api.key}")
    private String apiKey;

    @GetMapping("/orderlist")
    public String orderlist(Model model, 
            @RequestParam(required = false) List<Long> cartItemIds,
            @RequestParam(required = false) String appliedCoupons,
            @RequestParam(required = false, defaultValue = "0") int usePoint,
            @RequestParam(required = false, defaultValue = "false") boolean useAllPoint) {
        model.addAttribute("impKey", apiKey);
        
        if (cartItemIds != null && !cartItemIds.isEmpty()) {
            Map<String, Object> amounts = paymentService.calculateOrderAmounts(cartItemIds, appliedCoupons, usePoint, useAllPoint);
            
            model.addAttribute("selectedItems", amounts.get("selectedItems"));
            model.addAttribute("totalAmount", amounts.get("totalAmount"));
            model.addAttribute("totalDiscountAmount", amounts.get("totalDiscountAmount"));
            model.addAttribute("pointDiscount", amounts.get("pointDiscount"));
            model.addAttribute("finalAmount", amounts.get("finalAmount"));
        }
        
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

    }
}
