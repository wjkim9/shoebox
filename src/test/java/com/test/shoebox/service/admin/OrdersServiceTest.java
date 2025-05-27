package com.test.shoebox.service.admin;

import com.test.shoebox.entity.Orders;
import com.test.shoebox.repository.OrdersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional //테스트후 자동 롤백
public class OrdersServiceTest {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    void 주문상태_변경_성공() {
        // 1. 먼저 DB에서 아무 주문이나 하나 꺼내오기
        Orders order = ordersRepository.findAll().get(0);
        int 원래상태 = order.getOrdersStatus();

        // 2. 상태를 '배송중(6)'으로 변경
        ordersService.updateStatus(order.getOrdersId(), 6);

        // 3. 다시 조회해서 상태가 정말 바뀌었는지 확인
        Orders 변경된주문 = ordersRepository.findById(order.getOrdersId()).get();

        // 4. 검증
        assertEquals(6, 변경된주문.getOrdersStatus());
        Assertions.assertNotEquals(원래상태, 변경된주문.getOrdersStatus());
    }

    @Test
    void 주문상태변경_성공() {
        // given
        Long orderId = 1L;
        int newStatus = 5;

        // when
        ordersService.updateStatus(orderId, newStatus);

        // then
        Orders updated = ordersRepository.findById(orderId).get();
        assertEquals(newStatus, updated.getOrdersStatus());
    }

}

