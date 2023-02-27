package com.softeem.service;

import com.softeem.bean.Book;
import com.softeem.bean.Order;
import com.softeem.bean.OrderItem;
import com.softeem.util.Page;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    public String createOrder(Cart cart,Integer userId) throws SQLException;

    public Page<Order> page(Integer pageNo,Integer pageSize) throws SQLException;
    public Page<OrderItem> pageOrderItem(String orderId,Integer pageNo,Integer pageSize) throws SQLException;
}
