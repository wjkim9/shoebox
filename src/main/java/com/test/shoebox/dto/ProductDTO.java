package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.Brand;
import com.test.shoebox.entity.Categories;
import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
	private Long productId;

    private String productName;

    private Integer productPrice;

    private Double discountRate;

    private String targetCustomerType;

    private LocalDateTime productRegisterDate;

    private Long brandId;
    
    private Long categoriesId;
    
    private Long productGroupId;

    public Product toEntity(Brand brand, Categories categories, ProductGroup productGroup) {
        return Product.builder()
                .productId(this.productId)
                .productName(this.productName)
                .productPrice(this.productPrice)
                .discountRate(this.discountRate)
                .targetCustomerType(this.targetCustomerType)
                .productRegisterDate(this.productRegisterDate)
                .brand(brand)
                .categories(categories)
                .productGroup(productGroup)
                .build();
    }
}
