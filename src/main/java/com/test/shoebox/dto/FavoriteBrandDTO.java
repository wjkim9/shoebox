package com.test.shoebox.dto;


import com.test.shoebox.entity.Brand;
import com.test.shoebox.entity.FavoriteBrand;
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
public class FavoriteBrandDTO {
    private Long favoriteBrandId;

    private Long brandId;
    
    private Long membersId;

    public FavoriteBrand toEntity(Brand brand, Members members) {
        return FavoriteBrand.builder()
                .favoriteBrandId(this.favoriteBrandId)
                .brand(brand)
                .members(members)
                .build();
    }
}
