package com.test.shoebox.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

    /**
     * 상품 리스트 조회 / 등록 / 수정 / 삭제 / 재고관리 페이지
     */
    @GetMapping("/register")
    public String productRegister() {
        // TODO: Model에 상품 목록, 페이징 정보 등 필요한 데이터 추가
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
