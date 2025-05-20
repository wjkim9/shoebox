package com.test.shoebox.controller.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

	@GetMapping("/brandcategory")
    public String brandcategory(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        // 테스트용 더미 데이터
        List<Map<String, Object>> dummyOrders = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Map<String, Object> order = new HashMap<>();
            order.put("id", i);
            order.put("orderNumber", "20240516-" + i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            order.put("orderDateStr", LocalDateTime.now().minusHours(i).format(formatter));

            order.put("customerName", "회원" + i);
            order.put("customerPhone", "010-0000-000" + i);
            order.put("mainProductName", "나이키 운동화");
            order.put("productCount", 1);
            order.put("mainProductSize", "260");
            order.put("totalAmount", 89000);
            order.put("paymentMethod", "카드결제");
            order.put("status", "PAYMENT_COMPLETE");
            order.put("statusName", "결제완료");
            order.put("trackingCompany", "CJ대한통운");
            order.put("trackingNumber", "1234567890" + i);
            order.put("trackingLink", "https://www.example.com/track/" + i);


            dummyOrders.add(order);
        }

        model.addAttribute("category", dummyOrders);

        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/category/brandcategory :: content";
        }
        return "admin/category/brandcategory";
    }
	
	@GetMapping("/producecategory")
    public String producecategory(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        // 테스트용 더미 데이터
        List<Map<String, Object>> dummyOrders = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Map<String, Object> order = new HashMap<>();
            order.put("id", i);
            order.put("orderNumber", "20240516-" + i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            order.put("orderDateStr", LocalDateTime.now().minusHours(i).format(formatter));

            order.put("customerName", "회원" + i);
            order.put("customerPhone", "010-0000-000" + i);
            order.put("mainProductName", "나이키 운동화");
            order.put("productCount", 1);
            order.put("mainProductSize", "260");
            order.put("totalAmount", 89000);
            order.put("paymentMethod", "카드결제");
            order.put("status", "PAYMENT_COMPLETE");
            order.put("statusName", "결제완료");
            order.put("trackingCompany", "CJ대한통운");
            order.put("trackingNumber", "1234567890" + i);
            order.put("trackingLink", "https://www.example.com/track/" + i);


            dummyOrders.add(order);
        }

        model.addAttribute("category", dummyOrders);

        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/category/producecategory :: content";
        }
        return "admin/category/producecategory";
    }

}
