package com.test.shoebox.dto;

import com.test.shoebox.entity.DeliveryScenario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryScenarioDTO {
    private Long deliveryScenarioId;
    private String step;
    private String location;
    private String status;
    private Integer waitTime;

    public DeliveryScenario toEntity() {
        return DeliveryScenario.builder()
                .deliveryScenarioId(this.deliveryScenarioId)
                .step(this.step)
                .location(this.location)
                .status(this.status)
                .waitTime(this.waitTime)
                .build();
    }
}
