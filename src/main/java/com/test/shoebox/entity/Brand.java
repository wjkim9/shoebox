package com.test.shoebox.entity;

import com.test.shoebox.dto.BrandDTO;

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
@Table(name = "Brand")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seq_generator")
    @SequenceGenerator(name = "brand_seq_generator", sequenceName = "brand_seq", allocationSize = 1)
    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "brand_name", nullable = false, length = 200)
    private String brandName;

    public BrandDTO toDTO() {
        return BrandDTO.builder()
                .brandId(this.brandId)
                .brandName(this.brandName)
                .build();
    }
}
