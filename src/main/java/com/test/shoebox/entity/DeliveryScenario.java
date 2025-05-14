package com.test.shoebox.entity;

import com.test.shoebox.dto.DeliveryScenarioDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DeliveryScenario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryScenario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliveryscenario_seq_generator")
    @SequenceGenerator(name = "deliveryscenario_seq_generator", sequenceName = "deliveryscenario_seq", allocationSize = 1)
    @Column(name = "deliveryscenario_id")
    private Long deliveryScenarioId;

    @Column(name = "step", nullable = false, length = 30)
    private String step;

    @Column(name = "location", nullable = false, length = 30)
    private String location;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "wait_time", nullable = false)
    private Integer waitTime;

    public DeliveryScenarioDTO toDTO() {
        return DeliveryScenarioDTO.builder()
                .deliveryScenarioId(this.deliveryScenarioId)
                .step(this.step)
                .location(this.location)
                .status(this.status)
                .waitTime(this.waitTime)
                .build();
    }
}
