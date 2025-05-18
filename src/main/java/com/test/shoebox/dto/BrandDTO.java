package com.test.shoebox.dto;

import com.test.shoebox.entity.Brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BrandDTO {
    private Long brandId;
    private String brandName;

    public Brand toEntity() {
        return Brand.builder()
                .brandId(this.brandId)
                .brandName(this.brandName)
                .build();
    }
}