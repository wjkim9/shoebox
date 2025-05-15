package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.CustomerService;
import com.test.shoebox.entity.Members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerServiceDTO {
    private Long customerServiceId;
    private String title;
    private String content;
    private LocalDateTime writeDate;
    private String answerContent;
    private LocalDateTime answerDate;
    private String category;
    private Long membersId;
    private Long parentId;

    public CustomerService toEntity(Members members, CustomerService parent) {
        return CustomerService.builder()
                .customerServiceId(this.customerServiceId)
                .title(this.title)
                .content(this.content)
                .writeDate(this.writeDate != null ? this.writeDate : LocalDateTime.now())
                .answerContent(this.answerContent)
                .answerDate(this.answerDate)
                .category(this.category)
                .members(members)
                .parent(parent)
                .build();
    }
}
