package com.test.shoebox.dto;

import com.test.shoebox.entity.ChatMessage;
import com.test.shoebox.entity.ChatRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO {
    private Long chatMessageId;
    private String content;
    private Integer writerClassify;
    private java.time.LocalDateTime sendDatetime;
    private Long chatRoomId;

    public ChatMessage toEntity(ChatRoom chatRoom) {
        return ChatMessage.builder()
                .chatMessageId(this.chatMessageId)
                .content(this.content)
                .writerClassify(this.writerClassify)
                .sendDatetime(this.sendDatetime != null ? this.sendDatetime : java.time.LocalDateTime.now())
                .chatRoom(chatRoom)
                .build();
    }
}
