package com.test.shoebox.entity;

import com.test.shoebox.dto.MemberGradeDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "MemberGrade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membergrade_seq_generator")
    @SequenceGenerator(name = "membergrade_seq_generator", sequenceName = "membergrade_seq", allocationSize = 1)
    @Column(name = "membergrade_id")
    private Long memberGradeId;

    @Column(name = "grade_name", nullable = false, length = 20)
    private String gradeName;

    @Column(name = "discount_rate", nullable = false)
    private Double discountRate;

    @ManyToOne
    @JoinColumn(name = "members_id", nullable = false)
    private Members members;

    public MemberGradeDTO toDTO() {
        return MemberGradeDTO.builder()
                .memberGradeId(this.memberGradeId)
                .gradeName(this.gradeName)
                .discountRate(this.discountRate)
                .membersId(this.members.getMembersId())
                .build();
    }
}
