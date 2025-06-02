package com.test.shoebox.dto;

import com.test.shoebox.entity.MemberAddress;
import com.test.shoebox.entity.Members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
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
    
    public MemberAddressDTO(Object[] result) {
        // [0] memberAddressId
        if (result[0] instanceof java.math.BigDecimal) {
            this.memberAddressId = ((java.math.BigDecimal) result[0]).longValue();
        } else if (result[0] instanceof Number) {
            this.memberAddressId = ((Number) result[0]).longValue();
        }

        // [1] zipCode
        if (result[1] instanceof java.math.BigDecimal) {
            this.zipCode = ((java.math.BigDecimal) result[1]).intValue();
        } else if (result[1] instanceof Number) {
            this.zipCode = ((Number) result[1]).intValue();
        }

        this.roadAddress = (String) result[2];
        this.jibunAddress = (String) result[3];
        this.detailAddress = (String) result[4];
        this.addressReference = (String) result[5];

        // [6] membersId
        if (result[6] instanceof java.math.BigDecimal) {
            this.membersId = ((java.math.BigDecimal) result[6]).longValue();
        } else if (result[6] instanceof Number) {
            this.membersId = ((Number) result[6]).longValue();
        }
    }
}
