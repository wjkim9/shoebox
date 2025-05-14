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
public class CustomerServiceDTO {
	private Long customerServiceId;
	private String title;
	private String content;
	private LocalDateTime writeDate;
	private String answerContent;
	private LocalDateTime answerDate;
	private String category;
	private Long membersId;
	private Long parentId;

}
