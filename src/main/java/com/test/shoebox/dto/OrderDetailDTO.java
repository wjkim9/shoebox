package com.test.shoebox.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {
    private OrdersDTO orders; // 주문 테이블
    private MembersDTO member;
    private List<ProductStockOrderDTO> orderItems; // 주문 상품 목록 (ProductStockOrder 기준)
    private List<DeliveryProgressDTO> deliveryProgressList; // 배송 진행 정보

    private int totalOrderPrice; //전체 가격(수량*가격 의 합)
    private int discountAmount; //할인 금액


}
