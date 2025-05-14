package com.test.shoebox.dto;


import com.test.shoebox.entity.MemberGrade;
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
public class MemberGradeDTO {
	private Long memberGradeId;

    private String gradeName;

    private Double discountRate;

    private Members members;
    
    private Long membersId;

    public MemberGrade toEntity(Members members) {
        return MemberGrade.builder()
                .memberGradeId(this.memberGradeId)
                .gradeName(this.gradeName)
                .discountRate(this.discountRate)
                .members(members)
                .build();
    }
}
