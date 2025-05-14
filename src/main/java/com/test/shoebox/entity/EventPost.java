package com.test.shoebox.entity;

import java.time.LocalDateTime;

import com.test.shoebox.dto.EventPostDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EventPost")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventPost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventpost_seq_generator")
    @SequenceGenerator(name = "eventpost_seq_generator", sequenceName = "eventpost_seq", allocationSize = 1)
    @Column(name = "eventpost_id")
    private Long eventPostId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "write_date", nullable = false)
    private LocalDateTime writeDate;

    public EventPostDTO toDTO() {
        return EventPostDTO.builder()
                .eventPostId(this.eventPostId)
                .title(this.title)
                .content(this.content)
                .writeDate(this.writeDate)
                .build();
    }
    
    @PrePersist
    public void prePersist() {
        if (this.writeDate == null) {
            this.writeDate = LocalDateTime.now();
        }
    }

}

