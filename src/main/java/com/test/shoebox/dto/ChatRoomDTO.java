package com.test.shoebox.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ChatRoomDTO {
	private Long chatRoomId;
	private Long membersId;
}
