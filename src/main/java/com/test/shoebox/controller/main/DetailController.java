package com.test.shoebox.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.shoebox.entity.ProductPost;
import com.test.shoebox.repository.CustomDetailRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class DetailController {
	
	private final CustomDetailRepository customDetailRepository;
	
	@GetMapping("/detail")
	public String detail(@RequestParam("productpostId") Long productPostId, Model model) {
		
		ProductPost pp = customDetailRepository.findById(productPostId);
		
		System.out.println(pp.getProductPostId());
		System.out.println(pp.getProduct());
		System.out.println(pp.getProduct().getCategories());
		System.out.println(pp.getProduct().getProductGroup());
		System.out.println(pp.getProduct().getBrand());
		
		return "main/main";
	}
	
}
