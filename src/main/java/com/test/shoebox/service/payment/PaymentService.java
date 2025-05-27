package com.test.shoebox.service.payment;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.test.shoebox.entity.CartItem;
import com.test.shoebox.entity.MemberGrade;
import com.test.shoebox.repository.CartItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.shoebox.repository.MemberGradeRepository;
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
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final CartItemRepository cartItemRepository;
    private final MemberGradeRepository memberGradeRepository;

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

        // 총 주문 금액 계산 (수량 고려)
        int totalAmount = selectedItems.stream()
            .mapToInt(item -> {
                int itemPrice = item.getProductStock().getProduct().getProductPrice();
                int quantity = item.getQuantity();
                return itemPrice * quantity;
            })
            .sum();
        
        // 총 할인 금액 계산 (수량 고려)
        int totalDiscountAmount = selectedItems.stream()
            .mapToInt(item -> {
                // 상품 자체 할인 계산
                int productDiscountAmount = 0;
                int itemPrice = item.getProductStock().getProduct().getProductPrice();
                int quantity = item.getQuantity();
                int totalItemPrice = itemPrice * quantity;

                if (item.getProductStock().getProduct().getDiscountRate() != 0) {
                    productDiscountAmount += (int) (totalItemPrice * (item.getProductStock().getProduct().getDiscountRate() / 100.0));
                }

                //멤버 할인 금액 계산
                int memberDiscountAmount = 0;
                if (item.getMembers() != null) {
                    // 회원의 등급 정보 조회
                    Long membersId = item.getMembers().getMembersId();
                    List<MemberGrade> memberGrades = memberGradeRepository.findAllByMembers_MembersId(membersId);
                    
                    if (!memberGrades.isEmpty()) {
                        // 가장 높은 할인율을 가진 등급 선택
                        MemberGrade highestGrade = memberGrades.stream()
                            .max((g1, g2) -> Double.compare(g1.getDiscountRate(), g2.getDiscountRate()))
                            .orElse(null);
                            
                        if (highestGrade != null) {
                            // 회원 등급의 할인율 적용
                            Double discountRate = highestGrade.getDiscountRate();
                            memberDiscountAmount += (int) ((totalItemPrice - productDiscountAmount) * (discountRate / 100.0));
                        }
                    }
                }

                // 쿠폰 할인 계산
                int couponDiscountAmount = 0;
                if (appliedCoupons != null && !appliedCoupons.isEmpty()) {
                    try {
                        Map<String, List<Map<String, Object>>> allAppliedCoupons = new ObjectMapper().readValue(appliedCoupons, Map.class);
                        List<Map<String, Object>> itemCoupons = allAppliedCoupons.get(item.getCartItemId().toString());
                        if (itemCoupons != null) {
                            // 한 상품의 1개당 1개의 쿠폰만 적용
                            int applicableQuantity = Math.min(itemCoupons.size(), quantity);
                            for (int i = 0; i < applicableQuantity; i++) {
                                Map<String, Object> coupon = itemCoupons.get(i);
                                double discountRate = Double.parseDouble(coupon.get("discountRate").toString());
                                // 1개당 가격에 쿠폰 적용
                                couponDiscountAmount += (int) (itemPrice * (discountRate / 100.0));
                            }
                        }
                    } catch (Exception e) {
                        log.error("쿠폰 할인 계산 중 오류 발생", e);
                    }
                }

                // 사용 포인트 있으면 확인
                int usePointAmount = 0;
                if (usePoint > 0) {
                    usePointAmount = usePoint;
                }
                
                // 총 할인 금액 계산
                return productDiscountAmount + couponDiscountAmount + memberDiscountAmount + usePointAmount;
            })
            .sum();
        
        // 결제 예정 금액 계산 (포인트 할인 포함)
        int finalAmount = totalAmount - totalDiscountAmount;

        // CartItem을 Map으로 변환
        List<Map<String, Object>> selectedItemsMap = selectedItems.stream()
            .map(item -> {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("cartItemId", item.getCartItemId());
                itemMap.put("quantity", item.getQuantity());
                itemMap.put("productStock", Map.of(
                    "productStockId", item.getProductStock().getProductStockId(),
                    "shoeSize", item.getProductStock().getShoeSize(),
                    "product", Map.of(
                        "productId", item.getProductStock().getProduct().getProductId(),
                        "productName", item.getProductStock().getProduct().getProductName(),
                        "productPrice", item.getProductStock().getProduct().getProductPrice(),
                        "productImages", item.getProductStock().getProduct().getProductImages().stream()
                            .map(image -> Map.of(
                                "fileName", image.getFileName()
                            ))
                            .collect(Collectors.toList())
                    )
                ));
                return itemMap;
            })
            .collect(Collectors.toList());
        
        // 엔티티를 제외하고 숫자 값만 반환
        return Map.of(
            "selectedItems", selectedItemsMap,
            "totalAmount", totalAmount,
            "totalDiscountAmount", totalDiscountAmount,
            "finalAmount", finalAmount
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
