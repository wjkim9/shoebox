package com.test.shoebox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.test.shoebox.dto.BrandDTO;
import com.test.shoebox.dto.CategoriesDTO;
import com.test.shoebox.dto.EventPostDTO;
import com.test.shoebox.dto.ProductDTO;
import com.test.shoebox.dto.ProductImageDTO;
import com.test.shoebox.dto.ProductListDTO;
import com.test.shoebox.entity.Brand;
import com.test.shoebox.entity.EventPost;
import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductImage;
import com.test.shoebox.repository.BrandRepository;
import com.test.shoebox.repository.ProductRepository;
import com.test.shoebox.service.main.ListProductService;
import com.test.shoebox.service.main.MainService;

import lombok.Getter;
import lombok.Setter;


import static java.time.temporal.TemporalAdjusters.*;

@SpringBootTest
@Getter
@Setter
public class DBConnectionTests {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private ListProductService listProductService;
	
	@Autowired
	private MainService mainService;
	
	
	@Test
	public void connectionTest() {
		
		long count = productRepository.count();
		
		
		assertEquals(0, count);
		
		
	}
	
	@Test
	public void localdatetimeTests() {
		
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime starttime = today.with(firstDayOfMonth()).with(LocalTime.MIN); // 당월 1일 00:00:00
		LocalDateTime endtime = today.with(lastDayOfMonth()).with(LocalTime.MAX); // 당월 마지막날 23:59:59
		
		
		System.out.println("today: " + today);
		System.out.println("startTime: " + starttime);
		System.out.println("endtime: " + endtime);
	}
	
	
	@Test
	public void getNewProductListTests() {
		
		List<ProductImage> productList = listProductService.getNewProductList(LocalDateTime.now());
		
		
		System.out.println(productList);
		
		
	}
	
	@Test
	public void getEventPostListTests() {
		
		List<EventPost> list = mainService.getCurationList();
		
		
		System.out.println(list);
		
	}
	
	@Test
	public void findByProductImageByBrandTests() {
		
		Optional<Brand> brand = brandRepository.findById(1L);
		
		List<ProductImage> productImageList = listProductService.getRecommendProductList(brand.get(), 10);
		
		List<ProductImageDTO> dtoList = new ArrayList<>();
		for(ProductImage productImage : productImageList) {
			dtoList.add(productImage.toDTO());
			
		}
			
		
		System.out.println(dtoList);
	}
	
	@Test
	public void getBrandOnFilterTests() {
		
		List<BrandDTO> dtoList = mainService.getBrandOnFilter();
		
		assertEquals(8, dtoList.size());
		System.out.println(dtoList);
		
	}
	
	@Test
	public void getCategoriesOnFilterTests() {
		
		List<CategoriesDTO> dtoList = mainService.getCategoriesOnFilter();
		
		assertEquals(7, dtoList.size());
		System.out.println(dtoList);
		
	}
	
	@Test
	public void getProductListTests() {
		PageRequest pageRequest = PageRequest.of(0, 20, Sort.by("quantity"));
		Page<ProductListDTO> pageList = listProductService.getProductList(pageRequest, null, null, null, null, null, 1, "에어");
		
		//남자 or 남여공용
//		assertEquals(13, pageList.getContent().size());
		
		for(ProductListDTO dto : pageList.getContent()) {
			System.out.println("=======================");
			System.out.println("pageBrand: " + dto.getBrandName());
			System.out.println("pageProduct: " + dto.getProductName());
			System.out.println("=======================");
		}
		
		
		
	}
	
}
