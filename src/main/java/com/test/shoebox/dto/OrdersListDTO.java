package com.test.shoebox.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OrdersListDTO {
    private Long ordersId;
    private String receiverName;
    private String receiverContact;
    private LocalDateTime ordersDate;

    private String mainProductName;
    private String mainProductSize;
    private int productCount;

    private int paymentAmount;
    private int shippingFee;
    private String paymentMethod;

    private int ordersStatus;
    private String statusName;

    private String trackingCompany;
    private String trackingNumber;
}
