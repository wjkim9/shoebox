package com.test.shoebox.controller.admin;

import com.test.shoebox.dto.OrderDetailDTO;
import com.test.shoebox.dto.OrdersDTO;
import com.test.shoebox.dto.OrdersListDTO;
import com.test.shoebox.service.admin.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/list-search")
    public String searchOrders(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate orderDateStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate orderDateEnd,
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            Model model,
            @RequestHeader(value = "X-Requested-With", required = false) String requestedWith
    ) {
        // 검색 결과 조회
        List<OrdersListDTO> orders = ordersService.searchOrders(orderDateStart, orderDateEnd, orderStatus, searchType, searchKeyword);
        model.addAttribute("orders", orders);

        // 선택값을 다시 뷰로 전달
        model.addAttribute("orderDateStart", orderDateStart);
        model.addAttribute("orderDateEnd", orderDateEnd);
        model.addAttribute("orderStatus", orderStatus);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchKeyword", searchKeyword);

        // AJAX 요청일 경우 프래그먼트만 반환
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




}
