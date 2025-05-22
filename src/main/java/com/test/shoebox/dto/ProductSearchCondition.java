package com.test.shoebox.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProductSearchCondition {
    private LocalDate registrationDateStart;
    private LocalDate registrationDateEnd;
    private String productStatus;   // 없으면 제외 가능
    private String brandName;
    private String categoryName;
    private String searchType;     // productName, modelNumber 등
    private String searchKeyword;
    private String sort;
}
