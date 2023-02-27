package com.softeem.dao.impl;

import com.softeem.bean.Order;
import com.softeem.bean.OrderItem;
import com.softeem.bean.User;
import com.softeem.dao.OrderItemDao;
import com.softeem.util.BaseDao;
import com.softeem.util.JdbcUtils;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {
    @Override
    public User queryUserByUsername(String username) throws SQLException {
        return null;
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public List<OrderItem> findALL() throws SQLException {
        return null;
    }

    @Override
    public void save(OrderItem orderItem) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into t_order_item(`name`,`count`,`price`,`total_price`,`order_id`) values(?,?,?,?,?)";
        queryRunner.update(connection,sql, orderItem.getName(), orderItem.getCount(), orderItem.getPrice(), orderItem.getTotalPrice(), orderItem.getOrderId());
    }

    @Override
    public void updateById(OrderItem orderItem) throws SQLException {

    }

    @Override
    public void deleteById(Integer id) throws SQLException {


    }

    @Override
    public OrderItem findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<OrderItem> page(Integer pageNumber) throws SQLException {
        return null;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        return null;
    }

    @Override
    public List<OrderItem> page(String orderId,Integer pageNumber) throws SQLException {
        String sql = "select * from t_order_item where order_id = ? order by id desc limit ?,?";
        List<OrderItem> orderList = queryRunner.query(sql, new BeanListHandler<>(OrderItem.class, getProcessor()), orderId,(pageNumber - 1) * pageSize, pageSize);
        return orderList;
    }

    @Override
    public Integer pageRecord(String orderId) throws SQLException {
        String sql = "select count(*) from t_order_item where order_id = ?";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler,orderId);
        return i.intValue();
    }

    @Override
    public List<OrderItem> findALLByOrderId(String orderId) throws SQLException {
        String sql = "select * from t_order_item where order_id = ?";
        BeanListHandler<OrderItem> orderItemBeanListHandler = new BeanListHandler<>(OrderItem.class, getProcessor());
        List<OrderItem> query = queryRunner.query(sql, orderItemBeanListHandler, orderId);
        return query;
    }
}
