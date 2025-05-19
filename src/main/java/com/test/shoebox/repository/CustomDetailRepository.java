package com.test.shoebox.repository;

import static com.test.shoebox.entity.QProduct.product;
import static com.test.shoebox.entity.QProductImage.productImage;
import static com.test.shoebox.entity.QProductPost.productPost;
import static com.test.shoebox.entity.QCategories.categories;
import static com.test.shoebox.entity.QProductGroup.productGroup;
import static com.test.shoebox.entity.QBrand.brand;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.shoebox.entity.ProductPost;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomDetailRepository {

	private final JPAQueryFactory jpaQueryFactory;
	
	public ProductPost findById(String productPostId) {
	    
		return jpaQueryFactory
	            .selectFrom(productPost)
	            .join(productPost.product, product).fetchJoin()
	            .join(product.categories, categories).fetchJoin()
	            .join(product.productGroup, productGroup).fetchJoin()
	            .join(product.brand, brand).fetchJoin()
	            .where(productPost.productPostId.eq(Long.parseLong(productPostId)))
	            .fetchOne();
	}
	
}














