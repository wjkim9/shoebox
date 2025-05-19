package com.test.shoebox.entity;


import java.time.LocalDateTime;

import com.test.shoebox.dto.MembersDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "members_seq_generator")
    @SequenceGenerator(name = "members_seq_generator", sequenceName = "members_seq", allocationSize = 1)
    @Column(name = "members_id")
    private Long membersId;

    @Column(name = "account_name", nullable = false, length = 100)
    private String accountName;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 40)
    private String email;

    @Column(name = "contact", nullable = false, length = 30)
    private String contact;

    @Column(name = "foot_size")
    private Double footSize;

    @Column(name = "join_datetime", nullable = false)
    private LocalDateTime joinDatetime;

    @Column(name = "modified_datetime")
    private LocalDateTime modifiedDatetime;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted;

    public MembersDTO toDTO() {
        return MembersDTO.builder()
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
    
    @PrePersist
    public void prePersist() {
        if (this.joinDatetime == null) {
            this.joinDatetime = LocalDateTime.now();
        }

        if (this.point == null) {
            this.point = 0;
        }

        if (this.isDeleted == null) {
            this.isDeleted = 0;
        }
    }
    
    @PreUpdate
    public void preUpdate() {
        if (this.modifiedDatetime == null) {
            this.modifiedDatetime = LocalDateTime.now();
        }

    }
    
}

