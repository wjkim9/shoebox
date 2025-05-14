package com.test.shoebox.dto;

import com.test.shoebox.entity.MemberAddress;
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
public class MemberAddressDTO {
    private Long memberAddressId;

    private Integer zipCode;

    private String roadAddress;

    private String jibunAddress;

    private String detailAddress;

    private String addressReference;

    private Members members;
    
    private Long membersId;
    
    public MemberAddress toEntity(Members members) {
        return MemberAddress.builder()
                .memberAddressId(this.memberAddressId)
                .zipCode(this.zipCode)
                .roadAddress(this.roadAddress)
                .jibunAddress(this.jibunAddress)
                .detailAddress(this.detailAddress)
                .addressReference(this.addressReference)
                .members(members)
                .build();
    }
}
