package com.test.shoebox.entity;

import java.time.LocalDateTime;

import com.test.shoebox.dto.ChatMessageDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChatMessage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chatmessage_seq_generator")
    @SequenceGenerator(name = "chatmessage_seq_generator", sequenceName = "chatmessage_seq", allocationSize = 1)
    @Column(name = "chatmessage_id")
    private Long chatMessageId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "writer_classify", nullable = false)
    private Integer writerClassify;

    @Column(name = "send_datetime", nullable = false)
    private LocalDateTime sendDatetime;

    @ManyToOne
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;

    public ChatMessageDTO toDTO() {
        return ChatMessageDTO.builder()
                .chatMessageId(this.chatMessageId)
                .content(this.content)
                .writerClassify(this.writerClassify)
                .sendDatetime(this.sendDatetime)
                .chatRoomId(this.chatRoom.getChatRoomId())
                .build();
    }
    
    @PrePersist
    public void prePersist() {
        if (this.sendDatetime == null) {
            this.sendDatetime = LocalDateTime.now();
        }
    }

}