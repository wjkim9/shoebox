package com.test.shoebox.repository;



import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.shoebox.entity.Brand;
import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductImage;
import com.test.shoebox.entity.ProductPost;
import com.test.shoebox.entity.QProductPost;

import lombok.RequiredArgsConstructor;
import static com.test.shoebox.entity.QProduct.product;
import static com.test.shoebox.entity.QProductImage.productImage;
import static com.test.shoebox.entity.QProductPost.productPost;
import static com.test.shoebox.entity.QCategories.categories;
import static com.test.shoebox.entity.QBrand.brand;
import static com.test.shoebox.entity.QProductStock.productStock;
import static com.test.shoebox.entity.QProductStockOrder.productStockOrder;
import static com.test.shoebox.entity.QOrderReview.orderReview;




@Repository
@RequiredArgsConstructor
public class CustomRepository {
	
	private final JPAQueryFactory jpaQueryFactory;

	public List<ProductImage> findProductByDatetime(LocalDateTime startTime, LocalDateTime endTime, Integer offset, Integer fetchSize) {

		return jpaQueryFactory.select(productImage)
							.from(productImage)
							.innerJoin(productImage.product, product)
							.innerJoin(product.productPost, productPost)
							.where(productPost.postDate.between(startTime, endTime).and(productImage.sortOrder.eq(1)))
							.offset(offset)
							.limit(fetchSize)
							.fetch();
	}

	public List<ProductImage> findByProductImageByBrand(Brand brand, Integer offset,Integer fetchSize) {
		
		return jpaQueryFactory.select(productImage)
								.from(productImage)
								.innerJoin(productImage.product, product)
								.innerJoin(product.productPost, productPost)
								.where(product.brand.eq(brand).and(productImage.sortOrder.eq(1)))
								.offset(offset)
								.limit(fetchSize)
								.orderBy(productPost.viewCount.desc())
								.fetch();
	}
	
	
	
	public Page<ProductImage> findProductPage(PageRequest pageRequest, String targetCustomerType,
			Long categoriesId, Long brandId, Integer startPrice, Integer endPrice, Integer search, String searchWord, Optional<Integer> newItem) {
		
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
		
		if(newItem.isPresent()){
			if(newItem.get() == 1) {
				
				LocalDateTime startTime = LocalDateTime.now().with(firstDayOfMonth()).with(LocalTime.MIN); // 당월 1일 00:00:00
				LocalDateTime endTime = LocalDateTime.now().with(lastDayOfMonth()).with(LocalTime.MAX); // 당월 마지막날 23:59:59
				
				builder.and(productPost.postDate.between(startTime, endTime));
			}
		}
		
		
		
		
		if(search == 1 && searchWord != null) {
			
			builder.and(product.productName.lower().like("%" + searchWord.trim().toLowerCase() + "%")
						.or(brand.brandName.lower().like("%" + searchWord.trim().toLowerCase() + "%")));
		}
		
		
		
		List<String> sortPropertyList = new ArrayList<>(); 
		pageRequest.getSort().forEach(item -> {
			sortPropertyList.add(item.getProperty());
		});
		
		String sortProperty = sortPropertyList.get(0).toString();
				
		
		List<ProductImage> content = jpaQueryFactory
									.select(productImage)
									.from(productImage)
									.innerJoin(productImage.product, product)
									.innerJoin(product.productPost, productPost)
									.innerJoin(product.brand, brand)
									.where(builder)
									.orderBy(getOrderSpecifiers(pageRequest.getSort()))
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
		
		if(Optional.ofNullable(count).isEmpty()) {
			count = 0L;
		}
		
		return new PageImpl<>(content, pageRequest, count);
	
	}
	
	
	private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
		List<OrderSpecifier<?>> orders = new ArrayList<>();
		
		
		for(Sort.Order order: sort) {
			Order direction = order.isAscending() ? Order.ASC : Order.DESC;
			
			if(order.getProperty().equals("discountRate") || order.getProperty().equals("productPrice")) {
				PathBuilder<Product> productImagePathBuilder = new PathBuilder<>(product.getType(), product.getMetadata());
				orders.add(new OrderSpecifier<>(direction, productImagePathBuilder.getString(order.getProperty())));
			} else if(order.getProperty().equals("postDate")) {
				PathBuilder<ProductPost> productImagePathBuilder = new PathBuilder<>(productPost.getType(), productPost.getMetadata());
				orders.add(new OrderSpecifier<>(direction, productImagePathBuilder.getString(order.getProperty())));
			} 
			
			
			
			
		}
		
		return orders.toArray(new OrderSpecifier[0]);
	}

	public Page<Tuple> findProductImageByBest(PageRequest pageRequest, String targetCustomerType, Long categoriesId,
			Long brandId, Integer startPrice, Integer endPrice, Integer search, String searchWord, Optional<Integer> newItem) {
		
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
		
		if(newItem.isPresent()){
			if(newItem.get() == 1) {
				
				LocalDateTime startTime = LocalDateTime.now().with(firstDayOfMonth()).with(LocalTime.MIN); // 당월 1일 00:00:00
				LocalDateTime endTime = LocalDateTime.now().with(lastDayOfMonth()).with(LocalTime.MAX); // 당월 마지막날 23:59:59
				
				builder.and(productPost.postDate.between(startTime, endTime));
			}
		}
		
		
		
		
		if(search == 1 && searchWord != null) {
			
			builder.and(product.productName.lower().like("%" + searchWord.trim().toLowerCase() + "%")
						.or(brand.brandName.lower().like("%" + searchWord.trim().toLowerCase() + "%")));
		}
		
		
		QProductPost productPost1 =  QProductPost.productPost;
		QProductPost productPost2 =  new QProductPost("productPost2");
		
		List<Tuple> content = jpaQueryFactory.select(product.productId, productImage.fileName, brand.brandName, product.productName, product.productPrice, productPost1.productPostId, productStockOrder.quantity.coalesce(0).sum())
												.from(productImage)
												.innerJoin(productImage.product, product)
												.innerJoin(product.brand, brand)
												.innerJoin(product.productPost, productPost1)
												.leftJoin(product.productStock, productStock)
												.leftJoin(productStock.productStockOrder, productStockOrder)
												.where(productPost1.postDate.eq(JPAExpressions.select(productPost2.postDate.max())
																							.from(productPost2)
																							.where(productPost2.productPostId.eq(productPost1.productPostId))
													
													).and(builder)
														
												)
												.offset(pageRequest.getOffset())
												.limit(pageRequest.getPageSize())
												.groupBy(product.productId, productImage.fileName, brand.brandName, product.productName, product.productPrice, productPost1.productPostId)
												.orderBy(productStockOrder.quantity.coalesce(0).sum().desc())
												.fetch();
		
		List<Tuple> allContent = jpaQueryFactory.select(product.productId, productImage.fileName, brand.brandName, product.productName, product.productPrice, productPost1.productPostId, productStockOrder.quantity.coalesce(0).sum())
												.from(productImage)
												.innerJoin(productImage.product, product)
												.innerJoin(product.brand, brand)
												.innerJoin(product.productPost, productPost1)
												.leftJoin(product.productStock, productStock)
												.leftJoin(productStock.productStockOrder, productStockOrder)
												.where(productPost1.postDate.eq(JPAExpressions.select(productPost2.postDate.max())
																							.from(productPost2)
																							.where(productPost2.productPostId.eq(productPost1.productPostId))
													
													).and(builder)
														
												)
												.groupBy(product.productId, productImage.fileName, brand.brandName, product.productName, product.productPrice, productPost.productPostId)
												.fetch();
		
												
		Long count = (long)allContent.size();
		
		if(Optional.ofNullable(count).isEmpty()) {
			count = 0L;
		}
		
		
		
		return new PageImpl<>(content, pageRequest, count);
	}

	public Page<Tuple> findProductImageByReviewCount(PageRequest pageRequest, String targetCustomerType,
			Long categoriesId, Long brandId, Integer startPrice, Integer endPrice, Integer search, String searchWord, Optional<Integer> newItem) {
		
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
		
		if(newItem.isPresent()){
			if(newItem.get() == 1) {
				
				LocalDateTime startTime = LocalDateTime.now().with(firstDayOfMonth()).with(LocalTime.MIN); // 당월 1일 00:00:00
				LocalDateTime endTime = LocalDateTime.now().with(lastDayOfMonth()).with(LocalTime.MAX); // 당월 마지막날 23:59:59
				
				builder.and(productPost.postDate.between(startTime, endTime));
			}
		}
		
		
		if(search == 1 && searchWord != null) {
			
			builder.and(product.productName.lower().like("%" + searchWord.trim().toLowerCase() + "%")
						.or(brand.brandName.lower().like("%" + searchWord.trim().toLowerCase() + "%")));
		}
		
		QProductPost productPost1 =  QProductPost.productPost;
		QProductPost productPost2 =  new QProductPost("productPost2");
		
		
		List<Tuple> content = jpaQueryFactory.select(product.productId, productImage.fileName, brand.brandName, product.productName, product.productPrice, productPost1.productPostId, orderReview.orderReviewId.count())
												.from(productImage)
												.innerJoin(productImage.product, product)
												.innerJoin(product.brand, brand)
												.innerJoin(product.productPost, productPost1)
												.leftJoin(product.productStock, productStock)
												.leftJoin(productStock.productStockOrder, productStockOrder)
												.leftJoin(productStockOrder.orderReview, orderReview)
												.where(productPost1.postDate.eq(JPAExpressions.select(productPost2.postDate.max())
																							.from(productPost2)
																							.where(productPost2.productPostId.eq(productPost1.productPostId))
													
													).and(builder)
														
												)
												.offset(pageRequest.getOffset())
												.limit(pageRequest.getPageSize())
												.groupBy(product.productId, productImage.fileName, brand.brandName, product.productName, product.productPrice, productPost1.productPostId)
												.orderBy(orderReview.orderReviewId.count().desc())
												.fetch();
		
		List<Tuple> allContent = jpaQueryFactory.select(product.productId, productImage.fileName, brand.brandName, product.productName, product.productPrice, productPost1.productPostId, orderReview.orderReviewId.count())
												.from(productImage)
												.innerJoin(productImage.product, product)
												.innerJoin(product.brand, brand)
												.innerJoin(product.productPost, productPost1)
												.leftJoin(product.productStock, productStock)
												.leftJoin(productStock.productStockOrder, productStockOrder)
												.leftJoin(productStockOrder.orderReview, orderReview)
												.where(productPost1.postDate.eq(JPAExpressions.select(productPost2.postDate.max())
																							.from(productPost2)
																							.where(productPost2.productPostId.eq(productPost1.productPostId))
													
													).and(builder)
														
												)
												.groupBy(product.productId, productImage.fileName, brand.brandName, product.productName, product.productPrice, productPost1.productPostId)
												.fetch();
		
												
		Long count = (long)allContent.size();
		
		if(Optional.ofNullable(count).isEmpty()) {
			count = 0L;
		}
		
		return new PageImpl<>(content, pageRequest, count);
	}
	
	
}
 