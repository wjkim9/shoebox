package com.test.shoebox.entity;

import java.time.LocalDateTime;

import com.test.shoebox.dto.DeliveryProgressDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DeliveryProgress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliveryprogress_seq_generator")
    @SequenceGenerator(name = "deliveryprogress_seq_generator", sequenceName = "deliveryprogress_seq", allocationSize = 1)
    @Column(name = "deliveryprogress_id")
    private Long deliveryProgressId;

    @Column(name = "current_delivery_step", nullable = false, length = 30)
    private String currentDeliveryStep;

    @Column(name = "current_step_datetime", nullable = false)
    private LocalDateTime currentStepDatetime;

    @Column(name = "next_step_datetime", nullable = false)
    private LocalDateTime nextStepDatetime;

    @ManyToOne
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    public DeliveryProgressDTO toDTO() {
        return DeliveryProgressDTO.builder()
                .deliveryProgressId(this.deliveryProgressId)
                .currentDeliveryStep(this.currentDeliveryStep)
                .currentStepDatetime(this.currentStepDatetime)
                .nextStepDatetime(this.nextStepDatetime)
                .ordersId(this.orders.getOrdersId())
                .build();
    }
}