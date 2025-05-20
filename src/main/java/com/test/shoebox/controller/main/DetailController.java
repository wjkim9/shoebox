package com.test.shoebox.controller.main;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.shoebox.entity.ProductImage;
import com.test.shoebox.entity.ProductPost;
import com.test.shoebox.entity.ProductPostImage;
import com.test.shoebox.repository.CustomDetailRepository;
import com.test.shoebox.repository.ProductImageRepository;
import com.test.shoebox.repository.ProductPostImageRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class DetailController {
	
	private final CustomDetailRepository customDetailRepository;
	private final ProductImageRepository productImageRepository;
	private final ProductPostImageRepository productPostImageRepository;
	
	
	@GetMapping("/detailpage")
	public String detailpage(@RequestParam("productPostId") String productPostId, Model model) {

		ProductPost productPost = customDetailRepository.findById(productPostId);
		List<ProductImage> productImageList = productImageRepository.findByProductOrderBySortOrderAsc(productPost.getProduct());
		List<ProductPostImage> productPostImageList = productPostImageRepository.findByProductPost(productPost);
		
		for (ProductImage pi : productImageList) {
			System.out.println(pi.getFileName());
		}
		
		for (ProductPostImage ppi : productPostImageList) {
			System.out.println(ppi.getFileName());
		}
		
		System.out.println(productPost.getProductPostId());
		System.out.println(productPost.getProduct().getProductName());
		System.out.println(productPost.getProduct().getCategories().getCategoriesName());
		System.out.println(productPost.getProduct().getProductGroup().getProductGroupName());
		System.out.println(productPost.getProduct().getBrand().getBrandName());

		return "main/detailpage";
	}
	
}
