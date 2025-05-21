package com.test.shoebox.service.main;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.test.shoebox.controller.main.LoginController;
import com.test.shoebox.dto.BestProductImage;
import com.test.shoebox.dto.ProductListDTO;
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
	
	
	//상품 목록
	public Page<ProductImage> getProductList(PageRequest pageRequest, String targetCustomerType, Long categoriesId,
			Long brandId, Integer startPrice, Integer endPrice) {
		
		String orderProperty = "";
		
		for(Sort.Order order : pageRequest.getSort().toList()) {
			orderProperty = order.getProperty();
		}
		
		
		
		
		if(orderProperty.equals("quantity")) {
			Page<Tuple> tuple = customRepository.findProductImageByBest(pageRequest, targetCustomerType, categoriesId, brandId, startPrice, endPrice);
			
			List<ProductListDTO> dtoList = new ArrayList<>();
			
			for(Tuple item : tuple.getContent()) {
				ProductListDTO dto = new ProductListDTO();
				
				dto.setProductId(item.get(0, Long.class));
				dto.setFileName(item.get(1, String.class));
				dto.setBrandName(item.get(2,String.class));
				dto.setProductName(item.get(3, String.class));
				dto.setProductPrice(item.get(4, Integer.class));
				dto.setProductPostId(item.get(5, Long.class));
				dto.setSalesQuantity(item.get(6, Integer.class));
				
				dtoList.add(dto);
				
				123
			}
			
			
			
		} else {
			Page<ProductImage> page = customRepository.findProductPage(pageRequest, targetCustomerType, categoriesId, brandId, startPrice, endPrice);
			
			
		}
		
		
		
		
		
		
		return page;
	}
	
}
