package com.test.shoebox.service.admin;

import com.test.shoebox.dto.OrdersListDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExcelExporter {

    public Workbook exportOrderListToExcel(List<OrdersListDTO> orders) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("주문목록");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("주문번호");
        header.createCell(1).setCellValue("주문자명");
        header.createCell(2).setCellValue("연락처");
        header.createCell(3).setCellValue("상품명");
        header.createCell(4).setCellValue("결제금액");
        header.createCell(5).setCellValue("상태");

        int rowIdx = 1;
        for (OrdersListDTO order : orders) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(order.getOrdersId());
            row.createCell(1).setCellValue(order.getReceiverName());
            row.createCell(2).setCellValue(order.getReceiverContact());
            row.createCell(3).setCellValue(order.getMainProductName());
            row.createCell(4).setCellValue(order.getPaymentAmount() + order.getShippingFee());
            row.createCell(5).setCellValue(order.getStatusName());
        }

        return workbook;
    }
}
