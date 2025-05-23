package com.test.shoebox.controller.main;

import java.util.List;

import lombok.Data;

@Data
public class OrderForm {
    private List<OrderItemDTO> items;
}
