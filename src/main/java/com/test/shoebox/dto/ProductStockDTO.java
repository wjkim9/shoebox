package com.test.shoebox.dto;

import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductStock;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ProductStockDTO {
	private Long productStockId;
	private String shoeSize;
	private Integer stockQuantity;
	private Long productId;
	
	public ProductStock toEntity(Product product) {
        return ProductStock.builder()
                .productStockId(this.productStockId)
                .shoeSize(this.shoeSize)
                .stockQuantity(this.stockQuantity)
                .product(product)
                .build();
    }
}
