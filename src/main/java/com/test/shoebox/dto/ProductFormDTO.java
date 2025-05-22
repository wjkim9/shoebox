package com.test.shoebox.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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

    /** ✅ 등록일자 필드 추가 (선택적으로 채움) */
    private LocalDateTime registerDate;

    public Integer getPrice() {
        return productPrice;
    }
}
