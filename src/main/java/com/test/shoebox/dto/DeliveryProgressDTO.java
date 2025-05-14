package com.test.shoebox.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class DeliveryProgressDTO {
	private Long deliveryProgressId;
	private String currentDeliveryStep;
	private LocalDateTime currentStepDatetime;
	private LocalDateTime nextStepDatetime;
	private Long ordersId;
}
