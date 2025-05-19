package com.test.shoebox.controller.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageController {
	
	@GetMapping("/mypage")
	public String mypage(Model model) {
		
		return "mypage/mypage";
	}
	@GetMapping("/cart")
	public String cart(Model model) {
		
		return "mypage/cart";
	}
	
}
