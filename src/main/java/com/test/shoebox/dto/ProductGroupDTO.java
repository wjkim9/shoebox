package com.test.shoebox.dto;


import com.test.shoebox.entity.ProductGroup;

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
public class ProductGroupDTO {
	private Long productGroupId;
    private String productGroupName;

    public ProductGroup toEntity() {
        return ProductGroup.builder()
                .productGroupId(this.productGroupId)
                .productGroupName(this.productGroupName)
                .build();
    }
}
