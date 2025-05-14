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
public class ChatMessageDTO {
	private Long chatMessageId;
    private String content;
    private Integer writerClassify;
    private LocalDateTime sendDatetime;
    private Long chatRoomId;
}
