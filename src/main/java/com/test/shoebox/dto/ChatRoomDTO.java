package com.test.shoebox.dto;

import com.test.shoebox.entity.ChatRoom;
import com.test.shoebox.entity.Members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDTO {
    private Long chatRoomId;
    private Long membersId;

    public ChatRoom toEntity(Members members) {
        return ChatRoom.builder()
                .chatRoomId(this.chatRoomId)
                .members(members)
                .build();
    }
}
