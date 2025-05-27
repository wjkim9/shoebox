package com.test.shoebox.dto;

import com.test.shoebox.entity.CartItem;
import com.test.shoebox.entity.Members;
import com.test.shoebox.entity.ProductStock;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class CartItemDTO {
	private Long cartItemId;
	private Integer quantity;
	private Long productStockId;
	private Long membersId;
	
	
	public CartItem toEntity(ProductStock productStock, Members members) {
        return CartItem.builder()
                .cartItemId(this.cartItemId)
                .quantity(this.quantity)
                .productStock(productStock)
                .members(members)
                .build();
    }

}
