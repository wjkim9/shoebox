package com.test.shoebox.service.payment;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.test.shoebox.entity.CartItem;
import com.test.shoebox.repository.CartItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final CartItemRepository cartItemRepository;

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secret}")
    private String apiSecret;

    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> calculateOrderAmounts(List<Long> cartItemIds, String appliedCoupons, int usePoint, boolean useAllPoint) {
        List<CartItem> selectedItems = cartItemRepository.findAllById(cartItemIds);
        
        // 총 주문 금액 계산
        int totalAmount = selectedItems.stream()
            .mapToInt(item -> item.getProductStock().getProduct().getProductPrice() * item.getQuantity())
            .sum();
        
        // 총 할인 금액 계산
        int totalDiscountAmount = selectedItems.stream()
            .mapToInt(item -> {
                int itemPrice = item.getProductStock().getProduct().getProductPrice() * item.getQuantity();
                int productDiscount = (int) (itemPrice * (item.getProductStock().getProduct().getDiscountRate() / 100.0));
                
                // 쿠폰 할인 계산
                int couponDiscount = 0;
                if (appliedCoupons != null && !appliedCoupons.isEmpty()) {
                    try {
                        Map<Long, List<Map<String, Object>>> allAppliedCoupons = new ObjectMapper().readValue(appliedCoupons, Map.class);
                        List<Map<String, Object>> itemCoupons = allAppliedCoupons.get(item.getCartItemId());
                        if (itemCoupons != null) {
                            for (Map<String, Object> coupon : itemCoupons) {
                                double discountRate = Double.parseDouble(coupon.get("discountRate").toString());
                                couponDiscount += (int) (itemPrice * (discountRate / 100.0));
                            }
                        }
                    } catch (Exception e) {
                        log.error("쿠폰 할인 계산 중 오류 발생", e);
                    }
                }
                
                return productDiscount + couponDiscount;
            })
            .sum();
        
        // 포인트 사용 금액 계산
        int pointDiscount = usePoint;
        if (useAllPoint) {
            // TODO: 사용자의 전체 포인트 조회 로직 구현 필요
            // pointDiscount = memberService.getTotalPoint();
        }
        
        // 결제 예정 금액 계산 (포인트 할인 포함)
        int finalAmount = totalAmount - totalDiscountAmount - pointDiscount;
        
        return Map.of(
            "totalAmount", totalAmount,
            "totalDiscountAmount", totalDiscountAmount,
            "pointDiscount", pointDiscount,
            "finalAmount", finalAmount,
            "selectedItems", selectedItems
        );
    }

    public Payment verifyPayment(String impUid, String merchantUid, BigDecimal amount) {
        try {
            IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(impUid);
            Payment payment = paymentResponse.getResponse();

            if (payment.getAmount().compareTo(amount) != 0) {
                throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
            }
            if (!payment.getMerchantUid().equals(merchantUid)) {
                throw new IllegalArgumentException("주문 번호가 일치하지 않습니다.");
            }

            return payment;
        } catch (IamportResponseException | IOException e) {
            log.error("결제 검증 중 오류 발생", e);
            throw new RuntimeException("결제 검증 중 오류가 발생했습니다.", e);
        }
    }

    public Payment getPaymentStatus(String impUid) {
        try {
            IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(impUid);
            return paymentResponse.getResponse();
        } catch (IamportResponseException | IOException e) {
            log.error("결제 상태 조회 중 오류 발생", e);
            throw new RuntimeException("결제 상태 조회 중 오류가 발생했습니다.", e);
        }
    }
}
