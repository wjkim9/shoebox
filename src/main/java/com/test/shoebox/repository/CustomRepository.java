package com.test.shoebox.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductImage;
import com.test.shoebox.entity.ProductPost;

import lombok.RequiredArgsConstructor;
import static com.test.shoebox.entity.QProduct.product;
import static com.test.shoebox.entity.QProductPost.productPost;
import static com.test.shoebox.entity.QProductImage.productImage;

@Repository
@RequiredArgsConstructor
public class CustomRepository {
	
	private final JPAQueryFactory jpaQueryFactory;

	public List<ProductImage> findProductByDatetime(LocalDateTime startTime, LocalDateTime endTime) {

		return jpaQueryFactory.select(productImage)
							.from(productImage)
							.innerJoin(productImage.product, product)
							.innerJoin(product.productPost, productPost)
							.where(productPost.postDate.between(startTime, endTime).and(productImage.sortOrder.eq(1)))
							.fetch();
	}
	
	
	
	
}
 