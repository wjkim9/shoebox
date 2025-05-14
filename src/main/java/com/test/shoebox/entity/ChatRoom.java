package com.test.shoebox.entity;

import com.test.shoebox.dto.ChatRoomDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChatRoom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chatroom_seq_generator")
    @SequenceGenerator(name = "chatroom_seq_generator", sequenceName = "chatroom_seq", allocationSize = 1)
    @Column(name = "chatroom_id")
    private Long chatRoomId;

    @ManyToOne
    @JoinColumn(name = "members_id", nullable = false)
    private Members members;

    public ChatRoomDTO toDTO() {
        return ChatRoomDTO.builder()
                .chatRoomId(this.chatRoomId)
                .membersId(this.members.getMembersId())
                .build();
    }
}
