package com.test.shoebox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.shoebox.entity.Product;
import com.test.shoebox.repository.ProductRepository;
import com.test.shoebox.service.ListProductService;

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
	private ListProductService listProductService;
	
	
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
		
		List<Product> productList = listProductService.getNewProductList(LocalDateTime.now());
		
		
		System.out.println(productList);
		
		
	}
}
