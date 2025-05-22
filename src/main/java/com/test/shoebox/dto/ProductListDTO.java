package com.test.shoebox.dto;

import static com.test.shoebox.entity.QBrand.brand;
import static com.test.shoebox.entity.QProduct.product;
import static com.test.shoebox.entity.QProductImage.productImage;
import static com.test.shoebox.entity.QProductPost.productPost;
import static com.test.shoebox.entity.QProductStockOrder.productStockOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductListDTO {
	private Long productId;
	private String fileName;
	private String brandName;
	private String productName;
	private Integer productPrice;
	private Long productPostId;
	private Integer salesQuantity;
	private Long reviewCount;
	
}
