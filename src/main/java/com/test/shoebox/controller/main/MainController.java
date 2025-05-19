package com.test.shoebox.controller.main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.shoebox.dto.BrandDTO;
import com.test.shoebox.dto.CategoriesDTO;
import com.test.shoebox.entity.Brand;
import com.test.shoebox.entity.EventPost;
import com.test.shoebox.entity.MainBanner;
import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductImage;
import com.test.shoebox.entity.ProductPost;
import com.test.shoebox.repository.BrandRepository;
import com.test.shoebox.repository.MainBannerRepository;
import com.test.shoebox.service.main.ListProductService;
import com.test.shoebox.service.main.MainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
	
	private final MainBannerRepository mainBannerRepository;
	
	private final ListProductService listProductService;
	
	private final BrandRepository brandRepository;
	
	private final MainService mainService;
	
//	@GetMapping("/")
//	public String index(Model model) {
//		
//		//메인 배너
//		List<MainBanner> banner = mainBannerRepository.findAllByOrderBySortOrder();
//		
//		//당월 신상템
//		List<ProductImage> newProductList = listProductService.getNewProductList(LocalDateTime.now());
//		
//		//큐레이션
//		List<EventPost> eventPostList = mainService.getCurationList();
//		
//		
//		
//		model.addAttribute("banner", banner);
//		
//		model.addAttribute("newProductList", newProductList);
//		
//		model.addAttribute("eventPostList", eventPostList);
//		
//		return "main/index";
//	
//	}

	@GetMapping("/")
	public String main(Model model) {

		//메인 배너
		List<MainBanner> banner = mainBannerRepository.findAllByOrderBySortOrder();
		
		//당월 신상템
		List<ProductImage> newProductList = listProductService.getNewProductList(LocalDateTime.now());
		
		//큐레이션
		// 
		List<EventPost> eventPostList = mainService.getCurationList();
		
		//추천 브랜드
		//임시 브랜드 설정
		String brandName[] = {"Nike", "FILA", "Converse", "Adidas", "CROCS", "Discovery", "New balance", "asics"};
		int fetchSize = 8;
		
		List<Map<String, Object>> rcmdPrdtList = new ArrayList<>();
		
		for(String item : brandName) {
			Optional<Brand> brand = brandRepository.findByBrandName(item);
			
			if(brand.isPresent()) {
				Map<String, Object> itemList = new HashMap<>();
				
				itemList.put("brandName", brand.get().getBrandName());
				itemList.put("productList", listProductService.getRecommendProductList(brand.get(), fetchSize));
				
				rcmdPrdtList.add(itemList);
			}
		}
		
		model.addAttribute("banner", banner);
		
		model.addAttribute("newProductList", newProductList);
		
		model.addAttribute("eventPostList", eventPostList);
		
		model.addAttribute("rcmdPrdtList", rcmdPrdtList);
		
		
		
		return "main/main";
	}
	
	@GetMapping(value = "/listProduct")
	public String listProduct(Model model) {
		
		//필터로 보여줄 항목
		//브랜드
		List<BrandDTO> brandOnFilterList = mainService.getBrandOnFilter();
		//카테고리
		List<CategoriesDTO> categoriesOnFilterList = mainService.getCategoriesOnFilter();
		
		
		model.addAttribute("brandOnFilterList", brandOnFilterList);
		model.addAttribute("categoriesOnFilterList", categoriesOnFilterList);
		
		
		
		return "main/listProduct";
	}
	

	@GetMapping("/login")
	public String login(Model model) {

		return "main/login";
	}

	@GetMapping("/findid")
	public String findid(Model model) {

		return "main/findid";
	}

	@GetMapping("/findpw")
	public String findpw(Model model) {

		return "main/findpw";
	}

	@GetMapping("/register")
	public String register(Model model) {

		return "main/register";
	}
	@GetMapping("/category")
	public String category(Model model) {

		return "main/category";
	}
	@GetMapping("/detailpage")
	public String detailpage(Model model) {

		return "main/detailpage";
	}

}
