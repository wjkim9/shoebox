package com.test.shoebox.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberWithGradeDTO {
    private Long membersId;
    private String accountName;
    private String name;
    private String nickname;
    private String email;
    private String contact;
    private Double footSize;
    private Integer point;
    private String gradeName;
    private Integer isDeleted;
    private java.time.LocalDateTime joinDatetime;
}
