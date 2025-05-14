package com.test.shoebox.entity;

import java.time.LocalDateTime;

import com.test.shoebox.dto.NoticeDTO;

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
@Table(name = "Notice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_seq_generator")
    @SequenceGenerator(name = "notice_seq_generator", sequenceName = "notice_seq", allocationSize = 1)
    @Column(name = "notice_id")
    private Long noticeId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "write_date", nullable = false)
    private LocalDateTime writeDate;

    public NoticeDTO toDTO() {
        return NoticeDTO.builder()
                .noticeId(this.noticeId)
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
