package com.test.shoebox.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AdminsDTO {
	private Long adminsId;
	private String password;
}
