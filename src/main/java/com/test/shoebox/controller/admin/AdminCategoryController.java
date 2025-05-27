package com.test.shoebox.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String brandcategory(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		List<Categories> categories = categoriesService.findAll();
		model.addAttribute("categories", categories);
		if ("XMLHttpRequest".equals(requestedWith)) {
			return "admin/category/brandcategory :: content";
		}

		return "admin/category/brandcategory";
	}

	@PostMapping("/upload")
	@ResponseBody
	public String uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "categoriesId", required = false) Long categoriesId) throws IOException {
		String uploadDir = "src/main/resources/static/admin/images";
		Path dirPath = Paths.get(uploadDir);
		if (!Files.exists(dirPath)) {
			Files.createDirectories(dirPath);
		}
		String fileName;
		if (categoriesId != null) {
			fileName = categoriesId + "_" + file.getOriginalFilename();
		} else {
			fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		}
		Path savePath = dirPath.resolve(fileName);
		Files.copy(file.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);
		System.out.println("location:" + savePath);
		return fileName;
	}

	@PostMapping("/update")
	@ResponseBody
	public String updateCategory(@RequestParam("id") Long id, @RequestParam("categoriesName") String categoriesName,
			@RequestParam(value = "picName", required = false) String picName) {
		Categories category = categoriesService.findById(id).orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));
		category.setCategoriesName(categoriesName);

		// 이미지가 수정되지 않았으면 기존 이미지 파일명 유지
		if (picName != null && !picName.trim().isEmpty()) {
			category.setPicName(picName);
		}
		// else: 기존 picName 유지

		categoriesService.save(category);
		return "success";
	}

	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteCategory(@PathVariable("id") Long id) {
		try {
			categoriesService.deleteById(id);
			return "success";
		} catch (EmptyResultDataAccessException e) {
			return "not_found";
		} catch (Exception e) {
			return "fail";
		}
	}

	@PostMapping("/save")
	public String saveCategory(@RequestParam("categoriesName") String categoriesName,
			@RequestParam(value = "picName", required = false) String picName) {
		Categories category = new Categories();
		category.setCategoriesName(categoriesName);
		category.setPicName(picName);
		categoriesService.save(category);
		return "redirect:/admin/category/brandcategory";
	}
}
