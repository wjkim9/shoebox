package com.test.shoebox.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.test.shoebox.entity.Categories;
import com.test.shoebox.service.admin.CategoriesService;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

	@Autowired
	private CategoriesService categoriesService;

	@GetMapping("/brandcategory")
	public String brandcategory(Model model,
			@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		// 1. DB에서 카테고리 목록 가져오기
		List<Categories> categories = categoriesService.findAll();

		// 2. 모델에 담기
		model.addAttribute("categories", categories);

		// 3. Thymeleaf 템플릿 반환
		if ("XMLHttpRequest".equals(requestedWith)) {
			return "admin/category/brandcategory :: content";
		}
		return "admin/category/brandcategory";
	}
	
	@PostMapping("/upload")
	@ResponseBody
	public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
	    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
	    Path savePath = Paths.get("업로드_경로", fileName);
	    Files.copy(file.getInputStream(), savePath);
	    return fileName; // 이 값을 picName으로 저장
	}

}
