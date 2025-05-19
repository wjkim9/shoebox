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

    // ✅ 상품 등록 페이지
    @GetMapping("/add")
    public String showAddPage(Model model) {
        return "admin/product/add";
    }

    // ✅ 상품 수정 폼
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
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

        return "admin/product/edit";
    }

    // ✅ 상품 수정 처리
    @PostMapping("/update")
    public String updateDummy(@ModelAttribute ProductDTO form) {
        System.out.println("===== 상품 수정 요청 =====");
        System.out.println("상품명: " + form.getProductName());
        return "redirect:/admin/product/edit/" + form.getProductId();
    }

    // ✅ 상품 목록 페이지 리디렉션
    @GetMapping("/register")
    public String redirectToProductList() {
        return "redirect:/admin/products";
    }

    // ✅ 상품 목록 페이지
    @GetMapping("/products")
    public String showProductList(@RequestParam(required = false) String registrationDateStart,
                                  @RequestParam(required = false) String registrationDateEnd,
                                  @RequestParam(required = false) String productStatus,
                                  @RequestParam(required = false) String category,
                                  @RequestParam(required = false) String brand,
                                  @RequestParam(required = false) String searchType,
                                  @RequestParam(required = false) String searchKeyword,
                                  @RequestParam(required = false, defaultValue = "createdAt_desc") String sort,
                                  Model model) {

        List<Map<String, Object>> products = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Map<String, Object> product = new HashMap<>();
            product.put("id", i);
            product.put("productCode", "TEST-" + i);
            product.put("productName", "테스트 상품 " + i);
            product.put("modelNumber", "MODEL-" + i);
            product.put("brand", i % 2 == 0 ? "아디다스" : "나이키");
            product.put("category", "신발");
            product.put("price", 129000 + (i * 1000));
            product.put("discountRate", i % 3 == 0 ? 15 : 0);
            product.put("totalStock", 10 - i);
            product.put("status", i % 4 == 0 ? "HIDDEN" : "ACTIVE");
            product.put("createdAt", new Date());
            product.put("mainImageUrl", "/images/test" + i + ".jpg");
            products.add(product);
        }

        model.addAttribute("products", products);
        model.addAttribute("param", Map.of(
                "registrationDateStart", registrationDateStart,
                "registrationDateEnd", registrationDateEnd,
                "productStatus", productStatus,
                "category", category,
                "brand", brand,
                "searchType", searchType,
                "searchKeyword", searchKeyword,
                "sort", sort
        ));

        return "admin/product/register";
    }

    // ✅ 할인 관리 페이지
    @GetMapping("/discount")
    public String discountManagement(Model model) {
        return "admin/product/discount";
    }

    // ✅ 상품 게시물 관리 페이지
    @GetMapping("/post/register")
    public String productPostRegister(Model model) {
        return "admin/product/post/register";
    }

    // ✅ 상품 상세보기
    @GetMapping("/detail/{id}")
    public String showProductDetail(@PathVariable int id, Model model) {
        List<Map<String, Object>> products = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Map<String, Object> p = new HashMap<>();
            p.put("id", i);
            p.put("productCode", "TEST-" + i);
            p.put("productName", "테스트 상품 " + i);
            p.put("modelNumber", "MODEL-" + i);
            p.put("brand", i % 2 == 0 ? "아디다스" : "나이키");
            p.put("category", "신발");
            p.put("price", 129000 + (i * 1000));
            p.put("discountRate", i % 3 == 0 ? 15 : 0);
            p.put("totalStock", 10 - i);
            p.put("status", i % 4 == 0 ? "HIDDEN" : "ACTIVE");
            p.put("createdAt", new Date());
            p.put("mainImageUrl", "/images/test" + i + ".jpg");
            products.add(p);
        }

        Optional<Map<String, Object>> productOpt = products.stream()
                .filter(p -> Objects.equals(p.get("id"), id))
                .findFirst();

        if (productOpt.isEmpty()) {
            return "error/404";
        }

        model.addAttribute("product", productOpt.get());
        return "admin/product/detail";
    }
}
