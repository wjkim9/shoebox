package com.test.shoebox.controller.admin;

import com.test.shoebox.dto.ProductDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

    /**
     * 상품 리스트 조회 / 등록 / 수정 / 삭제 / 재고관리 페이지
     */
    @GetMapping("/register")
    public String productRegister(Model model,
                                  @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {

        // 테스트용 더미 상품 데이터
        List<Map<String, Object>> products = new ArrayList<>();

        Map<String, Object> product = new HashMap<>();
        product.put("id", 1);
        product.put("productCode", "TEST-001");
        product.put("productName", "테스트 상품");
        product.put("modelNumber", "MODEL-001");
        product.put("brand", "나이키");
        product.put("category", "신발");
        product.put("price", 129000);
        product.put("discountRate", 10);
        product.put("totalStock", 15);
        product.put("status", "ACTIVE");
        product.put("createdAt", new Date());
        product.put("mainImageUrl", "/images/test.jpg");

        products.add(product);

        model.addAttribute("products", products);

        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/product/register :: content";
        }
        return "admin/product/register";
    }

    /**
     * 할인 관리 페이지
     */
    @GetMapping("/discount")
    public String discountManagement(Model model) {
        // TODO: 할인 정책 및 리스트 필요 시 테스트 데이터 삽입
        return "admin/product/discount";
    }

    /**
     * 상품 게시물 등록/수정/삭제 페이지
     */
    @GetMapping("/post/register")
    public String productPostRegister(Model model) {
        // TODO: 게시물 관련 테스트 데이터 필요 시 삽입
        return "admin/product/post/register";
    }

    @GetMapping("/add")
    public String showAddPage(Model model) {
        return "admin/product/add"; // templates/admin/product/add.html
    }




//    @GetMapping("/edit/{id}")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
            // 더미 DTO 생성
            ProductDTO dummy = ProductDTO.builder()
                    .productId(id)
                    .productName("아디다스 울트라부스트")
                    .productPrice(179000)
                    .discountRate(15.0)
                    .targetCustomerType("MEN")
                    .productRegisterDate(LocalDateTime.now().minusDays(5))
                    .brandId(2L)
                    .categoriesId(3L)
                    .productGroupId(1L)
                    .build();

            // 더미 목록 (select option용)
            List<Map<String, Object>> brandList = List.of(
                    Map.of("id", 1L, "name", "NIKE"),
                    Map.of("id", 2L, "name", "ADIDAS"),
                    Map.of("id", 3L, "name", "NEW BALANCE")
            );

            List<Map<String, Object>> categoryList = List.of(
                    Map.of("id", 1L, "name", "러닝화"),
                    Map.of("id", 2L, "name", "샌들"),
                    Map.of("id", 3L, "name", "스니커즈")
            );

            List<Map<String, Object>> groupList = List.of(
                    Map.of("id", 1L, "name", "신상품"),
                    Map.of("id", 2L, "name", "인기상품")
            );

            model.addAttribute("product", dummy);
            model.addAttribute("brands", brandList);
            model.addAttribute("categories", categoryList);
            model.addAttribute("groups", groupList);
            return "admin/product/edit"; // Thymeleaf 템플릿
        }

        @PostMapping("/update")
        public String updateDummy(@ModelAttribute ProductDTO form) {
            System.out.println("===== 상품 수정 요청 (테스트용) =====");
            System.out.println("상품명: " + form.getProductName());
            System.out.println("브랜드 ID: " + form.getBrandId());
            System.out.println("카테고리 ID: " + form.getCategoriesId());
            System.out.println("상품군 ID: " + form.getProductGroupId());
            System.out.println("대상 성별: " + form.getTargetCustomerType());
            System.out.println("가격: " + form.getProductPrice());
            System.out.println("할인율: " + form.getDiscountRate());

            return "redirect:/admin/product/edit/" + form.getProductId();
        }


}
