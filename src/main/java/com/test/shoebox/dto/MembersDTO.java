package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.Members;

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
public class MembersDTO {
	
	private Long membersId;

    private String accountName;

    private String password;

    private String nickname;

    private String name;

    private String email;

    private String contact;

    private Double footSize;

    private LocalDateTime joinDatetime;

    private LocalDateTime modifiedDatetime;

    private Integer point;

    private Integer isDeleted;
    
    private String role;

    
    public Members toEntity() {
        return Members.builder()
                .membersId(this.membersId)
                .accountName(this.accountName)
                .nickname(this.nickname)
                .name(this.name)
                .email(this.email)
                .contact(this.contact)
                .footSize(this.footSize)
                .joinDatetime(this.joinDatetime)
                .modifiedDatetime(this.modifiedDatetime)
                .point(this.point)
                .isDeleted(this.isDeleted)
                .build();
    }
}
