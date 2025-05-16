package com.test.shoebox.dto;

import com.test.shoebox.entity.Categories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriesDTO {
    private Long categoriesId;
    private String categoriesName;
    private String picName;

    public Categories toEntity() {
        return Categories.builder()
                .categoriesId(this.categoriesId)
                .categoriesName(this.categoriesName)
                .picName(this.picName)
                .build();
    }
}
