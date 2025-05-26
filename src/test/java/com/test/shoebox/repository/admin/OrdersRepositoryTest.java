package com.test.shoebox.repository.admin;

import com.test.shoebox.entity.Orders;
import com.test.shoebox.repository.OrdersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class OrdersRepositoryTest {
    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    void OrdersListTest() {
        List<Orders> ordersList = ordersRepository.findAll();
        Assertions.assertNotNull(ordersList);
        Assertions.assertTrue(ordersList.size() >= 0); // 최소 0건 이상
    }
}
