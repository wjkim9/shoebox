package com.test.shoebox.service.main;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.test.shoebox.controller.main.LoginController;
import com.test.shoebox.entity.Brand;
import com.test.shoebox.entity.ProductImage;
import com.test.shoebox.entity.ProductPost;
import com.test.shoebox.repository.CustomRepository;
import com.test.shoebox.repository.ProductImageRepository;
import com.test.shoebox.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListProductService {

    private final LoginController loginController;

	private final ProductImageRepository productImageRepository;
	private final ProductRepository productRepository;
	
	private final CustomRepository customRepository;


	//당월 신상품
	public List<ProductImage> getNewProductList(LocalDateTime now) {
		
		LocalDateTime startTime = now.with(firstDayOfMonth()).with(LocalTime.MIN); // 당월 1일 00:00:00
		LocalDateTime endTime = now.with(lastDayOfMonth()).with(LocalTime.MAX); // 당월 마지막날 23:59:59
		
		List<ProductImage> newProductImageList = customRepository.findProductByDatetime(startTime, endTime);
		
		return newProductImageList;
	}
	
	//추천 브랜드 상품(인기상품 -> 조회수)
	public List<ProductImage> getRecommendProductList(Brand brand, Integer maxFetch) {
		
		List<ProductImage> rcmdPrdtList = customRepository.findByProductImageByBrand(brand, maxFetch);
		
		return rcmdPrdtList;
	}
	
}
