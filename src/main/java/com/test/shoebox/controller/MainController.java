package com.test.shoebox.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.shoebox.entity.MainBanner;
import com.test.shoebox.entity.Product;
import com.test.shoebox.repository.MainBannerRepository;
import com.test.shoebox.service.ListProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
	
	private final MainBannerRepository mainBannerRepository;
	
	private final ListProductService listProductService;
	
	@GetMapping("/")
	public String index(Model model) {
		
		//메인 배너
		List<MainBanner> banner = mainBannerRepository.findAllByOrderBySortOrder();
		
		//당월 신상템
 
		List<Product> newProductList = listProductService.getNewProductList(LocalDateTime.now());
		
		
		
		
		
		model.addAttribute("banner", banner);
		
		return "main/index";
	}
	
}
