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
public class EventPostDTO {
	private Long eventPostId;
	private String title;
	private String content;
	private LocalDateTime writeDate;
}
