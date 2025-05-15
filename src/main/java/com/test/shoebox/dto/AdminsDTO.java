package com.test.shoebox.dto;

import com.test.shoebox.entity.Admins;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminsDTO {
    private Long adminsId;
    private String password;

    public Admins toEntity() {
        return Admins.builder()
                .adminsId(this.adminsId)
                .password(this.password)
                .build();
    }
}

