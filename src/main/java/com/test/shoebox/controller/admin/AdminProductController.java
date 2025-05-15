package com.test.shoebox.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

    /**
     * 상품 리스트 조회 / 등록 / 수정 / 삭제 / 재고관리 페이지
     */
    @GetMapping("/register")
    public String productRegister(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        model.addAttribute("title", "상품 등록");
        // 필요하다면 model.addAttribute("categories", categoryService.findAll());
        // AJAX 요청일 땐 content fragment만 반환
        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/product/register :: content";
        }
        return "admin/product/register";
    }

    /**
     * 할인 관리 페이지
     */
    @GetMapping("/discount")
    public String discountManagement() {
        // TODO: Model에 할인 정책, 할인 상품 리스트 등 추가
        return "admin/product/discount";
    }

    /**
     * 상품 게시물 조회(등록/수정/삭제) 페이지
     */
    @GetMapping("/post/register")
    public String productPostRegister() {
        // TODO: Model에 게시물 목록, 쓰기/수정 폼 데이터 등 추가
        return "admin/product/post/register";
    }

}
