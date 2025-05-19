package com.test.shoebox.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    /**
     * 전체 주문 목록(상세조회/수정(주문 상태 변경)) 페이지
     */
    @GetMapping("/list")
    public String orderList(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
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

        model.addAttribute("orders", dummyOrders);

        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/orders/list :: content";
        }
        return "admin/orders/list";
    }


    /**
     * 교환 요청 목록 페이지
     */
    @GetMapping("/pending-cancel")
    public String pendingCancel(Model model) {
        // TODO: Model에 교환 요청 목록, 처리 상태 등 추가
        return "admin/orders/pending-cancel";
    }

    /**
     * 취소/환불 요청 목록 페이지
     */
    @GetMapping("/pending-refund")
    public String pendingRefund(Model model) {
        // TODO: Model에 취소/환불 요청 목록, 처리 상태 등 추가
        return "admin/orders/pending-refund";
    }

    /**
     * 배송 중 / 완료 내역 페이지
     */
    @GetMapping("/shipping")
    public String shippingHistory(Model model) {
        // TODO: Model에 배송 중 및 완료 내역, 조회 조건 등 추가
        return "admin/orders/shipping";
    }

    /**
     * 주문 엑셀 다운로드
     */
    @GetMapping("/excel")
    public String excelDownload(Model model) {
        // TODO: 엑셀 다운로드를 위한 데이터 준비 및 처리
        return "admin/orders/excel";
    }

}
