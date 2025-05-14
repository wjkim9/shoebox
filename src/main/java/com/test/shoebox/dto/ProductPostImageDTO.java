package com.test.shoebox.dto;


import com.test.shoebox.entity.ProductPost;
import com.test.shoebox.entity.ProductPostImage;

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
public class ProductPostImageDTO {
	private Long productPostImageId;

    private String fileName;

    private Long productPostId;

    public ProductPostImage toEntity(ProductPost productPost) {
        return ProductPostImage.builder()
                .productPostImageId(this.productPostImageId)
                .fileName(this.fileName)
                .productPost(productPost)
                .build();
    }
}
