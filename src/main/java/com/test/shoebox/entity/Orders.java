package com.test.shoebox.entity;

import java.time.LocalDateTime;

import com.test.shoebox.dto.OrdersDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq_generator")
    @SequenceGenerator(name = "orders_seq_generator", sequenceName = "orders_seq", allocationSize = 1)
    @Column(name = "orders_id")
    private Long ordersId;

    @Column(name = "orders_status", nullable = false)
    private Integer ordersStatus;

    @Column(name = "payment_amount", nullable = false)
    private Integer paymentAmount;

    @Column(name = "payment_point", nullable = false)
    private Integer paymentPoint;

    @Column(name = "shipping_fee", nullable = false)
    private Integer shippingFee;

    @Column(name = "receiver_name", nullable = false, length = 50)
    private String receiverName;

    @Column(name = "receiver_email", nullable = false, length = 40)
    private String receiverEmail;

    @Column(name = "receiver_contact", nullable = false, length = 30)
    private String receiverContact;

    @Column(name = "payment_method", nullable = false, length = 30)
    private String paymentMethod;

    @Column(name = "payment_info", nullable = false, length = 100)
    private String paymentInfo;

    @Column(name = "destination_zip_code", nullable = false)
    private Integer destinationZipCode;

    @Column(name = "destination_road_address", nullable = false, length = 255)
    private String destinationRoadAddress;

    @Column(name = "destination_jibun_address", length = 255)
    private String destinationJibunAddress;

    @Column(name = "destination_detail_address", length = 255)
    private String destinationDetailAddress;

    @Column(name = "destination_reference", length = 255)
    private String destinationReference;

    @Column(name = "orders_date", nullable = false)
    private LocalDateTime ordersDate;

    @ManyToOne
    @JoinColumn(name = "issuedcoupon_id")
    private IssuedCoupon issuedCoupon;

    @ManyToOne
    @JoinColumn(name = "members_id")
    private Members members;

    public OrdersDTO toDTO() {
        return OrdersDTO.builder()
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
                .issuedCouponId(this.issuedCoupon != null ? this.issuedCoupon.getIssuedCouponId() : null)
                .membersId(this.members != null ? this.members.getMembersId() : null)
                .build();
    }
    
    @PrePersist
    public void prePersist() {
        if (this.ordersDate == null) {
            this.ordersDate = LocalDateTime.now();
        }
    }

}