package com.test.shoebox.controller.admin;

import com.test.shoebox.dto.OrderDetailDTO;
import com.test.shoebox.dto.OrdersDTO;
import com.test.shoebox.dto.OrdersListDTO;
import com.test.shoebox.service.admin.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrdersService ordersService;

    /**
     * 전체 주문 목록(상세조회/수정(주문 상태 변경)) 페이지
     */
    @GetMapping("/list")
    public String orderList(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        List<OrdersListDTO> orderItems = ordersService.getOrderPageItems();
        model.addAttribute("orders", orderItems);

        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/orders/list :: content";
        }
        return "admin/orders/list";
    }



    /**
     * 주문 상세 페이지
     */
    @GetMapping("/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model,@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        // 서비스에서 주문 상세 정보 가져오기
        OrderDetailDTO order = ordersService.getOrderDetail(orderId);

        model.addAttribute("order", order);

        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/orders/detail :: content";
        }

        return "admin/orders/detail";  // resources/templates/admin/orders/detail.html
    }


    @GetMapping("/cancel")
    public String cancelRequests(Model model,@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        List<Map<String, Object>> cancellations = new ArrayList<>();

        // 예시 취소 요청 1
        Map<String, Object> c1 = new HashMap<>();
        c1.put("id", 1L);
        c1.put("orderNumber", "CNL-" + String.format("%04d", 1));
        c1.put("orderDateStr", "2025-05-16 16:00");
        c1.put("customerName", "테스트 취소자");
        c1.put("customerPhone", "010-1111-2222");
        c1.put("mainProductName", "뉴발란스 574");
        c1.put("productCount", 1);
        c1.put("mainProductSize", "270");
        c1.put("totalAmount", 75000);
        c1.put("paymentMethod", "무통장 입금");
        c1.put("status", "CANCELLED");
        c1.put("statusName", "취소 요청");
        cancellations.add(c1);

        // 예시 취소 요청 2
        Map<String, Object> c2 = new HashMap<>();
        c2.put("id", 2L);
        c2.put("orderNumber", "CNL-" + String.format("%04d", 2));
        c2.put("orderDateStr", "2025-05-15 10:45");
        c2.put("customerName", "홍길동");
        c2.put("customerPhone", "010-3333-4444");
        c2.put("mainProductName", "아디다스 울트라부스트");
        c2.put("productCount", 2);
        c2.put("mainProductSize", "260");
        c2.put("totalAmount", 198000);
        c2.put("paymentMethod", "카드결제");
        c2.put("status", "CANCELLED");
        c2.put("statusName", "취소 요청");
        cancellations.add(c2);

        model.addAttribute("cancellations", cancellations);

        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/orders/cancel :: content";
        }

        return "admin/orders/cancel";  // templates/admin/orders/cancel.html
    }

    /**
     * 환불 요청 목록 조회
     */
    @GetMapping("/refund")
    public String refundRequests(
            Model model,
            @RequestHeader(value = "X-Requested-With", required = false) String requestedWith
    ) {
        List<Map<String, Object>> refunds = new ArrayList<>();

        // 예시 환불 요청 1
        Map<String, Object> r1 = new HashMap<>();
        r1.put("id", 1L);
        r1.put("orderNumber", "RFD-" + String.format("%04d", 1));
        r1.put("requestDateStr", "2025-05-16 16:10");
        r1.put("customerName", "테스트 환불자");
        r1.put("customerPhone", "010-5555-6666");
        r1.put("mainProductName", "컨버스 척테일러");
        r1.put("productCount", 1);
        r1.put("mainProductSize", "250");
        r1.put("totalAmount", 65000);
        r1.put("paymentMethod", "카드결제");
        r1.put("status", "REFUND_REQUESTED");
        r1.put("statusName", "환불 요청");
        refunds.add(r1);

        // 예시 환불 요청 2
        Map<String, Object> r2 = new HashMap<>();
        r2.put("id", 2L);
        r2.put("orderNumber", "RFD-" + String.format("%04d", 2));
        r2.put("requestDateStr", "2025-05-15 09:30");
        r2.put("customerName", "김철수");
        r2.put("customerPhone", "010-7777-8888");
        r2.put("mainProductName", "푸마 스웨이드");
        r2.put("productCount", 2);
        r2.put("mainProductSize", "270");
        r2.put("totalAmount", 180000);
        r2.put("paymentMethod", "무통장 입금");
        r2.put("status", "REFUND_REQUESTED");
        r2.put("statusName", "환불 요청");
        refunds.add(r2);

        model.addAttribute("refunds", refunds);

        // AJAX 요청 시 fragment만 반환
        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/orders/refund :: content";
        }
        // 일반 요청 시 전체 페이지
        return "admin/orders/refund";
    }



}
