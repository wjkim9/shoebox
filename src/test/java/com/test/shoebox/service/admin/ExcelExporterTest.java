package com.test.shoebox.service.admin;

import com.test.shoebox.dto.OrdersListDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ExcelExporterTest {

    @Autowired
    ExcelExporter excelExporter;

    @Test
    void 엑셀_파일_생성_테스트() throws IOException {
        // given
        List<OrdersListDTO> dummyList = List.of(
                OrdersListDTO.builder()
                        .ordersId(1L)
                        .receiverName("홍길동")
                        .receiverContact("010-1234-5678")
                        .paymentAmount(89000)
                        .shippingFee(3000)
                        .statusName("결제확인완료")
                        .build(),

                OrdersListDTO.builder()
                        .ordersId(2L)
                        .receiverName("이순신")
                        .receiverContact("010-9999-8888")
                        .paymentAmount(125000)
                        .shippingFee(2500)
                        .statusName("배송중")
                        .build()
        );


        // when
        Workbook workbook = excelExporter.exportOrderListToExcel(dummyList);

        // then
        File file = new File("test-orders.xlsx");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }
        workbook.close();

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }
}
