package com.test.shoebox.controller.main;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.shoebox.entity.EventPost;
import com.test.shoebox.entity.MainBanner;
import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductImage;
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
	
	private final MainService mainService;
	
	@GetMapping("/")
	public String index(Model model) {
		
		//메인 배너
		List<MainBanner> banner = mainBannerRepository.findAllByOrderBySortOrder();
		
		//당월 신상템
		List<ProductImage> newProductList = listProductService.getNewProductList(LocalDateTime.now());
		
		//큐레이션
		List<EventPost> eventPostList = mainService.getCurationList();
		
		
		
		model.addAttribute("banner", banner);
		
		model.addAttribute("newProductList", newProductList);
		
		model.addAttribute("eventPostList", eventPostList);
		
		return "main/index";


	@GetMapping("/")
	public String main(Model model) {

		return "main/main";
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

}
