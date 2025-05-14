package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.Notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDTO {
	private Long noticeId;

    private String title;

    private String content;

    private LocalDateTime writeDate;

    public Notice toEntity() {
        return Notice.builder()
                .noticeId(this.noticeId)
                .title(this.title)
                .content(this.content)
                .writeDate(this.writeDate)
                .build();
    }
}
