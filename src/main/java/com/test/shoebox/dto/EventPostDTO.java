package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.EventPost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventPostDTO {
    private Long eventPostId;
    private String title;
    private String content;
    private LocalDateTime writeDate;

    public EventPost toEntity() {
        return EventPost.builder()
                .eventPostId(this.eventPostId)
                .title(this.title)
                .content(this.content)
                .writeDate(this.writeDate != null ? this.writeDate : LocalDateTime.now())
                .build();
    }
}
