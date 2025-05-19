package com.test.shoebox.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/main")
public class DetailController {
	
	@GetMapping("/detail")
	public String detail(@RequestParam("productpost_id") Long productPostId, Model model) {
		
		
		
		return "main/detail";
	}
	
}
