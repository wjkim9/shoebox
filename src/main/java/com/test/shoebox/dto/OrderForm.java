package com.test.shoebox.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderForm {
    private List<OrderItemDTO> items;
}
