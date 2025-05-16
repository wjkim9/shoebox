package com.test.shoebox.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.shoebox.entity.Product;

import lombok.RequiredArgsConstructor;

import static com.test.shoebox.entity.QProductPost.productPost;

@Repository
@RequiredArgsConstructor
public class CustomRepository {
	
	private final JPAQueryFactory jpaQueryFactory;

	public List<Product> findProductByDatetime(LocalDateTime startTime, LocalDateTime endTime) {

		return jpaQueryFactory.select(productPost.product)
							.from(productPost)
							.where(productPost.postDate.between(startTime, endTime))
							.fetch();
	}
	
	
}
