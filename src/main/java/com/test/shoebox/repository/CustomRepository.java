package com.test.shoebox.repository;

import static com.test.shoebox.entity.QProduct.product;
import static com.test.shoebox.entity.QProductImage.productImage;
import static com.test.shoebox.entity.QProductPost.productPost;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.shoebox.entity.Brand;
import com.test.shoebox.entity.ProductImage;

import lombok.RequiredArgsConstructor;

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

	public List<ProductImage> findByProductImageByBrand(Brand brand, Integer maxFetch) {
		
		return jpaQueryFactory.select(productImage)
								.from(productImage)
								.innerJoin(productImage.product, product)
								.innerJoin(product.productPost, productPost)
								.where(product.brand.eq(brand).and(productImage.sortOrder.eq(1)))
								.offset(0)
								.limit(maxFetch)
								.orderBy(productPost.viewCount.desc())
								.fetch();
	}
	
	
	
	
}
 