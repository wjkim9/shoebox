package com.test.shoebox.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productStockId;
    private Integer qty;
}