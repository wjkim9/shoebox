package com.test.shoebox.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    /**
     * 전체 주문 목록(상세조회/수정(주문 상태 변경)) 페이지
     */
    @GetMapping("/list")
    public String orderList(Model model) {
        // TODO: Model에 주문 목록, 페이징 정보, 필터 조건 등 추가
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
