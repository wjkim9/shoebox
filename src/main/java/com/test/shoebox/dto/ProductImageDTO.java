package com.test.shoebox.dto;

import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductImageDTO {
	private Long productImageId;

    private String fileName;

    private Integer sortOrder;

    private Long productId;

    public ProductImage toEntity(Product product) {
        return ProductImage.builder()
                .productImageId(this.productImageId)
                .fileName(this.fileName)
                .sortOrder(this.sortOrder)
                .product(product)
                .build();
    }
}
