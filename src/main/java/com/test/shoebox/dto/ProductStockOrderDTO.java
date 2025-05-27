package com.test.shoebox.dto;

import com.test.shoebox.entity.Orders;
import com.test.shoebox.entity.ProductStock;
import com.test.shoebox.entity.ProductStockOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ProductStockOrderDTO {
	private Long productStockOrderId;
	private Integer quantity;
	private Integer orderPrice;
	private Long ordersId;
	private Long productStockId;


	//조인할것들
	private Long productId;
	private String productName;
	private String productImageUrl;
	private String shoeSize;
	
	public ProductStockOrder toEntity(Orders orders, ProductStock productStock) {
        return ProductStockOrder.builder()
                .productStockOrderId(this.productStockOrderId)
                .quantity(this.quantity)
                .orderPrice(this.orderPrice)
                .orders(orders)
                .productStock(productStock)
                .build();
    }
	
}
