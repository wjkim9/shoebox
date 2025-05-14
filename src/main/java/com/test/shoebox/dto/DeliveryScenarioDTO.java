package com.test.shoebox.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class DeliveryScenarioDTO {
	private Long deliveryScenarioId;
	private String step;
	private String location;
	private String status;
	private Integer waitTime;
}
