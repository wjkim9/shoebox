package com.test.shoebox.service.admin;

import com.test.shoebox.dto.*;
import com.test.shoebox.entity.*;
import com.test.shoebox.repository.DeliveryProgressRepository;
import com.test.shoebox.repository.OrdersRepository;
import com.test.shoebox.repository.ProductStockOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final ProductStockOrderRepository productStockOrderRepository;
    private final DeliveryProgressRepository deliveryProgressRepository;


    // 상태 코드 → 한글 상태명 매핑
    private static final Map<Integer, String> statusMap = new HashMap<>();
    static {
        statusMap.put(1, "결제 확인중");
        statusMap.put(2, "결제 확인완료");
        statusMap.put(3, "배송준비중");
        statusMap.put(4, "취소 대기중");
        statusMap.put(5, "취소 확정");
        statusMap.put(6, "배송중");
        statusMap.put(7, "배송완료");
        statusMap.put(8, "구매확정완료");
        statusMap.put(9, "반송중");
        statusMap.put(10, "반송완료");
        statusMap.put(11, "환불처리중");
        statusMap.put(12, "환불완료");
    }


    private String getStatusName(int statusCode) {
        return statusMap.getOrDefault(statusCode, "알 수 없음");
    }

    public List<OrdersListDTO> getOrderPageItems() {
        List<Orders> ordersList = ordersRepository.findAllByOrderByOrdersDateDesc();


        return ordersList.stream().map(order -> {
            List<ProductStockOrder> stockOrders = productStockOrderRepository.findByOrders(order);
            ProductStockOrder mainStockOrder = stockOrders.get(0); // 대표 상품 1개만 표시

            ProductStock productStock = mainStockOrder.getProductStock();
            Product product = productStock.getProduct();

            DeliveryProgress delivery = deliveryProgressRepository.findByOrders(order);

            return OrdersListDTO.builder()
                    .ordersId(order.getOrdersId())
                    .ordersDate(order.getOrdersDate())
                    .receiverName(order.getReceiverName())
                    .receiverContact(order.getReceiverContact())

                    .mainProductName(product.getProductName())
                    .mainProductSize(productStock.getShoeSize())
                    .productCount(stockOrders.size())

                    .paymentAmount(order.getPaymentAmount())
                    .shippingFee(order.getShippingFee())
                    .paymentMethod(order.getPaymentMethod())

                    .ordersStatus(order.getOrdersStatus())
                    .statusName(getStatusName(order.getOrdersStatus()))
                    .build();
        }).collect(Collectors.toList());
    }



    //상품 상세조회를 위한 dto
    public OrderDetailDTO getOrderDetail(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다: id=" + orderId));

        // 주문 DTO 변환
        OrdersDTO ordersDTO = OrdersDTO.builder()
                .ordersId(order.getOrdersId())
                .ordersStatus(order.getOrdersStatus())
                .statusName(getStatusName(order.getOrdersStatus()))
                .paymentAmount(order.getPaymentAmount())
                .paymentPoint(order.getPaymentPoint())
                .shippingFee(order.getShippingFee())
                .receiverName(order.getReceiverName())
                .receiverEmail(order.getReceiverEmail())
                .receiverContact(order.getReceiverContact())
                .paymentMethod(order.getPaymentMethod())
                .paymentInfo(order.getPaymentInfo())
                .destinationZipCode(order.getDestinationZipCode())
                .destinationRoadAddress(order.getDestinationRoadAddress())
                .destinationJibunAddress(order.getDestinationJibunAddress())
                .destinationDetailAddress(order.getDestinationDetailAddress())
                .destinationReference(order.getDestinationReference())
                .ordersDate(order.getOrdersDate())
                .issuedCouponId(order.getIssuedCoupon().getIssuedCouponId())
                .membersId(order.getMembers().getMembersId())
                .build();

        // 회원 DTO 생성
        Members member = order.getMembers();
        MembersDTO memberDTO = MembersDTO.builder()
                .membersId(member.getMembersId())
                .name(member.getName())
                .contact(member.getContact())
                .email(member.getEmail())
                .build();

        // 주문 상품 리스트
        List<ProductStockOrderDTO> itemDTOs = productStockOrderRepository.findByOrders(order).stream()
                .map(pso -> {
                    ProductStock ps = pso.getProductStock();
                    Product product = ps.getProduct();
                    return ProductStockOrderDTO.builder()
                            .productStockOrderId(pso.getProductStockOrderId())
                            .quantity(pso.getQuantity())
                            .orderPrice(pso.getOrderPrice())
                            .productStockId(ps.getProductStockId())
                            .ordersId(order.getOrdersId())
                            .productId(product.getProductId())
                            .productName(product.getProductName())
                            .productImageUrl("/images/product/" + product.getProductId() + ".jpg")
                            .shoeSize(ps.getShoeSize())
                            .build();
                })
                .collect(Collectors.toList());

        // 배송 상태
        List<DeliveryProgressDTO> progressList = List.of();
        DeliveryProgress dp = deliveryProgressRepository.findByOrders(order);
        if (dp != null) {
            progressList = List.of(
                    DeliveryProgressDTO.builder()
                            .deliveryProgressId(dp.getDeliveryProgressId())
                            .currentDeliveryStep(dp.getCurrentDeliveryStep())
                            .currentStepDatetime(dp.getCurrentStepDatetime())
                            .nextStepDatetime(dp.getNextStepDatetime())
                            .ordersId(order.getOrdersId())
                            .build()
            );
        }



        int totalOrderPrice = itemDTOs.stream()
                .mapToInt(item -> item.getQuantity() * item.getOrderPrice())
                .sum();


        IssuedCoupon issuedCoupon = order.getIssuedCoupon();
        Coupon coupon = issuedCoupon != null ? issuedCoupon.getCoupon() : null;
        int discountRate = (coupon != null && coupon.getDiscountRate() != null)
                ? coupon.getDiscountRate().intValue()
                : 0;

        int discountAmount = (totalOrderPrice * discountRate) / 100;


        return OrderDetailDTO.builder()
                .orders(ordersDTO)
                .member(memberDTO)
                .orderItems(itemDTOs)
                .deliveryProgressList(progressList)
                .totalOrderPrice(totalOrderPrice)
                .discountAmount(discountAmount)
                .build();
    }

//검색하기
    public List<OrdersListDTO> searchOrders(LocalDate orderDateStart, LocalDate orderDateEnd,
                                            String orderStatus, String searchType, String searchKeyword) {
        List<Orders> ordersList = ordersRepository.findAll(); // 일단 전체 불러오기


        // 필터 조건 적용
        return ordersList.stream()
                .filter(order -> {
                    System.out.println("order.getOrdersStatus() = " + order.getOrdersStatus());

                    if (orderDateStart != null && orderDateEnd != null) {
                        if (order.getOrdersDate().toLocalDate().isBefore(orderDateStart) ||
                                order.getOrdersDate().toLocalDate().isAfter(orderDateEnd)) {
                            return false;
                        }
                    }

                    if (orderStatus != null && !orderStatus.isEmpty()) {
                        int statusCode = convertStatus(orderStatus);
                        if (order.getOrdersStatus() != statusCode) return false;
                    }


                    if (searchType != null && searchKeyword != null && !searchKeyword.isBlank()) {
                        boolean match = switch (searchType) {
                            case "orderNumber" -> String.valueOf(order.getOrdersId()).contains(searchKeyword);
                            case "customerName" -> order.getReceiverName().contains(searchKeyword);
                            case "customerPhone" -> order.getReceiverContact().contains(searchKeyword);
                            case "productName" -> {
                                List<ProductStockOrder> stockOrders = productStockOrderRepository.findByOrders(order);
                                yield stockOrders.stream()
                                        .map(pso -> pso.getProductStock().getProduct().getProductName())
                                        .anyMatch(name -> name.contains(searchKeyword));
                            }
                            default -> true;
                        };
                        if (!match) return false;
                    }

                    return true;
                })
                .map(order -> {
                    List<ProductStockOrder> stockOrders = productStockOrderRepository.findByOrders(order);
                    ProductStockOrder mainStockOrder = stockOrders.get(0);
                    ProductStock productStock = mainStockOrder.getProductStock();
                    Product product = productStock.getProduct();

                    DeliveryProgress delivery = deliveryProgressRepository.findByOrders(order);

                    return OrdersListDTO.builder()
                            .ordersId(order.getOrdersId())
                            .ordersDate(order.getOrdersDate())
                            .receiverName(order.getReceiverName())
                            .receiverContact(order.getReceiverContact())

                            .mainProductName(product.getProductName())
                            .mainProductSize(productStock.getShoeSize())
                            .productCount(stockOrders.size())

                            .paymentAmount(order.getPaymentAmount())
                            .shippingFee(order.getShippingFee())
                            .paymentMethod(order.getPaymentMethod())

                            .ordersStatus(order.getOrdersStatus())
                            .statusName(getStatusName(order.getOrdersStatus()))

                            .trackingCompany(delivery != null ? delivery.getCurrentDeliveryStep() : "미등록")
                            .trackingNumber(delivery != null ? delivery.getCurrentDeliveryStep() : null)
                            .build();
                })
                .collect(Collectors.toList());
    }


    // 숫자 문자열을 그대로 int로 변환
    private int convertStatus(String orderStatus) {
        try {
            return Integer.parseInt(orderStatus);
        } catch (NumberFormatException e) {
            return -1; // 또는 예외 처리
        }
    }





    public void updateStatus(Long orderId, int newStatus) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다: id=" + orderId));

        order.setOrdersStatus(newStatus);  // 상태 변경
        ordersRepository.save(order);      // 저장
    }
}
