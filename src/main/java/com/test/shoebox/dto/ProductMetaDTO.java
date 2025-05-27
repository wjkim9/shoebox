package com.test.shoebox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMetaDTO {
    private List<BrandDTO> brands;
    private List<CategoriesDTO> categories;
    private List<ProductGroupDTO> productGroups;


}
