package com.test.shoebox.controller.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageController {
	
	@GetMapping("/")
	public String index(Model model) {
		
		return "mypage/index";
	}
	
}
