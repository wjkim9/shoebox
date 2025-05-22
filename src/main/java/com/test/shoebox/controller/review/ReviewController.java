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
	
	//리뷰작성
	@GetMapping("/create")
	public String review_create(Model model) {
		
		return "review/review_create";
	}
	
	
	// 리뷰보기(정적)
	@GetMapping("/static")
	public String static_review(Model model) {
		
		return "review/static_review";
	}
	//리뷰작성(정적)
	@GetMapping("/static/create")
	public String static_review_create(Model model) {
		
		return "review/static_review_create";
	}
	//개인리뷰 확인, 수정, 삭제(정적)
		@GetMapping("/static/user_review")
		public String static_user_review(Model model) {
			
			return "review/static_user_review";
	}
	//개인리뷰 수정(정적)
	@GetMapping("/static/update")
	public String static_review_update(Model model) {
		
		return "review/static_review_update";
	}
	

	//개인리뷰 확인 0개(정적)
	@GetMapping("/static/user_review/none")
	public String static_user_review_none(Model model) {
		
		return "review/static_user_review_none";
	}
	// 리뷰보기 0개 (정적)
		@GetMapping("/static/none")
		public String static_review_none(Model model) {
			
			return "review/static_review_none";
		}

	
}
