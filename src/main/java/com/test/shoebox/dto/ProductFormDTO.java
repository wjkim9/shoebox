package com.test.shoebox.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductFormDTO {
    private String productName;
    private Long productId;
    private Integer productPrice;
    private double discountRate;
    private String targetCustomerType;

    private Long brandId;
    private Long categoriesId;
    private Long productGroupId;

    private List<String> sizes;
    private List<Integer> sizeStocks;

    private MultipartFile mainImage;
    private List<MultipartFile> additionalImages;

    public Integer getPrice() {
        return productPrice;
    }

}
