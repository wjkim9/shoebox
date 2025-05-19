package com.test.shoebox.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.shoebox.entity.Brand;
import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductImage;
import com.test.shoebox.entity.ProductPost;

import lombok.RequiredArgsConstructor;
import static com.test.shoebox.entity.QProduct.product;
import static com.test.shoebox.entity.QProductPost.productPost;
import static com.test.shoebox.entity.QProductImage.productImage;
import static com.test.shoebox.entity.QCategories.categories;
import static com.test.shoebox.entity.QBrand.brand;
import static com.test.shoebox.entity.QProductStock.productStock;
import static com.test.shoebox.entity.QProductStockOrder.productStockOrder;
import static com.test.shoebox.entity.QOrderReview.orderReview;



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

	public Page<ProductImage> findProductPage(PageRequest pageRequest, String targetCustomerType,
			Long categoriesId, Long brandId, Integer startPrice, Integer endPrice) {
		
		BooleanBuilder builder = new BooleanBuilder();
		
		//첫번째 이미지만
		builder.and(productImage.sortOrder.eq(1));
		
		if(targetCustomerType != null) {
			if(targetCustomerType.equals("men") || targetCustomerType.equals("women")) {
				builder.and((product.targetCustomerType.eq("unisex").or(product.targetCustomerType.eq(targetCustomerType))));
			} else {
				builder.and(product.targetCustomerType.eq(targetCustomerType));
			}
		}
		
		if(categoriesId != null) {
			builder.and(categories.categoriesId.eq(categoriesId));
		}
		
		if(brandId != null) {
			builder.and(brand.brandId.eq(brandId));
		}
		
		if(startPrice != null) {
			builder.and(product.productPrice.goe(startPrice));
		}
		
		if(startPrice != null) {
			builder.and(product.productPrice.loe(endPrice));
		}
		
		List<ProductImage> content = jpaQueryFactory
									.select(productImage)
									.from(productImage)
									.innerJoin(productImage.product, product)
									.innerJoin(product.productPost, productPost)
									.innerJoin(product.brand, brand)
									.where(builder)
									.offset(pageRequest.getOffset())
									.limit(pageRequest.getPageSize())
									.fetch();
		
		Long count = jpaQueryFactory.select(productImage.count())
									.from(productImage)
									.innerJoin(productImage.product, product)
									.innerJoin(product.productPost, productPost)
									.innerJoin(product.brand, brand)
									.where(builder)
									.fetchOne();
		
		
		return new PageImpl<>(content, pageRequest, count);
	
	}
	
	
}
 