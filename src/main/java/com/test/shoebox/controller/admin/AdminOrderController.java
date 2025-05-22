package com.test.shoebox.controller.admin;

import com.test.shoebox.dto.OrderDetailDTO;
import com.test.shoebox.dto.OrdersDTO;
import com.test.shoebox.dto.OrdersListDTO;
import com.test.shoebox.service.admin.OrdersService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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


    @PostMapping("/{id}/cancel")
    @ResponseBody
    public ResponseEntity<?> cancelOrder(@PathVariable("id") Long orderId) {
        ordersService.updateStatus(orderId, 5); // 상태 5 = '취소 확정'
        return ResponseEntity.ok().build();
    }


    //엑셀로 내보내리
    @GetMapping("/export")
    public void exportOrdersToExcel(
            HttpServletResponse response,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate orderDateStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate orderDateEnd,
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword
    ) throws IOException {
        List<OrdersListDTO> orders = ordersService.searchOrders(orderDateStart, orderDateEnd, orderStatus, searchType, searchKeyword);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=orders.xlsx");

         Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("주문목록");

        // 헤더
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("주문번호");
        header.createCell(1).setCellValue("주문일시");
        header.createCell(2).setCellValue("주문자명");
        header.createCell(3).setCellValue("연락처");
        header.createCell(4).setCellValue("상품명");
        header.createCell(5).setCellValue("상품사이즈");
        header.createCell(6).setCellValue("결제금액");
        header.createCell(7).setCellValue("결제방법");
        header.createCell(8).setCellValue("상태");


        // 날짜 형식 셀 스타일 만들기 (엑셀용)
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm"));

        // 데이터
        int rowIdx = 1;
        for (OrdersListDTO order : orders) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(order.getOrdersId());

            // 날짜 포맷 적용한 셀
            Cell dateCell = row.createCell(1);
            if (order.getOrdersDate() != null) {
                dateCell.setCellValue(java.sql.Timestamp.valueOf(order.getOrdersDate())); // LocalDateTime -> Date
                dateCell.setCellStyle(dateCellStyle);
            }

            row.createCell(2).setCellValue(order.getReceiverName());
            row.createCell(3).setCellValue(order.getReceiverContact());
            row.createCell(4).setCellValue(order.getMainProductName());
            row.createCell(5).setCellValue(order.getMainProductSize());
            row.createCell(6).setCellValue(order.getPaymentAmount() + order.getShippingFee());
            row.createCell(7).setCellValue(order.getPaymentMethod());
            row.createCell(8).setCellValue(order.getStatusName());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }




}
