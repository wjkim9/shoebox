package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.IssuedCoupon;
import com.test.shoebox.entity.Members;
import com.test.shoebox.entity.Orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDTO {
	
    private Long ordersId;

    
    private Integer ordersStatus;

    private Integer paymentAmount;

    private Integer paymentPoint;

    private Integer shippingFee;

    private String receiverName;

    private String receiverEmail;

    private String receiverContact;

    private String paymentMethod;

    private String paymentInfo;

    private Integer destinationZipCode;

    private String destinationRoadAddress;

    private String destinationJibunAddress;

    private String destinationDetailAddress;

    private String destinationReference;

    private LocalDateTime ordersDate;

    private Long issuedCouponId;
    
    private Long membersId;
    
    private String statusName; //주문상태 추가

    public Orders toEntity(IssuedCoupon issuedCoupon, Members members) {
        return Orders.builder()
                .ordersId(this.ordersId)
                .ordersStatus(this.ordersStatus)
                .paymentAmount(this.paymentAmount)
                .paymentPoint(this.paymentPoint)
                .shippingFee(this.shippingFee)
                .receiverName(this.receiverName)
                .receiverEmail(this.receiverEmail)
                .receiverContact(this.receiverContact)
                .paymentMethod(this.paymentMethod)
                .paymentInfo(this.paymentInfo)
                .destinationZipCode(this.destinationZipCode)
                .destinationRoadAddress(this.destinationRoadAddress)
                .destinationJibunAddress(this.destinationJibunAddress)
                .destinationDetailAddress(this.destinationDetailAddress)
                .destinationReference(this.destinationReference)
                .ordersDate(this.ordersDate)
                .issuedCoupon(issuedCoupon)
                .members(members)
                .build();
    }
}
