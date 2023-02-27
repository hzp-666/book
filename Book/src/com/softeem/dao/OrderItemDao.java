package com.softeem.dao;

import com.softeem.bean.OrderItem;
import com.softeem.util.BaseInterface;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemDao extends BaseInterface<OrderItem> {
    public List<OrderItem> findALLByOrderId(String orderId) throws SQLException;
    public List<OrderItem> page(String orderId,Integer pageNumber) throws SQLException;
    public Integer pageRecord(String orderId) throws SQLException;
}
