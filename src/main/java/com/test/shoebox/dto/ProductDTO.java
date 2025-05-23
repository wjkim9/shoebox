package com.test.shoebox.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

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


    private String productCode;
    private String mainImageUrl;
    private BrandDTO brand;
    private CategoriesDTO category;
    private String status;

    private LocalDate registerDate;

    private List<String> sizes;           // 폼에서 name="sizes[]"
    private List<Integer> sizeStocks;     // 폼에서 name="sizeStocks[]"
    private Integer totalStock; // 전체 재고 합계

    private MultipartFile mainImage;              // 폼에서 name="mainImage"
    private List<MultipartFile> additionalImages; // 폼에서 name="additionalImages"



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
