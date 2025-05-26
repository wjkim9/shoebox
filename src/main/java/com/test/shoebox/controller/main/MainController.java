package com.test.shoebox.controller.main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.shoebox.dto.BrandDTO;
import com.test.shoebox.dto.CategoriesDTO;
import com.test.shoebox.dto.ProductListDTO;
import com.test.shoebox.entity.Brand;
import com.test.shoebox.entity.Categories;
import com.test.shoebox.entity.EventPost;
import com.test.shoebox.entity.MainBanner;
import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductImage;
import com.test.shoebox.entity.ProductPost;
import com.test.shoebox.repository.BrandRepository;
import com.test.shoebox.repository.CategoriesRepository;
import com.test.shoebox.repository.CustomDetailRepository;
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
	
	private final CategoriesRepository categoriesRepository;
	
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
		
		int offset = 0;
		int fetchSize = 8;

		//메인 배너
		List<MainBanner> banner = mainBannerRepository.findAllByOrderBySortOrder();
		
		//당월 신상템
		List<ProductImage> newProductList = listProductService.getNewProductList(LocalDateTime.now(), offset, fetchSize);
		
		//큐레이션
		// 
		List<EventPost> eventPostList = mainService.getCurationList();
		
		//추천 브랜드
		//임시 브랜드 설정
		String brandName[] = {"Nike", "FILA", "Converse", "Adidas", "CROCS", "Discovery", "New balance", "asics"};
		
		
		List<Map<String, Object>> rcmdPrdtList = new ArrayList<>();
		
		for(String item : brandName) {
			Optional<Brand> brand = brandRepository.findByBrandName(item);
			
			if(brand.isPresent()) {
				Map<String, Object> itemList = new HashMap<>();
				
				itemList.put("brandName", brand.get().getBrandName());
				itemList.put("productList", listProductService.getRecommendProductList(brand.get(), offset, fetchSize));
				
				rcmdPrdtList.add(itemList);
			}
		}
		
		model.addAttribute("banner", banner);
		
		model.addAttribute("newProductList", newProductList);
		
		model.addAttribute("eventPostList", eventPostList);
		
		model.addAttribute("rcmdPrdtList", rcmdPrdtList);
		
		
		
		return "main/main";
	}
	//정렬 방법
	//베스트상품순: orderBestDesc
	//신상품순: orderNewDesc
	//할인율 높은순: orderDiscountDesc
	//상품평순: orderReviewDesc
	//낮은가격순: orderPriceAsc
	//높은가격순: orderPriceDesc
	
	@GetMapping(value = "listProduct")
	public String listProduct(Model model, 
		@PageableDefault(page = 0, size = 20) Pageable pageable,
		@RequestParam(value = "order", required = false, defaultValue = "orderBestDesc") String order,
		@RequestParam(value = "targetCustomerType", required = false) String targetCustomerType,
		@RequestParam(value = "categoriesId", required = false) Long categoriesId, 
		@RequestParam(value = "brandId", required = false) Long brandId, 
		@RequestParam(value = "startPrice", required = false) Integer startPrice, 
		@RequestParam(value = "endPrice", required = false) Integer endPrice,
		@RequestParam(value = "search", required = false, defaultValue = "0") Integer search,
		@RequestParam(value = "searchWord", required = false) String searchWord,
		@RequestParam(value = "menu", required = false) String menu,
		@RequestParam(value = "filter", required = false) Integer filter,
		@RequestParam(value = "newItem", required = false) Integer newItem
		
	) {
		
		
		
		PageRequest pageRequest = null;
		
		//파라미터에 따라 pageRequest 생성
		if(order.equals("orderBestDesc")) {
			
			pageRequest = PageRequest.of(pageable.getPageNumber(), 20, Sort.by(Direction.DESC, "quantity"));
			
		} else if(order.equals("orderNewDesc")) {
			
			pageRequest = PageRequest.of(pageable.getPageNumber(), 20, Sort.by(Direction.DESC, "postDate"));
			
		} else if(order.equals("orderDiscountDesc")) {
			
			pageRequest = PageRequest.of(pageable.getPageNumber(), 20, Sort.by(Direction.DESC, "discountRate"));
			
		} else if(order.equals("orderReviewDesc")) {
			//손봐야함, count
			pageRequest = PageRequest.of(pageable.getPageNumber(), 20, Sort.by(Direction.DESC, "reviewCount"));
			
		} else if(order.equals("orderPriceAsc")) {
			
			pageRequest = PageRequest.of(pageable.getPageNumber(), 20, Sort.by(Direction.ASC, "productPrice"));
			
		} else if(order.equals("orderPriceDesc")) {
			pageRequest = PageRequest.of(pageable.getPageNumber(), 20, Sort.by(Direction.DESC, "productPrice"));
		} else {
			
			pageRequest = PageRequest.of(pageable.getPageNumber(), 20, Sort.by(Direction.DESC, "postDate"));
		}
		
		Optional<Integer> optNewItem = Optional.ofNullable(newItem);
		
		
		//필터로 보여줄 항목
		//브랜드
		List<BrandDTO> brandOnFilterList = mainService.getBrandOnFilter();
		//카테고리
		List<CategoriesDTO> categoriesOnFilterList = mainService.getCategoriesOnFilter();
		
		
		
		//상품페이지 경로
		Optional<Categories> categories = categoriesRepository.findCategoriesNameByCategoriesId(categoriesId);
		Optional<Brand> brand = brandRepository.findBrandNameByBrandId(brandId);
		
		
		StringBuilder sb = new StringBuilder();
		StringBuilder sbForSort = new StringBuilder();
		
		StringBuilder sbForQueryString = new StringBuilder();
		
		
		pageRequest.getSort().forEach(item -> sbForSort.append(item.getProperty() + "," + item.getDirection()));
		
		sbForQueryString.append("""
				<a href="/main/listProduct?page=%d&size=20&sort=%s
				""");
		if(order != null) {
			sbForQueryString.append("&order=%s".formatted(order));
		}
		
		if(targetCustomerType != null) {
			sbForQueryString.append("&targetCustomerType=%s".formatted(targetCustomerType));
		}
		
		if(categoriesId != null) {
			sbForQueryString.append("&categoriesId=%d".formatted(categoriesId));
		}
		
		if(brandId != null) {
			sbForQueryString.append("&brandId=%d".formatted(brandId));
		}
		
		if(startPrice != null) {
			sbForQueryString.append("&startPrice=%d".formatted(startPrice));
		}
		
		if(endPrice != null) {
			sbForQueryString.append("&endPrice=%d".formatted(endPrice));
		}
		
		if(search != null) {
			sbForQueryString.append("&search=%d".formatted(search));
		}
		
		if(searchWord != null) {
			sbForQueryString.append("&searchWord=%s".formatted(searchWord));
		}
		
		if(menu != null) {
			sbForQueryString.append("&menu=%s".formatted(menu));
		}
		
		
		sbForQueryString.append("\">%d</a>");
		
		
		System.out.println("sbForSort: " + sbForSort.toString());
		
		
		
		
		//상품목록
		Page<ProductListDTO> productList = listProductService.getProductList(pageRequest, targetCustomerType, categoriesId, brandId, startPrice, endPrice, search, searchWord, optNewItem);
		
		for(int i=0; i<productList.getTotalPages(); i++) {
			sb.append(sbForQueryString.toString().formatted(i, sbForSort, i+1));
		}
		
		System.out.println("TotalPages: " + productList.getTotalPages());
		System.out.println("Size: " + productList.getSize());
		System.out.println("Size: " + productList.getSize());
		System.out.println("Offset: " + pageRequest.getOffset());
		System.out.println("sb: " + sb);
		
		
		//상품경로
		model.addAttribute("targetCustomerType", targetCustomerType);
		
		
		//필터 출력 항목
		model.addAttribute("brandOnFilterList", brandOnFilterList);
		model.addAttribute("categoriesOnFilterList", categoriesOnFilterList);
		
		//필터 상태 유지 항목
		model.addAttribute("filter", filter);
		model.addAttribute("categoriesId", categoriesId);
		model.addAttribute("brandId", brandId);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
		
		//상품 정보
		model.addAttribute("productList", productList);
		
		//검색 유무
		model.addAttribute("search", search);
		model.addAttribute("searchWord", searchWord);
		
		if(categories.isPresent()) {
			model.addAttribute("categories", categories.get().toDTO());
		} else {
			model.addAttribute("categories", Categories.builder().categoriesName("ALL").build());
		}
		
		if(brand.isPresent()) {
			model.addAttribute("brand", brand.get().toDTO());
		} else {
			model.addAttribute("brand", Brand.builder().brandName("ALL").build());
		}
		
		//메뉴 진입 경로
		model.addAttribute("menu", menu);
		
		model.addAttribute("newItem", newItem);
		
		//페이지네이션
		model.addAttribute("sb", sb);
		
		
		return "main/category";
	}


	@GetMapping("/findid")
	public String findid(Model model) {

		return "main/findid";
	}

	@GetMapping("/findpw")
	public String findpw(Model model) {

		return "main/findpw";
	}

	@GetMapping("/category")
	public String category(Model model) {

		return "main/category";
	}

}
