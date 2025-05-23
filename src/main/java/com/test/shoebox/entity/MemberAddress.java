package com.test.shoebox.entity;

import com.test.shoebox.dto.MemberAddressDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MemberAddress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberaddress_seq_generator")
    @SequenceGenerator(name = "memberaddress_seq_generator", sequenceName = "memberaddress_seq", allocationSize = 1)
    @Column(name = "memberaddress_id")
    private Long memberAddressId;

    @Column(name = "zip_code", nullable = false)
    private Integer zipCode;

    @Column(name = "road_address", nullable = false, length = 255)
    private String roadAddress;

    @Column(name = "jibun_address", nullable = false, length = 255)
    private String jibunAddress;

    @Column(name = "detail_address", nullable = false, length = 255)
    private String detailAddress;

    @Column(name = "address_reference", length = 255)
    private String addressReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id", nullable = false)
    private Members members;

    public MemberAddressDTO toDTO() {
        return MemberAddressDTO.builder()
                .memberAddressId(this.memberAddressId)
                .zipCode(this.zipCode)
                .roadAddress(this.roadAddress)
                .jibunAddress(this.jibunAddress)
                .detailAddress(this.detailAddress)
                .addressReference(this.addressReference)
                .membersId(this.members.getMembersId())
                .build();
    }
    
}
