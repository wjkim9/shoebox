package com.test.shoebox.controller.main;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productStockId;
    private Integer qty;
}