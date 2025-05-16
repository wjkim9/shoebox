package com.test.shoebox.controller.review;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	@GetMapping("/")
	public String review(Model model) {
		
		return "review/review";
	}
	
	@GetMapping("/create")
	public String review_create(Model model) {
		
		return "review/review_create";
	}
	
	
	// 보기전용 (예시)
	@GetMapping("/static")
	public String static_review(Model model) {
		
		return "review/static_review";
	}
	@GetMapping("/static/create")
	public String static_review_create(Model model) {
		
		return "review/static_review_create";
	}
}
