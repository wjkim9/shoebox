package com.test.shoebox.service.payment;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.test.shoebox.entity.*;
import com.test.shoebox.repository.*;
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
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final CartItemRepository cartItemRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final ProductStockRepository productStockRepository;
    private final OrdersRepository ordersRepository;
    private final ProductStockOrderRepository productStockOrderRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final MembersRepository membersRepository;
    private final PointTransactionRepository pointTransactionRepository;

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

        // 사용할 포인트 금액 계산
        final int usePointAmount = usePoint > 0 ? usePoint : 0;

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
                
                // 총 할인 금액 계산 (포인트 제외)
                return productDiscountAmount + couponDiscountAmount + memberDiscountAmount;
            })
            .sum();
        
        // 결제 예정 금액 계산 (포인트 할인 포함)
        int finalAmount = totalAmount - totalDiscountAmount - usePointAmount;

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

    @Transactional
    public void processOrderCompletion(String impUid, String merchantUid, BigDecimal amount, Map<String, Object> orderData) {
        try {
            // 1. 주문 정보 생성
            Orders order = createOrder(orderData);
            ordersRepository.save(order);

            // 2. 상품 재고 감소 및 주문 상품 정보 저장
            List<Long> cartItemIds = (List<Long>) orderData.get("cartItemIds");
            for (Long cartItemId : cartItemIds) {
                CartItem cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new RuntimeException("장바구니 상품을 찾을 수 없습니다."));
                
                // 재고 감소
                ProductStock productStock = cartItem.getProductStock();
                productStock.setStockQuantity(productStock.getStockQuantity() - cartItem.getQuantity());
                productStockRepository.save(productStock);

                // 주문 상품 정보 저장
                ProductStockOrder productStockOrder = ProductStockOrder.builder()
                    .orders(order)
                    .productStock(productStock)
                    .quantity(cartItem.getQuantity())
                    .orderPrice(cartItem.getProductStock().getProduct().getProductPrice())
                    .build();
                productStockOrderRepository.save(productStockOrder);
            }

            // 3. 사용한 쿠폰 삭제
            Map<String, Object> appliedCoupons = (Map<String, Object>) orderData.get("appliedCoupons");
            if (appliedCoupons != null) {
                for (Object couponInfo : appliedCoupons.values()) {
                    List<String> couponIds = (List<String>) ((Map<String, Object>) couponInfo).get("couponIds");
                    for (String couponId : couponIds) {
                        issuedCouponRepository.deleteById(Long.parseLong(couponId));
                    }
                }
            }

            // 4. 포인트 차감
            Integer usePoint = (Integer) orderData.get("usePoint");
            if (usePoint != null && usePoint > 0) {
                Members member = membersRepository.findById((Long) orderData.get("membersId"))
                    .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
                
                member.setPoint(member.getPoint() - usePoint);
                membersRepository.save(member);

                // 포인트 사용 내역 기록
                PointTransaction pointTransaction = PointTransaction.builder()
                    .members(member)
                    .transactionType("USE")
                    .transactionPoint(-usePoint)
                    .reason("주문 결제")
                    .build();
                pointTransactionRepository.save(pointTransaction);
            }

            // 5. 장바구니에서 주문한 상품 삭제
            cartItemRepository.deleteAllById(cartItemIds);

        } catch (Exception e) {
            log.error("주문 처리 중 오류 발생", e);
            throw new RuntimeException("주문 처리 중 오류가 발생했습니다.");
        }
    }

    @Transactional
    public void deleteCartItems(List<Long> cartItemIds) {
        cartItemRepository.deleteAllByCartItemIdIn(cartItemIds);
    }

    private Orders createOrder(Map<String, Object> orderData) {
        Members member = membersRepository.findById((Long) orderData.get("membersId"))
            .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        return Orders.builder()
            .ordersStatus(0) // 주문 상태: 결제 완료
            .paymentAmount((Integer) orderData.get("totalAmount"))
            .paymentPoint((Integer) orderData.get("usePoint"))
            .shippingFee(0) // 무료 배송
            .receiverName((String) orderData.get("receiverName"))
            .receiverEmail((String) orderData.get("receiverEmail"))
            .receiverContact((String) orderData.get("receiverPhone"))
            .paymentMethod((String) orderData.get("paymentMethod"))
            .paymentInfo((String) orderData.get("merchantUid"))
            .destinationZipCode(Integer.parseInt((String) orderData.get("zipcode")))
            .destinationRoadAddress((String) orderData.get("address"))
            .destinationJibunAddress((String) orderData.get("address")) // 도로명 주소와 동일하게 설정
            .destinationDetailAddress((String) orderData.get("detailAddress"))
            .destinationReference((String) orderData.get("deliveryRequest"))
            .members(member)
            .build();
    }
}
