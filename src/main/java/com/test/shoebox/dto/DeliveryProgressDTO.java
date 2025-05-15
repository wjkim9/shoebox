package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.DeliveryProgress;
import com.test.shoebox.entity.Orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryProgressDTO {
    private Long deliveryProgressId;
    private String currentDeliveryStep;
    private LocalDateTime currentStepDatetime;
    private LocalDateTime nextStepDatetime;
    private Long ordersId;

    public DeliveryProgress toEntity(Orders orders) {
        return DeliveryProgress.builder()
                .deliveryProgressId(this.deliveryProgressId)
                .currentDeliveryStep(this.currentDeliveryStep)
                .currentStepDatetime(this.currentStepDatetime != null ? this.currentStepDatetime : LocalDateTime.now())
                .nextStepDatetime(this.nextStepDatetime)
                .orders(orders)
                .build();
    }
}
