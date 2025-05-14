package com.test.shoebox.entity;

import com.test.shoebox.dto.AdminsDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admins {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admins_seq_generator")
    @SequenceGenerator(name = "admins_seq_generator", sequenceName = "admins_seq", allocationSize = 1)
    @Column(name = "admins_id")
    private Long adminsId;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    public AdminsDTO toDTO() {
        return AdminsDTO.builder()
                .adminsId(this.adminsId)
                .password(this.password)
                .build();
    }
}