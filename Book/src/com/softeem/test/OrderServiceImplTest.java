package com.softeem.test;

import com.softeem.bean.OrderItem;
import com.softeem.service.OrderService;
import com.softeem.service.impl.OrderServiceImpl;
import com.softeem.util.Page;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceImplTest {

    @Test
    public void pageOrderItem() throws SQLException {
        OrderService orderService = new OrderServiceImpl();
        Page<OrderItem> page = orderService.pageOrderItem("16600387583322", 1, 4);
        for (OrderItem item : page.getItems()) {
            System.out.println(item );
        }
    }
}