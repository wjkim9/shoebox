package com.test.shoebox.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {


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
